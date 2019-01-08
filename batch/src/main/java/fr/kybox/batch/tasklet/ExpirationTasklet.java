package fr.kybox.batch.tasklet;

import fr.kybox.batch.result.BatchResult;
import fr.kybox.entities.Email;
import fr.kybox.gencode.*;
import fr.kybox.service.AuthService;
import fr.kybox.service.MailBuilder;
import fr.kybox.service.MailService;
import fr.kybox.utils.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static fr.kybox.utils.ValueTypes.*;
import static fr.kybox.utils.ValueTypes.CONNECTED;
import static fr.kybox.utils.ValueTypes.TOKEN;

@Component
@PropertySource("classpath:application.properties")
public class ExpirationTasklet extends AbstractTasklet implements Tasklet, StepExecutionListener {

    private final LibraryService libraryService = ServiceFactory.getLibraryService();
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final Email email;
    private final MailService mailService;
    private final AuthService authService;
    private final ObjectFactory objectFactory;

    @Value("${auth.email}")
    private String batchEmail;

    @Value("${auth.pass}")
    private String batchPass;

    @Value("${expiration.subject}")
    private String mailSubject;

    @Value("${expiration.intro}")
    private String mailIntro;

    @Value("${expiration.message}")
    private String mailMessage;

    @Value("${expiration.message2}")
    private String mailMessage2;

    @Value("${expiration.outro}")
    private String mailOutro;

    @Value("${expiration.signature}")
    private String mailSignature;

    @Value("${mail.sender}")
    private String mailSender;

    @Autowired
    public ExpirationTasklet(AuthService authService, ObjectFactory objectFactory,
                             Email email, MailService mailService) {

        this.authService = authService;
        this.objectFactory = objectFactory;
        this.email = email;
        this.mailService = mailService;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        logger.debug("BeforeStep method");
        BatchResult.put(START_TIME, stepExecution.getJobExecution().getStartTime());
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        logger.debug("Execute method");
        BatchResult.put(AUTHENTICATION, ATTEMPT_TO_CONNECT);

        Map<String, Object> loginData = authService.login(batchEmail, batchPass);
        if(loginData.get(AUTHENTICATION).equals(UNAUTHORIZED)){
            BatchResult.put(ERROR, LOGIN_FAILED);
            logger.debug(ERROR + LOGIN_FAILED);
            return RepeatStatus.FINISHED;
        }

        BatchResult.put(LOGIN_SUCCESS, CONNECTED);
        String token = (String) loginData.get(TOKEN);

        SearchLoansAboutToExpire request = objectFactory.createSearchLoansAboutToExpire();
        request.setToken(token);

        BatchResult.put(CHECKING_CURRENT_LOANS, ATTEMPT_TO_CONNECT);

        SearchLoansAboutToExpireResponse response = libraryService.searchLoansAboutToExpire(request);

        int resultCode = response.getResult();
        if(response.getResult() != HTTP_CODE_OK)
            return executionError(resultCode, token, libraryService);

        BatchResult.put(CHECKING_CURRENT_LOANS, CHECKING_COMPLETED);

        List<Borrower> borrowerList = response.getBorrower();

        int total = borrowerList.size();
        int index = 1;

        for(Borrower borrower : borrowerList){

            BatchResult.put(LOAN, index + SLASH + total);

            BookBorrowed loan = borrower.getBookBorrowed();
            Book book = borrower.getBookBorrowed().getBook();
            User user = borrower.getUser();

            String userFirstName = user.getFirstName();
            String userLastName = user.getLastName();
            String userEmail = user.getEmail();

            String bookIsbn = book.getIsbn();
            String bookTitle = book.getTitle();
            String bookAuthor = book.getAuthor();

            BatchResult.put(USER, userFirstName + SPACE + userLastName);
            BatchResult.put(BOOK, bookTitle + BY + bookAuthor);

            email.setFrom(mailSender);
            email.setTo(userEmail);
            email.setSubject(mailSubject);

            LocalDate returnDate = loan.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today = LocalDate.now();

            long numberOfDay = ChronoUnit.DAYS.between(today, returnDate);

            String expirationMsg = numberOfDay + SPACE;
            if(numberOfDay == 1) expirationMsg += DAY;
            else expirationMsg += DAYS;

            if(numberOfDay < 0){
                BatchResult.put(LOAN_STATUS, LOAN_NOT_RETURNED);
                BatchResult.put(PROCESS_DELEGATION, BATCH_OF_UNRETURNED_BOOKS);
                continue;
            }

            BatchResult.put(TIME_TO_EXPIRY, LOAN_EXPIRE_IN + SPACE + expirationMsg);

            String message = mailIntro + DOUBLE_LINE_BREAK;
            message += mailMessage + SPACE + expirationMsg + DOUBLE_LINE_BREAK;
            message += mailMessage2 + DOUBLE_LINE_BREAK;
            message += "ISBN : " + bookIsbn + LINE_BREAK;
            message += "Titre : " + bookTitle + LINE_BREAK;
            message += "Auteur : " + bookAuthor + DOUBLE_LINE_BREAK;

            if(!borrower.getBookBorrowed().isExtended()) message += mailOutro + DOUBLE_LINE_BREAK;

            message += mailSignature;

            email.setMessage(message);

            if(email.checkAll()) {

                MailBuilder mailBuilder = new MailBuilder();
                mailBuilder.setEmail(email);

                BatchResult.put(ATTEMPT_TO_SEND, TO + userEmail);

                if (!mailService.send(mailBuilder)) {
                    BatchResult.put(SENDING_FAIL, NOTIFICATION_NOT_SENT);
                    continue;
                }

                BatchResult.put(SUCCESSFUL_SENDING, NOTIFICATION_SENT);

            }
        }

        libraryService.logout(token);
        BatchResult.put(LOGOUT, ADMIN_TOKEN_REMOVED);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        BatchResult.put(BATCH_PROCESSING, COMPLETED);
        BatchResult.put(END_TIME, new Date());
        return ExitStatus.COMPLETED;
    }
}

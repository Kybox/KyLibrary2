package fr.kybox.batch.tasklet;

import fr.kybox.batch.result.BatchResult;
import fr.kybox.entities.Email;
import fr.kybox.exception.EmailException;
import fr.kybox.gencode.*;
import fr.kybox.service.MailBuilder;
import fr.kybox.service.MailService;
import fr.kybox.utils.ResultCode;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static fr.kybox.utils.ValueTypes.*;

@Component
@PropertySource("classpath:application.properties")
public class UnreturnedTasklet implements Tasklet, StepExecutionListener {

    private final LibraryService libraryService = ServiceFactory.getLibraryService();
    private static Logger logger = LogManager.getLogger(UnreturnedTasklet.class);

    private final ObjectFactory objectFactory;
    private final MailService mailService;
    private final Email email;

    @Value("${auth.email}")
    private String batchEmail;

    @Value("${auth.pass}")
    private String batchPass;

    @Value("${unreturned.subject}")
    private String mailSubject;

    @Value("${unreturned.intro}")
    private String mailIntro;

    @Value("${unreturned.message}")
    private String mailMsg1;

    @Value("${unreturned.message2}")
    private String mailMsg2;

    @Value("${unreturned.outro}")
    private String mailOutro;

    @Value("${unreturned.signature}")
    private String mailSignature;

    @Value("${mail.sender}")
    private String mailSender;

    @Autowired
    public UnreturnedTasklet(ObjectFactory objectFactory, MailService mailService, Email email) {
        this.objectFactory = objectFactory;
        this.mailService = mailService;
        this.email = email;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        logger.info("Unreturned Tasklet - BeforeStep method");
        BatchResult.put(START_TIME, stepExecution.getJobExecution().getStartTime());
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        logger.info("Unreturned Tasklet - Execute method");
        BatchResult.put(AUTHENTICATION, ATTEMPT_TO_CONNECT);

        Login login = objectFactory.createLogin();
        login.setLogin(batchEmail);
        login.setPassword(batchPass);

        LoginResponse loginResponse = libraryService.login(login);

        if(loginResponse.getResult() != ResultCode.OK){
            BatchResult.put(ERROR, LOGIN_FAILED);
            logger.debug("Error ! Login = " + loginResponse.getResult());
            return RepeatStatus.FINISHED;
        }

        BatchResult.put(LOGIN_SUCCESS, CONNECTED);

        String token = loginResponse.getToken();

        UnreturnedBookList request = objectFactory.createUnreturnedBookList();
        request.setToken(token);
        request.setReturnDate(new Date());
        UnreturnedBookListResponse response = libraryService.unreturnedBookList(request);

        int resultCode = response.getResult();
        if(resultCode != ResultCode.OK){
            BatchResult.put(ERROR, resultCode);
            logger.error("Error ! Reponse = " + resultCode);
            return RepeatStatus.FINISHED;
        }

        logger.info("UnreturnedBookListResponse = OK");

        if(response.getUnreturnedBook().isEmpty()) {
            BatchResult.put(NO_BOOK_FOUND, BOOK_LIST_IS_EMPTY);
            logger.info(BOOK_LIST_IS_EMPTY);
            libraryService.logout(token);
            return RepeatStatus.FINISHED;
        }

        List<UnreturnedBook> bookList = response.getUnreturnedBook();

        int index = 1;
        int total = bookList.size();

        for(UnreturnedBook unreturnedBook : bookList){

            Book book = unreturnedBook.getBookBorrowed().getBook();
            User user = unreturnedBook.getUser();

            email.setFrom(mailSender);
            email.setTo(user.getEmail());
            email.setSubject(mailSubject);

            BatchResult.put(USER, user.getFirstName() + SPACE + user.getLastName());
            BatchResult.put(BOOK + index + SLASH + total, book.getTitle() + BY + book.getAuthor());

            String message = mailIntro + SPACE + user.getFirstName() + SPACE + user.getLastName() + DOUBLE_LINE_BREAK;
            message += mailMsg1;
            message += ISBN + book.getIsbn() + LINE_BREAK;
            message += BOOK_TITLE + book.getTitle() + LINE_BREAK;
            message += AUTHOR + book.getAuthor() + DOUBLE_LINE_BREAK;

            Date returnDate = unreturnedBook.getBookBorrowed().getReturnDate();
            String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(returnDate);

            message += mailMsg2 + formatedDate + DOUBLE_LINE_BREAK;
            message += mailOutro;
            message += mailSignature;

            email.setMessage(message);

            if(email.checkAll()){

                MailBuilder mailBuilder = new MailBuilder();
                mailBuilder.setEmail(email);

                BatchResult.put(ATTEMPT_TO_SEND, TO + user.getEmail());
                if(mailService.send(mailBuilder)) BatchResult.put(SUCCESSFUL_SENDING, NOTIFICATION_SENT);
                else BatchResult.put(SENDING_FAIL, NOTIFICATION_NOT_SENT);
            }
            else throw new EmailException("L'objet Email ne contient pas pas toutes les informations requises");
        }

        libraryService.logout(token);
        BatchResult.put(LOGOUT, ADMIN_TOKEN_REMOVED);

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        logger.info("Unreturned Tasklet - AfterStep method");
        BatchResult.put(BATCH_PROCESSING, COMPLETED);
        BatchResult.put(END_TIME, new Date());

        return ExitStatus.COMPLETED;
    }
}

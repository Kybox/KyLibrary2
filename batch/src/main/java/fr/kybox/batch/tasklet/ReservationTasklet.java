package fr.kybox.batch.tasklet;

import fr.kybox.batch.result.BatchResult;
import fr.kybox.entities.Email;
import fr.kybox.gencode.*;
import fr.kybox.service.AuthService;
import fr.kybox.service.MailBuilder;
import fr.kybox.service.MailService;
import fr.kybox.service.NotificationService;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static fr.kybox.utils.ValueTypes.*;

@Component
@PropertySource("classpath:application.properties")
public class ReservationTasklet implements Tasklet, StepExecutionListener {

    private final LibraryService libraryService = ServiceFactory.getLibraryService();
    private static Logger logger = LogManager.getLogger(UnreturnedTasklet.class);

    private final NotificationService notificationService;
    private final ObjectFactory objectFactory;
    private final AuthService authService;
    private final MailService mailService;
    private final Email email;

    @Value("${auth.email}")
    private String batchEmail;

    @Value("${auth.pass}")
    private String batchPass;

    @Value("${reservation.subject}")
    private String mailSubject;

    @Value("${reservation.intro}")
    private String mailIntro;

    @Value("${reservation.message}")
    private String mailMessage;

    @Value("${reservation.outro}")
    private String mailOutro;

    @Value("${reservation.signature}")
    private String mailSignature;

    @Value("${mail.sender}")
    private String mailSender;

    @Autowired
    public ReservationTasklet(ObjectFactory objectFactory, MailService mailService, Email email,
                              AuthService authService, NotificationService notificationService) {
        this.objectFactory = objectFactory;
        this.mailService = mailService;
        this.email = email;
        this.authService = authService;
        this.notificationService = notificationService;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        logger.debug("BeforeStep method");
        BatchResult.put(START_TIME, stepExecution.getJobExecution().getStartTime());
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution,
                                ChunkContext chunkContext) throws Exception {

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

        CheckReservedBooks checkReservedBooks = objectFactory.createCheckReservedBooks();
        checkReservedBooks.setToken(token);

        CheckReservedBooksResponse response = libraryService.checkReservedBooks(checkReservedBooks);

        int resultCode = response.getResult();
        if(resultCode != HTTP_CODE_OK){
            BatchResult.put(ERROR, resultCode);
            logger.warn(ERROR, resultCode);
            libraryService.logout(token);
            BatchResult.put(LOGOUT, ADMIN_TOKEN_REMOVED);
            return RepeatStatus.FINISHED;
        }

        List<BookReserved> bookList = response.getBookReserved();

        if(bookList.isEmpty()){
            BatchResult.put(NO_BOOK_FOUND, BOOK_LIST_IS_EMPTY);
            logger.info(BOOK_LIST_IS_EMPTY);
            libraryService.logout(token);
            BatchResult.put(LOGOUT, ADMIN_TOKEN_REMOVED);
            return RepeatStatus.FINISHED;
        }

        List<User> userList = response.getUser();
        int total = bookList.size();

        for(int index = 0; index < total; index++){

            BatchResult.put(RESERVATION, (index + 1) + SLASH + total);

            String userFirstName = userList.get(index).getFirstName();
            String userLastName = userList.get(index).getLastName();
            String userEmail = userList.get(index).getEmail();

            String bookIsbn = bookList.get(index).getBook().getIsbn();
            String bookTitle = bookList.get(index).getBook().getTitle();
            String bookAuthor = bookList.get(index).getBook().getAuthor();

            BatchResult.put(USER, userFirstName + SPACE + userLastName);
            BatchResult.put(BOOK, bookTitle + BY + bookAuthor);

            email.setFrom(mailSender);
            email.setTo(userEmail);
            email.setSubject(mailSubject);

            String message = mailIntro;
            message += mailMessage;
            message += "ISBN : " + bookIsbn + "\n";
            message += "Titre : " + bookTitle + "\n";
            message += "Auteur : " + bookAuthor + "\n\n";
            message += mailOutro;
            message += mailSignature;

            email.setMessage(message);

            if(email.checkAll()){

                MailBuilder mailBuilder = new MailBuilder();
                mailBuilder.setEmail(email);

                BatchResult.put(ATTEMPT_TO_SEND, TO + userEmail);

                if(!mailService.send(mailBuilder)) {
                    BatchResult.put(SENDING_FAIL, NOTIFICATION_NOT_SENT);
                    continue;
                }

                BatchResult.put(SUCCESSFUL_SENDING, NOTIFICATION_SENT);

                if(!notificationService
                        .setReservationAvailable(token, bookIsbn, userEmail)){
                    BatchResult.put(NOTIFICATION_REGISTRATION, NOTIFICATION_FAILED);
                    continue;
                }

                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
                String currentDate = dateFormat.format(date);
                dateFormat = new SimpleDateFormat("HH:mm");
                String currentTime = dateFormat.format(date);

                BatchResult.put(NOTIFICATION_REGISTRATION,
                        NOTIFICATION_SUCCESS + SPACE + ON + currentDate + AT + currentTime);
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

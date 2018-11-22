package fr.kybox.batch.tasklet;

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

@Component
@PropertySource("classpath:application.properties")
public class ReminderTasklet implements Tasklet, StepExecutionListener {

    private final LibraryService libraryService = ServiceFactory.getLibraryService();
    private static Logger logger = LogManager.getLogger(ReminderTasklet.class);

    @Autowired private ObjectFactory objectFactory;
    @Autowired private MailService mailService;
    @Autowired private Email email;

    @Value("${auth.email}") private String batchEmail;
    @Value("${auth.pass}") private String batchPass;
    @Value("${reminder.subject}") private String reminderSubject;
    @Value("${reminder.intro}") private String reminderIntro;
    @Value("${reminder.message}") private String reminderMessage;
    @Value("${reminder.outro}") private String reminderOutro;
    @Value("${reminder.signature}") private String reminderSignature;

    private String token;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        logger.debug("BeforeStep method");


    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        logger.debug("Execute method");

        Login login = objectFactory.createLogin();
        login.setLogin(batchEmail);
        login.setPassword(batchPass);

        LoginResponse loginResponse = libraryService.login(login);

        if(loginResponse.getResult() == ResultCode.OK) {
            token = loginResponse.getToken();

            UnreturnedBookList request = objectFactory.createUnreturnedBookList();
            request.setToken(token);
            UnreturnedBookListResponse response = libraryService.unreturnedBookList(request);

            if(response.getResult() == ResultCode.OK){

                logger.debug("UnreturnedBookListResponse = OK");

                for(UnreturnedBook unreturnedBook : response.getUnreturnedBook()){

                    Book book = unreturnedBook.getBookBorrowed().getBook();
                    User user = unreturnedBook.getUser();

                    email.setFrom("no-reply@kylibrary.fr");
                    email.setTo(user.getEmail());
                    email.setSubject(reminderSubject);

                    String message = reminderIntro + " " + user.getFirstName() + " " + user.getLastName() + "\n\n";
                    message += reminderMessage;
                    message += "ISBN : " + book.getIsbn() + "\n";
                    message += "Titre : " + book.getTitle() + "\n";
                    message += "Auteur : " + book.getAuthor() + "\n\n";

                    Date returnDate = unreturnedBook.getBookBorrowed().getReturnDate();
                    String formatedDate = new SimpleDateFormat("dd-MM-yyyy").format(returnDate);

                    message += "Ce livre aurait dû être rapporter le " + formatedDate + ".\n\n";
                    message += reminderOutro;
                    message += reminderSignature;

                    email.setMessage(message);

                    if(email.checkAll()){

                        logger.debug("Email.checkAll() returned TRUE");
                        MailBuilder mailBuilder = new MailBuilder();
                        mailBuilder.setEmail(email);
                        mailService.send(mailBuilder);
                    }
                    else throw new EmailException("L'objet Email ne contient pas pas toutes les informations requises");
                }

                libraryService.logout(token);
            }
            else logger.error("Reponse = " + response.getResult());
        }
        else logger.debug("Login error - Code : " + loginResponse.getResult());

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        logger.debug("AfterStep method");

        return ExitStatus.COMPLETED;
    }
}

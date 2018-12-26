package fr.kybox.batch.tasklet;

import fr.kybox.entities.Email;
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

@Component
@PropertySource("classpath:application.properties")
public class ReservationAlertTasklet implements Tasklet, StepExecutionListener {

    private final LibraryService libraryService = ServiceFactory.getLibraryService();
    private static Logger logger = LogManager.getLogger(UnreturnedTasklet.class);

    private final ObjectFactory objectFactory;
    private final MailService mailService;
    private final Email email;

    @Value("${auth.email}") private String batchEmail;
    @Value("${auth.pass}") private String batchPass;
    @Value("${reservation.subject}") private String reminderSubject;
    @Value("${reservation.intro}") private String reminderIntro;
    @Value("${reservation.message}") private String reminderMessage;
    @Value("${reservation.outro}") private String reminderOutro;
    @Value("${reservation.signature}") private String reminderSignature;

    @Autowired
    public ReservationAlertTasklet(ObjectFactory objectFactory, MailService mailService, Email email) {
        this.objectFactory = objectFactory;
        this.mailService = mailService;
        this.email = email;
    }


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

            CheckReservedBooks checkReservedBooks = objectFactory.createCheckReservedBooks();
            checkReservedBooks.setToken(loginResponse.getToken());

            CheckReservedBooksResponse response = libraryService.checkReservedBooks(checkReservedBooks);

            if(response.getResult() == ResultCode.OK){

                if(!response.getBookReserved().isEmpty()){

                    for(int i = 0; i < response.getBookReserved().size(); i++){

                        email.setFrom("no-reply@kylibrary.fr");
                        email.setTo(response.getUser().get(i).getEmail());
                        email.setSubject(reminderSubject);

                        String message = reminderIntro;
                        message += reminderMessage;
                        message += "ISBN : " + response.getBookReserved().get(i).getBook().getIsbn() + "\n";
                        message += "Titre : " + response.getBookReserved().get(i).getBook().getTitle() + "\n";
                        message += "Auteur : " + response.getBookReserved().get(i).getBook().getAuthor() + "\n\n";
                        message += reminderOutro;
                        message += reminderSignature;

                        email.setMessage(message);

                        if(email.checkAll()){
                            MailBuilder mailBuilder = new MailBuilder();
                            mailBuilder.setEmail(email);
                            mailService.send(mailBuilder);
                        }
                    }
                }

            }
            else logger.info("CheckReqeservedBooks error : " + response.getResult());

        }
        else logger.debug("Login error - Code : " + loginResponse.getResult());

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}

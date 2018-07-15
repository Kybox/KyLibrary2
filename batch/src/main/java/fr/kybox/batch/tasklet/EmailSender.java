package fr.kybox.batch.tasklet;

import fr.kybox.batch.utils.BatchResult;
import fr.kybox.gencode.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Kybox
 * @version 1.0
 */
@Component
public class EmailSender implements Tasklet, StepExecutionListener {

    private Logger logger = LogManager.getLogger(EmailSender.class);

    private List<UnreturnedBook> itemList;

    @Autowired
    @Qualifier("mailProperties")
    private Properties properties;

    private Session session;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        logger.debug("MailSender before tasklet");
        ExecutionContext executionContext = stepExecution.getJobExecution().getExecutionContext();
        itemList = (List<UnreturnedBook>) executionContext.get("itemList");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String user = properties.getProperty("mail.auth.user");
                String pass = properties.getProperty("mail.auth.pass");
                return new PasswordAuthentication(user, pass);
            }
        });
    }


    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        logger.debug("MailSender execute tasklet");

        int index = 0;

        for (UnreturnedBook item : itemList){

            index++;
            BatchResult.put("User [" + index + "/" + itemList.size() + "]", item.getUser().getEmail());
            BatchResult.put("Book [" + index + "/" + itemList.size() + "]", item.getBookBorrowed().getBook().getTitle());

            try{
                MimeMessage email = new MimeMessage(session);
                InternetAddress address = new InternetAddress();
                address.setAddress(item.getUser().getEmail());
                email.setRecipient(Message.RecipientType.TO, address);
                email.setHeader("XPriority", "1");
                email.setSubject(properties.getProperty("mail.template.subject"));

                String finalMessage = properties.getProperty("mail.template.intro") + " "
                        + item.getUser().getFirstName()
                        + " " + item.getUser().getLastName() + "\n\n";

                finalMessage += properties.getProperty("mail.template.message1") + "\n";
                finalMessage += "ISBN : " + item.getBookBorrowed().getBook().getIsbn() + "\n";
                finalMessage += "Titre : " + item.getBookBorrowed().getBook().getTitle() + "\n";
                finalMessage += "Auteur : " + item.getBookBorrowed().getBook().getAuthor() + "\n";
                finalMessage += "Edition : " + item.getBookBorrowed().getBook().getPublisher() + "\n";
                finalMessage += "\n";

                Date returnDate = item.getBookBorrowed().getReturndate();


                finalMessage += "Date limite de retour : "
                        + new SimpleDateFormat("dd-MM-yyyy")
                        .format(returnDate) + "\n";

                finalMessage += "\n";
                finalMessage += properties.getProperty("mail.template.outro");

                email.setText(finalMessage, "utf-8");

                Transport.send(email);

                BatchResult.put("Mail [" + index + "/" + itemList.size() + "]", "Sent");

            }
            catch (MessagingException e){
                logger.error(e);
                BatchResult.put("Error for User[" + index + "/" + itemList.size() + "]", "E-mail not sent");
            }
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        logger.debug("MailSender after tasklet");

        BatchResult.put("Batch processing", "Completed");

        return ExitStatus.COMPLETED;
    }
}

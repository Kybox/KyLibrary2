package fr.kybox.batch.email;

import com.sendgrid.Mail;
import com.sendgrid.SendGrid;
import fr.kybox.batch.bean.EmailMessage;
import fr.kybox.batch.email.sender.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Kybox
 * @version 1.0
 */
public class SendgridSender implements EmailSender {

    @Autowired
    private SendGrid sendGrid;

    @Override
    public void send(EmailMessage email) {

        Mail mail = new Mail();

    }
}

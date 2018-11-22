package fr.kybox.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private static Logger logger = LogManager.getLogger(MailService.class);

    private final
    JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean send(MailBuilder mailBuilder)
            throws MailException, javax.mail.MessagingException {
        return this.send(mailBuilder, false, "UTF-8");
    }

    public boolean send(MailBuilder mailBuilder, boolean multipart, String encoding)
            throws MailException, javax.mail.MessagingException {

        logger.debug("Send method called");

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, multipart, encoding);

        helper.setFrom(mailBuilder.getFrom());
        helper.addTo(mailBuilder.getTo());
        helper.setSubject(mailBuilder.getSubject());

        if(mailBuilder.getText() != null) {
            helper.setText(mailBuilder.getText(), false);
        }

        this.javaMailSender.send(mail);

        logger.debug("Mail sent to " + mailBuilder.getTo());

        return true;
    }
}

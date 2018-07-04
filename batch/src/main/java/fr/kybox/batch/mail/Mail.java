package fr.kybox.batch.mail;

import fr.kybox.gencode.Book;
import fr.kybox.gencode.UnreturnedBook;
import fr.kybox.gencode.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Properties;

/**
 * @author Kybox
 * @version 1.0
 */
public class Mail {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private Properties properties;

    public void send(UnreturnedBook unreturnedBook){

        SimpleMailMessage templateMessage = new SimpleMailMessage();
        templateMessage.setFrom(properties.getProperty("mail.template.sender"));
        templateMessage.setTo(unreturnedBook.getUser().getEmail());
        templateMessage.setSubject(properties.getProperty("mail.template.subject"));

        User user = unreturnedBook.getUser();

        String message = "Bonjour " + user.getFirstName() + " " + user.getLastName() + "\n";
        message += properties.getProperty("mail.template.message1") + "\n";

        Book book = unreturnedBook.getBookBorrowed().getBook();
        message += "ISBN de l'ouvrage : " + book.getIsbn() + "\n";
        message += "Titre de l'ouvrage : " + book.getTitle() + "\n";
        message += "Auteur de l'ouvrage : " + book.getAuthor() + "\n\n";
        message += "Date limite de retour le " + unreturnedBook.getBookBorrowed().getReturndate() + "\n\n";
        message += properties.getProperty("mail.template.message2");

        templateMessage.setText(message);

        mailSender.send(templateMessage);

    }

}

package fr.kybox.batch.email.sender;

import fr.kybox.batch.bean.EmailMessage;

public interface EmailSender {

    void send(EmailMessage email);
}

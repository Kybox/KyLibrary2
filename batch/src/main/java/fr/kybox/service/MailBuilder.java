package fr.kybox.service;

import fr.kybox.entities.Email;
import org.apache.logging.log4j.Logger;

public class MailBuilder {

    private String subject;
    private String text;
    private String from;
    private String to;

    public void setEmail(Email email){

        setSubject(email.getSubject());
        setText(email.getMessage());
        setFrom(email.getFrom());
        setTo(email.getTo());
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

package fr.kybox.entities;

public class Email {

    private String from;
    private String to;
    private String subject;
    private String message;

    public Email() {
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
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public boolean checkAll(){

        if(
                getFrom() != null && !getFrom().isEmpty()
                && getTo() != null && !getTo().isEmpty()
                && getSubject() != null && !getSubject().isEmpty()
                && getMessage() != null && !getMessage().isEmpty()
        )
            return true;
            else return false;
    }
}

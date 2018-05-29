package artwork.web.rest.rdto.connection;

import java.time.ZonedDateTime;

public class NewConnectionRDTO {

    private Boolean acepted;

    private String title;

    private String message;

    private Long sender;

    private Long receiver;

    public Boolean getAcepted() {
        return acepted;
    }

    public void setAcepted(Boolean acepted) {
        this.acepted = acepted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Connection{" +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}

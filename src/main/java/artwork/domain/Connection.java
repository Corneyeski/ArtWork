package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Connection.
 */
@Entity
@Table(name = "connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Connection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "acepted")
    private Boolean acepted;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAcepted() {
        return acepted;
    }

    public Connection acepted(Boolean acepted) {
        this.acepted = acepted;
        return this;
    }

    public void setAcepted(Boolean acepted) {
        this.acepted = acepted;
    }

    public String getTitle() {
        return title;
    }

    public Connection title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public Connection message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Connection time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public User getSender() {
        return sender;
    }

    public Connection sender(User user) {
        this.sender = user;
        return this;
    }

    public void setSender(User user) {
        this.sender = user;
    }

    public User getReceiver() {
        return receiver;
    }

    public Connection receiver(User user) {
        this.receiver = user;
        return this;
    }

    public void setReceiver(User user) {
        this.receiver = user;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Connection connection = (Connection) o;
        if (connection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), connection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Connection{" +
            "id=" + getId() +
            ", acepted='" + isAcepted() + "'" +
            ", title='" + getTitle() + "'" +
            ", message='" + getMessage() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}

package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Conversation.
 */
@Entity
@Table(name = "conversation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    private User userOne;

    @ManyToOne
    private User userTwo;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Conversation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Conversation time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Conversation deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public User getUserOne() {
        return userOne;
    }

    public Conversation userOne(User user) {
        this.userOne = user;
        return this;
    }

    public void setUserOne(User user) {
        this.userOne = user;
    }

    public User getUserTwo() {
        return userTwo;
    }

    public Conversation userTwo(User user) {
        this.userTwo = user;
        return this;
    }

    public void setUserTwo(User user) {
        this.userTwo = user;
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
        Conversation conversation = (Conversation) o;
        if (conversation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conversation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", time='" + getTime() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}

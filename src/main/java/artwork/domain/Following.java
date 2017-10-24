package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Following.
 */
@Entity
@Table(name = "following")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Following implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @ManyToOne
    private User follower;

    @ManyToOne
    private User followed;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Following time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public User getFollower() {
        return follower;
    }

    public Following follower(User user) {
        this.follower = user;
        return this;
    }

    public void setFollower(User user) {
        this.follower = user;
    }

    public User getFollowed() {
        return followed;
    }

    public Following followed(User user) {
        this.followed = user;
        return this;
    }

    public void setFollowed(User user) {
        this.followed = user;
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
        Following following = (Following) o;
        if (following.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), following.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Following{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            "}";
    }
}

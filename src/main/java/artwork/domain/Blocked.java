package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Blocked.
 */
@Entity
@Table(name = "blocked")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Blocked implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @ManyToOne
    private User block;

    @ManyToOne
    private User blocked;

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

    public Blocked time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public User getBlock() {
        return block;
    }

    public Blocked block(User user) {
        this.block = user;
        return this;
    }

    public void setBlock(User user) {
        this.block = user;
    }

    public User getBlocked() {
        return blocked;
    }

    public Blocked blocked(User user) {
        this.blocked = user;
        return this;
    }

    public void setBlocked(User user) {
        this.blocked = user;
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
        Blocked blocked = (Blocked) o;
        if (blocked.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blocked.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Blocked{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            "}";
    }
}

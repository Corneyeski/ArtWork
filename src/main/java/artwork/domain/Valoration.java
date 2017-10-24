package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Valoration.
 */
@Entity
@Table(name = "valoration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Valoration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "mark", nullable = false)
    private Double mark;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @ManyToOne
    private User user;

    @ManyToOne
    private Multimedia multimedia;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMark() {
        return mark;
    }

    public Valoration mark(Double mark) {
        this.mark = mark;
        return this;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Valoration time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public Valoration user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public Valoration multimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
        return this;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
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
        Valoration valoration = (Valoration) o;
        if (valoration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valoration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Valoration{" +
            "id=" + getId() +
            ", mark='" + getMark() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}

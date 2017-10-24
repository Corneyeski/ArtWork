package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserProfileValoration.
 */
@Entity
@Table(name = "user_profile_valoration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfileValoration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "jhi_value", nullable = false)
    private Double value;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    private User valuator;

    @ManyToOne
    private User valuated;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public UserProfileValoration value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getComments() {
        return comments;
    }

    public UserProfileValoration comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getValuator() {
        return valuator;
    }

    public UserProfileValoration valuator(User user) {
        this.valuator = user;
        return this;
    }

    public void setValuator(User user) {
        this.valuator = user;
    }

    public User getValuated() {
        return valuated;
    }

    public UserProfileValoration valuated(User user) {
        this.valuated = user;
        return this;
    }

    public void setValuated(User user) {
        this.valuated = user;
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
        UserProfileValoration userProfileValoration = (UserProfileValoration) o;
        if (userProfileValoration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userProfileValoration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserProfileValoration{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}

package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Offer.
 */
@Entity
@Table(name = "offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "tags")
    private String tags;

    @Column(name = "location")
    private String location;

    @Column(name = "contract")
    private String contract;

    @ManyToOne
    private User creator;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "offer_user_ext",
               joinColumns = @JoinColumn(name="offers_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="user_exts_id", referencedColumnName="id"))
    private Set<UserExt> userExts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Offer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Offer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Offer time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Double getSalary() {
        return salary;
    }

    public Offer salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getDuration() {
        return duration;
    }

    public Offer duration(Double duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Boolean isStatus() {
        return status;
    }

    public Offer status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public Offer tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLocation() {
        return location;
    }

    public Offer location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContract() {
        return contract;
    }

    public Offer contract(String contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public User getCreator() {
        return creator;
    }

    public Offer creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public Set<UserExt> getUserExts() {
        return userExts;
    }

    public Offer userExts(Set<UserExt> userExts) {
        this.userExts = userExts;
        return this;
    }

    public Offer addUserExt(UserExt userExt) {
        this.userExts.add(userExt);
        userExt.getOffers().add(this);
        return this;
    }

    public Offer removeUserExt(UserExt userExt) {
        this.userExts.remove(userExt);
        userExt.getOffers().remove(this);
        return this;
    }

    public void setUserExts(Set<UserExt> userExts) {
        this.userExts = userExts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Offer offer = (Offer) o;
        if (offer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", time='" + getTime() + "'" +
            ", salary='" + getSalary() + "'" +
            ", duration='" + getDuration() + "'" +
            ", status='" + isStatus() + "'" +
            ", tags='" + getTags() + "'" +
            ", location='" + getLocation() + "'" +
            ", contract='" + getContract() + "'" +
            "}";
    }
}

package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "reason")
    private String reason;

    @ManyToOne
    private User reporter;

    @ManyToOne
    private User reported;

    @ManyToOne
    private Offer offer;

    @ManyToOne
    private Multimedia multimedia;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Report title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public Report reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getReporter() {
        return reporter;
    }

    public Report reporter(User user) {
        this.reporter = user;
        return this;
    }

    public void setReporter(User user) {
        this.reporter = user;
    }

    public User getReported() {
        return reported;
    }

    public Report reported(User user) {
        this.reported = user;
        return this;
    }

    public void setReported(User user) {
        this.reported = user;
    }

    public Offer getOffer() {
        return offer;
    }

    public Report offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public Report multimedia(Multimedia multimedia) {
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
        Report report = (Report) o;
        if (report.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), report.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}

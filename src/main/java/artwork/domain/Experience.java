package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Experience.
 */
@Entity
@Table(name = "experience")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "ending_year")
    private Integer endingYear;

    @Column(name = "name")
    private String name;

    @Column(name = "company")
    private String company;

    @Lob
    @Column(name = "competitions")
    private String competitions;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Experience startYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndingYear() {
        return endingYear;
    }

    public Experience endingYear(Integer endingYear) {
        this.endingYear = endingYear;
        return this;
    }

    public void setEndingYear(Integer endingYear) {
        this.endingYear = endingYear;
    }

    public String getName() {
        return name;
    }

    public Experience name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public Experience company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompetitions() {
        return competitions;
    }

    public Experience competitions(String competitions) {
        this.competitions = competitions;
        return this;
    }

    public void setCompetitions(String competitions) {
        this.competitions = competitions;
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
        Experience experience = (Experience) o;
        if (experience.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experience.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Experience{" +
            "id=" + getId() +
            ", startYear='" + getStartYear() + "'" +
            ", endingYear='" + getEndingYear() + "'" +
            ", name='" + getName() + "'" +
            ", company='" + getCompany() + "'" +
            ", competitions='" + getCompetitions() + "'" +
            "}";
    }
}

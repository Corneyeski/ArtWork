package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "ending_year")
    private Integer endingYear;

    @Column(name = "jhi_degree")
    private String degree;

    @Column(name = "study_center")
    private String studyCenter;

    @Lob
    @Column(name = "competitions")
    private byte[] competitions;

    @Column(name = "competitions_content_type")
    private String competitionsContentType;

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

    public Training startYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndingYear() {
        return endingYear;
    }

    public Training endingYear(Integer endingYear) {
        this.endingYear = endingYear;
        return this;
    }

    public void setEndingYear(Integer endingYear) {
        this.endingYear = endingYear;
    }

    public String getDegree() {
        return degree;
    }

    public Training degree(String degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getStudyCenter() {
        return studyCenter;
    }

    public Training studyCenter(String studyCenter) {
        this.studyCenter = studyCenter;
        return this;
    }

    public void setStudyCenter(String studyCenter) {
        this.studyCenter = studyCenter;
    }

    public byte[] getCompetitions() {
        return competitions;
    }

    public Training competitions(byte[] competitions) {
        this.competitions = competitions;
        return this;
    }

    public void setCompetitions(byte[] competitions) {
        this.competitions = competitions;
    }

    public String getCompetitionsContentType() {
        return competitionsContentType;
    }

    public Training competitionsContentType(String competitionsContentType) {
        this.competitionsContentType = competitionsContentType;
        return this;
    }

    public void setCompetitionsContentType(String competitionsContentType) {
        this.competitionsContentType = competitionsContentType;
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
        Training training = (Training) o;
        if (training.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), training.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Training{" +
            "id=" + getId() +
            ", startYear='" + getStartYear() + "'" +
            ", endingYear='" + getEndingYear() + "'" +
            ", degree='" + getDegree() + "'" +
            ", studyCenter='" + getStudyCenter() + "'" +
            ", competitions='" + getCompetitions() + "'" +
            ", competitionsContentType='" + competitionsContentType + "'" +
            "}";
    }
}

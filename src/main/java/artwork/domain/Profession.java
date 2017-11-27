package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Profession.
 */
@Entity
@Table(name = "profession")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sector")
    private String sector;

    @Column(name = "workers_num")
    private Integer workersNum;

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

    public Profession name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public Profession sector(String sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Integer getWorkersNum() {
        return workersNum;
    }

    public Profession workersNum(Integer workersNum) {
        this.workersNum = workersNum;
        return this;
    }

    public void setWorkersNum(Integer workersNum) {
        this.workersNum = workersNum;
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
        Profession profession = (Profession) o;
        if (profession.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profession{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", sector='" + getSector() + "'" +
            ", workersNum='" + getWorkersNum() + "'" +
            "}";
    }
}

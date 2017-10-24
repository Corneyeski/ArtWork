package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PricesT.
 */
@Entity
@Table(name = "prices_t")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PricesT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @OneToOne
    @JoinColumn(unique = true)
    private Tool tool;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhone() {
        return phone;
    }

    public PricesT phone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public PricesT name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public PricesT city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Tool getTool() {
        return tool;
    }

    public PricesT tool(Tool tool) {
        this.tool = tool;
        return this;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
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
        PricesT pricesT = (PricesT) o;
        if (pricesT.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pricesT.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PricesT{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", name='" + getName() + "'" +
            ", city='" + getCity() + "'" +
            "}";
    }
}

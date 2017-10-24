package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ResumeCreation.
 */
@Entity
@Table(name = "resume_creation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResumeCreation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "skills")
    private byte[] skills;

    @Column(name = "skills_content_type")
    private String skillsContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Tool tool;

    @ManyToOne
    private Language language;

    @ManyToOne
    private City city;

    @ManyToOne
    private Training training;

    @ManyToOne
    private Experience experience;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ResumeCreation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public ResumeCreation lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public ResumeCreation birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public ResumeCreation photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public ResumeCreation photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getEmail() {
        return email;
    }

    public ResumeCreation email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public ResumeCreation phone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public ResumeCreation address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getSkills() {
        return skills;
    }

    public ResumeCreation skills(byte[] skills) {
        this.skills = skills;
        return this;
    }

    public void setSkills(byte[] skills) {
        this.skills = skills;
    }

    public String getSkillsContentType() {
        return skillsContentType;
    }

    public ResumeCreation skillsContentType(String skillsContentType) {
        this.skillsContentType = skillsContentType;
        return this;
    }

    public void setSkillsContentType(String skillsContentType) {
        this.skillsContentType = skillsContentType;
    }

    public Tool getTool() {
        return tool;
    }

    public ResumeCreation tool(Tool tool) {
        this.tool = tool;
        return this;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Language getLanguage() {
        return language;
    }

    public ResumeCreation language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public City getCity() {
        return city;
    }

    public ResumeCreation city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Training getTraining() {
        return training;
    }

    public ResumeCreation training(Training training) {
        this.training = training;
        return this;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Experience getExperience() {
        return experience;
    }

    public ResumeCreation experience(Experience experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
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
        ResumeCreation resumeCreation = (ResumeCreation) o;
        if (resumeCreation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resumeCreation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResumeCreation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", birthdate='" + getBirthdate() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + photoContentType + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", skills='" + getSkills() + "'" +
            ", skillsContentType='" + skillsContentType + "'" +
            "}";
    }
}

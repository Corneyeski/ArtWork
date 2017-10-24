package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import artwork.domain.enumeration.Theme;

/**
 * A UserExt.
 */
@Entity
@Table(name = "user_ext")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "kind")
    private Integer kind;

    @Column(name = "tags")
    private String tags;

    @Column(name = "address")
    private String address;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "popular")
    private Double popular;

    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "company_points")
    private Double companyPoints;

    @Column(name = "validated")
    private Boolean validated;

    @Column(name = "age")
    private Integer age;

    @Column(name = "working")
    private Boolean working;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private Theme theme;

    @Lob
    @Column(name = "resume")
    private byte[] resume;

    @Column(name = "resume_content_type")
    private String resumeContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private City city;

    @ManyToOne
    private Language language;

    @ManyToOne
    private User workingOn;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public UserExt birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public byte[] getImage() {
        return image;
    }

    public UserExt image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public UserExt imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Integer getPhone() {
        return phone;
    }

    public UserExt phone(Integer phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getKind() {
        return kind;
    }

    public UserExt kind(Integer kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getTags() {
        return tags;
    }

    public UserExt tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public UserExt address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPopular() {
        return popular;
    }

    public UserExt popular(Double popular) {
        this.popular = popular;
        return this;
    }

    public void setPopular(Double popular) {
        this.popular = popular;
    }

    public Double getCompanyPoints() {
        return companyPoints;
    }

    public UserExt companyPoints(Double companyPoints) {
        this.companyPoints = companyPoints;
        return this;
    }

    public void setCompanyPoints(Double companyPoints) {
        this.companyPoints = companyPoints;
    }

    public Boolean isValidated() {
        return validated;
    }

    public UserExt validated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Integer getAge() {
        return age;
    }

    public UserExt age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean isWorking() {
        return working;
    }

    public UserExt working(Boolean working) {
        this.working = working;
        return this;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public Theme getTheme() {
        return theme;
    }

    public UserExt theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public byte[] getResume() {
        return resume;
    }

    public UserExt resume(byte[] resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getResumeContentType() {
        return resumeContentType;
    }

    public UserExt resumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
        return this;
    }

    public void setResumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
    }

    public User getUser() {
        return user;
    }

    public UserExt user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCity() {
        return city;
    }

    public UserExt city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Language getLanguage() {
        return language;
    }

    public UserExt language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public User getWorkingOn() {
        return workingOn;
    }

    public UserExt workingOn(User user) {
        this.workingOn = user;
        return this;
    }

    public void setWorkingOn(User user) {
        this.workingOn = user;
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
        UserExt userExt = (UserExt) o;
        if (userExt.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userExt.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserExt{" +
            "id=" + getId() +
            ", birthdate='" + getBirthdate() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", phone='" + getPhone() + "'" +
            ", kind='" + getKind() + "'" +
            ", tags='" + getTags() + "'" +
            ", address='" + getAddress() + "'" +
            ", popular='" + getPopular() + "'" +
            ", companyPoints='" + getCompanyPoints() + "'" +
            ", validated='" + isValidated() + "'" +
            ", age='" + getAge() + "'" +
            ", working='" + isWorking() + "'" +
            ", theme='" + getTheme() + "'" +
            ", resume='" + getResume() + "'" +
            ", resumeContentType='" + resumeContentType + "'" +
            "}";
    }
}

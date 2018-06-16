package artwork.web.rest.rdto;

import artwork.domain.City;
import artwork.domain.Language;
import artwork.domain.Profession;
import artwork.domain.User;
import artwork.domain.enumeration.Theme;

public class UserRDTO {

    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String langKey;
    private Long idExt;
    private byte[] image;
    private String imageContentType;
    private Integer phone;
    private String tags;
    private String address;
    private Boolean working;
    private Theme theme;
    private byte[] resume;
    private String resumeContentType;

    private Boolean rememberInfo;
    private City city;
    private Language language;
    private Profession profession;
    private User workingOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Long getIdExt() {
        return idExt;
    }

    public void setIdExt(Long idExt) {
        this.idExt = idExt;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getResumeContentType() {
        return resumeContentType;
    }

    public void setResumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
    }

    public Boolean getRememberInfo() {
        return rememberInfo;
    }

    public void setRememberInfo(Boolean rememberInfo) {
        this.rememberInfo = rememberInfo;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public User getWorkingOn() {
        return workingOn;
    }

    public void setWorkingOn(User workingOn) {
        this.workingOn = workingOn;
    }
}

package artwork.service.dto;

import artwork.config.Constants;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class NewUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @Email
    @NotNull
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(min = 2, max = 5)
    private String langKey;

    private NewUserExtDTO newUserExtDTO;

    public NewUserDTO() {}

    public NewUserDTO(String login, String email, String firstName, String lastName, String password, String langKey, NewUserExtDTO newUserExtDTO) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.langKey = langKey;
        this.newUserExtDTO = newUserExtDTO;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NewUserExtDTO getNewUserExtDTO() {
        return newUserExtDTO;
    }

    public void setNewUserExtDTO(NewUserExtDTO newUserExtDTO) {
        this.newUserExtDTO = newUserExtDTO;
    }

    public String getLangKey() { return langKey; }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }
}

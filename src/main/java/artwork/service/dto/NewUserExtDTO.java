package artwork.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class NewUserExtDTO {

    @NotNull
    @Size(min = 1, max = 1)
    private Integer kind;

    @NotNull
    private Timestamp birthdate;

    public NewUserExtDTO() {}

    public NewUserExtDTO(Integer kind, Timestamp birthdate) {
        this.kind = kind;
        this.birthdate = birthdate;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }
}

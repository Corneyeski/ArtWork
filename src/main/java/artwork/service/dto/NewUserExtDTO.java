package artwork.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class NewUserExtDTO {

    @NotNull
    @Size(min = 1, max = 1)
    private Integer kind;

    @NotNull
    private LocalDate birthdate;

    private byte[] image;

    private String imageContentType;

    @NotNull
    private Boolean working;

    public NewUserExtDTO() {}

    public NewUserExtDTO(Integer kind, LocalDate birthdate, byte[] image, String imageContentType, Boolean working) {
        this.kind = kind;
        this.birthdate = birthdate;
        this.image = image;
        this.imageContentType = imageContentType;
        this.working = working;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
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

    public Boolean getWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }
}

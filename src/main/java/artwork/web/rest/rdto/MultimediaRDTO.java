package artwork.web.rest.rdto;

import artwork.domain.enumeration.Type;

import java.time.ZonedDateTime;

/**
 * Created by alanv on 14/01/2018.
 */
public class MultimediaRDTO {

    private Long id;
    private Double totalValoration;
    private String title;
    private byte[] song;
    private byte[] image;
    private Type type;
    private ZonedDateTime time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalValoration() {
        return totalValoration;
    }

    public void setTotalValoration(Double totalValoration) {
        this.totalValoration = totalValoration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getSong() {
        return song;
    }

    public void setSong(byte[] song) {
        this.song = song;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }
}

package artwork.web.rest.rdto.multimedia;

import artwork.domain.enumeration.Type;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.ZonedDateTime;

/**
 * Created by alanv on 14/01/2018.
 */
public class MultimediaRDTO {

    private Long id;
    private Double totalValoration;
    private String title;
    private String tags;
    private byte[] song;
    private byte[] image;
    private String imageContentType;
    private String songContentType;
    @Enumerated(EnumType.STRING)
    private Type type;
    private ZonedDateTime time;
    private Long user;
    private Long Album;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getSongContentType() {
        return songContentType;
    }

    public void setSongContentType(String songContentType) {
        this.songContentType = songContentType;
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

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getAlbum() {
        return Album;
    }

    public void setAlbum(Long album) {
        Album = album;
    }
}

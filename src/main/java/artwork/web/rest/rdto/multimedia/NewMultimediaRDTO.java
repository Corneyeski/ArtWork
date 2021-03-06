package artwork.web.rest.rdto.multimedia;

import artwork.domain.enumeration.Type;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

public class NewMultimediaRDTO {

    @NotNull
    private String title;
    private String description;
    private String resolution;
    private String copyright;
    private String tags;
    private byte[] song;
    private byte[] image;
    private String imageContentType;
    private String songContentType;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;
    //TODO arreglar album para que se realiciones con el usuario tambien
    private Long Album;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
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

    public Long getAlbum() {
        return Album;
    }

    public void setAlbum(Long album) {
        Album = album;
    }
}

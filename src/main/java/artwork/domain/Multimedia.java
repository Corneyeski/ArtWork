package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import artwork.domain.enumeration.Type;

/**
 * A Multimedia.
 */
@Entity
@Table(name = "multimedia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Multimedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "song")
    private byte[] song;

    @Column(name = "song_content_type")
    private String songContentType;

    @Column(name = "url")
    private String url;

    @Column(name = "url_share")
    private String urlShare;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "tags")
    private String tags;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "5")
    @Column(name = "total_valoration", nullable = false)
    private Double totalValoration;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private Type type;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "users_tag")
    private String usersTag;

    @Column(name = "copyright")
    private String copyright;

    @ManyToOne
    private User user;

    @ManyToOne
    private Album album;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Multimedia title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public Multimedia image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Multimedia imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getSong() {
        return song;
    }

    public Multimedia song(byte[] song) {
        this.song = song;
        return this;
    }

    public void setSong(byte[] song) {
        this.song = song;
    }

    public String getSongContentType() {
        return songContentType;
    }

    public Multimedia songContentType(String songContentType) {
        this.songContentType = songContentType;
        return this;
    }

    public void setSongContentType(String songContentType) {
        this.songContentType = songContentType;
    }

    public String getUrl() {
        return url;
    }

    public Multimedia url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlShare() {
        return urlShare;
    }

    public Multimedia urlShare(String urlShare) {
        this.urlShare = urlShare;
        return this;
    }

    public void setUrlShare(String urlShare) {
        this.urlShare = urlShare;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Multimedia time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getTags() {
        return tags;
    }

    public Multimedia tags(String tags) {
        this.tags = tags;
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Double getTotalValoration() {
        return totalValoration;
    }

    public Multimedia totalValoration(Double totalValoration) {
        this.totalValoration = totalValoration;
        return this;
    }

    public void setTotalValoration(Double totalValoration) {
        this.totalValoration = totalValoration;
    }

    public Type getType() {
        return type;
    }

    public Multimedia type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Multimedia description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsersTag() {
        return usersTag;
    }

    public Multimedia usersTag(String usersTag) {
        this.usersTag = usersTag;
        return this;
    }

    public void setUsersTag(String usersTag) {
        this.usersTag = usersTag;
    }

    public String getCopyright() {
        return copyright;
    }

    public Multimedia copyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public User getUser() {
        return user;
    }

    public Multimedia user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album getAlbum() {
        return album;
    }

    public Multimedia album(Album album) {
        this.album = album;
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Multimedia multimedia = (Multimedia) o;
        if (multimedia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), multimedia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Multimedia{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", song='" + getSong() + "'" +
            ", songContentType='" + songContentType + "'" +
            ", url='" + getUrl() + "'" +
            ", urlShare='" + getUrlShare() + "'" +
            ", time='" + getTime() + "'" +
            ", tags='" + getTags() + "'" +
            ", totalValoration='" + getTotalValoration() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", usersTag='" + getUsersTag() + "'" +
            ", copyright='" + getCopyright() + "'" +
            "}";
    }
}

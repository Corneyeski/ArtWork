package artwork.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "likes")
    private Integer likes;

    @ManyToOne
    private Multimedia multimedia;

    @ManyToOne
    private Comment response;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Comment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Comment time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Integer getLikes() {
        return likes;
    }

    public Comment likes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public Comment multimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
        return this;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    public Comment getResponse() {
        return response;
    }

    public Comment response(Comment comment) {
        this.response = comment;
        return this;
    }

    public void setResponse(Comment comment) {
        this.response = comment;
    }

    public User getUser() {
        return user;
    }

    public Comment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", time='" + getTime() + "'" +
            ", likes='" + getLikes() + "'" +
            "}";
    }
}

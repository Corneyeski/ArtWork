package artwork.web.rest.rdto;

import artwork.domain.Album;
import artwork.domain.Following;
import artwork.domain.Multimedia;
import artwork.domain.User;

import java.util.List;

public class ProfileRDTO {

    private User user;
    private List<Multimedia> multimedias;
    private List<Following> followers;
    private List<Following> followeds;
    private List<Album> albums;

    public ProfileRDTO() {}

    public ProfileRDTO(User user, List<Multimedia> multimedias, List<Following> followers, List<Following> followeds, List<Album> albums) {
        this.user = user;
        this.multimedias = multimedias;
        this.followers = followers;
        this.followeds = followeds;
        this.albums = albums;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Multimedia> getMultimedias() {
        return multimedias;
    }

    public void setMultimedias(List<Multimedia> multimedias) {
        this.multimedias = multimedias;
    }

    public List<Following> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Following> followers) {
        this.followers = followers;
    }

    public List<Following> getFolloweds() {
        return followeds;
    }

    public void setFolloweds(List<Following> followeds) {
        this.followeds = followeds;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}

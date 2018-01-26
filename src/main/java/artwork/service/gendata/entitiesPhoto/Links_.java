
package artwork.service.gendata.entitiesPhoto;

import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "self",
    "html",
    "photos",
    "likes",
    "portfolio",
    "following",
    "followers"
})
public class Links_ {

    @JsonProperty("self")
    private String self;
    @JsonProperty("html")
    private String html;
    @JsonProperty("photos")
    private String photos;
    @JsonProperty("likes")
    private String likes;
    @JsonProperty("portfolio")
    private String portfolio;
    @JsonProperty("following")
    private String following;
    @JsonProperty("followers")
    private String followers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    public Links_ withSelf(String self) {
        this.self = self;
        return this;
    }

    @JsonProperty("html")
    public String getHtml() {
        return html;
    }

    @JsonProperty("html")
    public void setHtml(String html) {
        this.html = html;
    }

    public Links_ withHtml(String html) {
        this.html = html;
        return this;
    }

    @JsonProperty("photos")
    public String getPhotos() {
        return photos;
    }

    @JsonProperty("photos")
    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Links_ withPhotos(String photos) {
        this.photos = photos;
        return this;
    }

    @JsonProperty("likes")
    public String getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(String likes) {
        this.likes = likes;
    }

    public Links_ withLikes(String likes) {
        this.likes = likes;
        return this;
    }

    @JsonProperty("portfolio")
    public String getPortfolio() {
        return portfolio;
    }

    @JsonProperty("portfolio")
    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public Links_ withPortfolio(String portfolio) {
        this.portfolio = portfolio;
        return this;
    }

    @JsonProperty("following")
    public String getFollowing() {
        return following;
    }

    @JsonProperty("following")
    public void setFollowing(String following) {
        this.following = following;
    }

    public Links_ withFollowing(String following) {
        this.following = following;
        return this;
    }

    @JsonProperty("followers")
    public String getFollowers() {
        return followers;
    }

    @JsonProperty("followers")
    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public Links_ withFollowers(String followers) {
        this.followers = followers;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Links_ withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}

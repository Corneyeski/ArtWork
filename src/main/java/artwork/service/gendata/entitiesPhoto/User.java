
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
    "id",
    "updated_at",
    "username",
    "name",
    "first_name",
    "last_name",
    "twitter_username",
    "portfolio_url",
    "bio",
    "location",
    "links",
    "profile_image",
    "total_collections",
    "total_likes",
    "total_photos"
})
public class User {

    @JsonProperty("id")
    private String id;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("username")
    private String username;
    @JsonProperty("name")
    private String name;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("twitter_username")
    private String twitterUsername;
    @JsonProperty("portfolio_url")
    private String portfolioUrl;
    @JsonProperty("bio")
    private String bio;
    @JsonProperty("location")
    private String location;
    @JsonProperty("links")
    private Links_ links;
    @JsonProperty("profile_image")
    private ProfileImage profileImage;
    @JsonProperty("total_collections")
    private int totalCollections;
    @JsonProperty("total_likes")
    private int totalLikes;
    @JsonProperty("total_photos")
    private int totalPhotos;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public User withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public User withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonProperty("twitter_username")
    public String getTwitterUsername() {
        return twitterUsername;
    }

    @JsonProperty("twitter_username")
    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public User withTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
        return this;
    }

    @JsonProperty("portfolio_url")
    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    @JsonProperty("portfolio_url")
    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public User withPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
        return this;
    }

    @JsonProperty("bio")
    public String getBio() {
        return bio;
    }

    @JsonProperty("bio")
    public void setBio(String bio) {
        this.bio = bio;
    }

    public User withBio(String bio) {
        this.bio = bio;
        return this;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    public User withLocation(String location) {
        this.location = location;
        return this;
    }

    @JsonProperty("links")
    public Links_ getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links_ links) {
        this.links = links;
    }

    public User withLinks(Links_ links) {
        this.links = links;
        return this;
    }

    @JsonProperty("profile_image")
    public ProfileImage getProfileImage() {
        return profileImage;
    }

    @JsonProperty("profile_image")
    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public User withProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    @JsonProperty("total_collections")
    public int getTotalCollections() {
        return totalCollections;
    }

    @JsonProperty("total_collections")
    public void setTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
    }

    public User withTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
        return this;
    }

    @JsonProperty("total_likes")
    public int getTotalLikes() {
        return totalLikes;
    }

    @JsonProperty("total_likes")
    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public User withTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
        return this;
    }

    @JsonProperty("total_photos")
    public int getTotalPhotos() {
        return totalPhotos;
    }

    @JsonProperty("total_photos")
    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public User withTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
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

    public User withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}


package artwork.service.gendata.entitiesPhoto;

import java.util.HashMap;
import java.util.List;
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
    "created_at",
    "updated_at",
    "width",
    "height",
    "color",
    "description",
    "categories",
    "urls",
    "links",
    "liked_by_user",
    "sponsored",
    "likes",
    "user",
    "current_user_collections",
    "slug",
    "exif",
    "views",
    "downloads"
})
public class LinksRetrofit {

    @JsonProperty("id")
    private String id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;
    @JsonProperty("color")
    private String color;
    @JsonProperty("description")
    private String description;
    @JsonProperty("categories")
    private List<Object> categories = null;
    @JsonProperty("urls")
    private Urls urls;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("liked_by_user")
    private boolean likedByUser;
    @JsonProperty("sponsored")
    private boolean sponsored;
    @JsonProperty("likes")
    private int likes;
    @JsonProperty("user")
    private User user;
    @JsonProperty("current_user_collections")
    private List<Object> currentUserCollections = null;
    @JsonProperty("slug")
    private Object slug;
    @JsonProperty("exif")
    private Exif exif;
    @JsonProperty("views")
    private int views;
    @JsonProperty("downloads")
    private int downloads;
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

    public LinksRetrofit withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public LinksRetrofit withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public LinksRetrofit withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @JsonProperty("width")
    public int getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(int width) {
        this.width = width;
    }

    public LinksRetrofit withWidth(int width) {
        this.width = width;
        return this;
    }

    @JsonProperty("height")
    public int getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(int height) {
        this.height = height;
    }

    public LinksRetrofit withHeight(int height) {
        this.height = height;
        return this;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    public LinksRetrofit withColor(String color) {
        this.color = color;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public LinksRetrofit withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("categories")
    public List<Object> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public LinksRetrofit withCategories(List<Object> categories) {
        this.categories = categories;
        return this;
    }

    @JsonProperty("urls")
    public Urls getUrls() {
        return urls;
    }

    @JsonProperty("urls")
    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public LinksRetrofit withUrls(Urls urls) {
        this.urls = urls;
        return this;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    public LinksRetrofit withLinks(Links links) {
        this.links = links;
        return this;
    }

    @JsonProperty("liked_by_user")
    public boolean isLikedByUser() {
        return likedByUser;
    }

    @JsonProperty("liked_by_user")
    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public LinksRetrofit withLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
        return this;
    }

    @JsonProperty("sponsored")
    public boolean isSponsored() {
        return sponsored;
    }

    @JsonProperty("sponsored")
    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public LinksRetrofit withSponsored(boolean sponsored) {
        this.sponsored = sponsored;
        return this;
    }

    @JsonProperty("likes")
    public int getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LinksRetrofit withLikes(int likes) {
        this.likes = likes;
        return this;
    }

    @JsonProperty("user")
    public User getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(User user) {
        this.user = user;
    }

    public LinksRetrofit withUser(User user) {
        this.user = user;
        return this;
    }

    @JsonProperty("current_user_collections")
    public List<Object> getCurrentUserCollections() {
        return currentUserCollections;
    }

    @JsonProperty("current_user_collections")
    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public LinksRetrofit withCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
        return this;
    }

    @JsonProperty("slug")
    public Object getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(Object slug) {
        this.slug = slug;
    }

    public LinksRetrofit withSlug(Object slug) {
        this.slug = slug;
        return this;
    }

    @JsonProperty("exif")
    public Exif getExif() {
        return exif;
    }

    @JsonProperty("exif")
    public void setExif(Exif exif) {
        this.exif = exif;
    }

    public LinksRetrofit withExif(Exif exif) {
        this.exif = exif;
        return this;
    }

    @JsonProperty("views")
    public int getViews() {
        return views;
    }

    @JsonProperty("views")
    public void setViews(int views) {
        this.views = views;
    }

    public LinksRetrofit withViews(int views) {
        this.views = views;
        return this;
    }

    @JsonProperty("downloads")
    public int getDownloads() {
        return downloads;
    }

    @JsonProperty("downloads")
    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public LinksRetrofit withDownloads(int downloads) {
        this.downloads = downloads;
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

    public LinksRetrofit withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}

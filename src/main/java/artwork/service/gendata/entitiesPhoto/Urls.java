
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
    "raw",
    "full",
    "regular",
    "small",
    "thumb"
})
public class Urls {

    @JsonProperty("raw")
    private String raw;
    @JsonProperty("full")
    private String full;
    @JsonProperty("regular")
    private String regular;
    @JsonProperty("small")
    private String small;
    @JsonProperty("thumb")
    private String thumb;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("raw")
    public String getRaw() {
        return raw;
    }

    @JsonProperty("raw")
    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Urls withRaw(String raw) {
        this.raw = raw;
        return this;
    }

    @JsonProperty("full")
    public String getFull() {
        return full;
    }

    @JsonProperty("full")
    public void setFull(String full) {
        this.full = full;
    }

    public Urls withFull(String full) {
        this.full = full;
        return this;
    }

    @JsonProperty("regular")
    public String getRegular() {
        return regular;
    }

    @JsonProperty("regular")
    public void setRegular(String regular) {
        this.regular = regular;
    }

    public Urls withRegular(String regular) {
        this.regular = regular;
        return this;
    }

    @JsonProperty("small")
    public String getSmall() {
        return small;
    }

    @JsonProperty("small")
    public void setSmall(String small) {
        this.small = small;
    }

    public Urls withSmall(String small) {
        this.small = small;
        return this;
    }

    @JsonProperty("thumb")
    public String getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Urls withThumb(String thumb) {
        this.thumb = thumb;
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

    public Urls withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}

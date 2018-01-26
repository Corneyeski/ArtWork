
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
    "make",
    "model",
    "exposure_time",
    "aperture",
    "focal_length",
    "iso"
})
public class Exif {

    @JsonProperty("make")
    private String make;
    @JsonProperty("model")
    private String model;
    @JsonProperty("exposure_time")
    private String exposureTime;
    @JsonProperty("aperture")
    private String aperture;
    @JsonProperty("focal_length")
    private String focalLength;
    @JsonProperty("iso")
    private int iso;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("make")
    public String getMake() {
        return make;
    }

    @JsonProperty("make")
    public void setMake(String make) {
        this.make = make;
    }

    public Exif withMake(String make) {
        this.make = make;
        return this;
    }

    @JsonProperty("model")
    public String getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    public Exif withModel(String model) {
        this.model = model;
        return this;
    }

    @JsonProperty("exposure_time")
    public String getExposureTime() {
        return exposureTime;
    }

    @JsonProperty("exposure_time")
    public void setExposureTime(String exposureTime) {
        this.exposureTime = exposureTime;
    }

    public Exif withExposureTime(String exposureTime) {
        this.exposureTime = exposureTime;
        return this;
    }

    @JsonProperty("aperture")
    public String getAperture() {
        return aperture;
    }

    @JsonProperty("aperture")
    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public Exif withAperture(String aperture) {
        this.aperture = aperture;
        return this;
    }

    @JsonProperty("focal_length")
    public String getFocalLength() {
        return focalLength;
    }

    @JsonProperty("focal_length")
    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public Exif withFocalLength(String focalLength) {
        this.focalLength = focalLength;
        return this;
    }

    @JsonProperty("iso")
    public int getIso() {
        return iso;
    }

    @JsonProperty("iso")
    public void setIso(int iso) {
        this.iso = iso;
    }

    public Exif withIso(int iso) {
        this.iso = iso;
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

    public Exif withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}

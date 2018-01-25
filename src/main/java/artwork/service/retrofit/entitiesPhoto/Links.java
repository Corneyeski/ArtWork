package artwork.service.retrofit.entitiesPhoto;

import com.google.gson.annotations.SerializedName;

public class Links {
    private String self;
    private String html;
    private String download;
    @SerializedName("download_location")
    private String downloadLocation;

    public Links(String self, String html, String download, String downloadLocation) {
        this.self = self;
        this.html = html;
        this.download = download;
        this.downloadLocation = downloadLocation;
    }

    public Links() {
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }
}

package app.elghamry.android.bakingapp.model;

import java.io.Serializable;

/**
 * Created by ELGHAMRY
 */

public class Step implements Serializable {

    private String id,shortDescription,description,videoURL,thumbnailURL;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}

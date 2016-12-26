package com.ilp.tcs.sitesafety.modals;

/**
 * Created by 1239940 on 6/1/2016.
 * This class contains all the images of the Survey.
 */
public class Image {

    /**
     * Primary key of the TblImages table
     */
    private long imageId;
    /**
     * Primary Key of the TblSurvey table.
     */
    private long surveyId;
    /**
     * Primary key of TblCategoryPoints table
     */
    private long pointId;

    private String location;

    /**
     * It contains the image path.
     */
    private String imagePath;

    /**
     * It contains image caption
     */
    private String caption;

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getSubcategoryId() {
        return pointId;
    }

    public void setSubcategoryId(long subcategoryId) {
        this.pointId = subcategoryId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}

package com.ilp.tcs.sitesafety.modals;

/**
 * Created by 1239940 on 6/1/2016.
 * SurveyResponse
 */
public class SurveyResponse {
    /**
     * Primary key of the TblSurveyResponse
     */
    private long surveyResponseId;
    /**
     *  Primary key of the TblSurvey table
     */
    private long surveyId;
    /**
     *  Primary key of the TblCategoryPoints
     */
    private long subcategoryId;
    /**
     * Descriptions entered by the user for a particular response.
     */
    private String description;
    /**
     * Status of the response.It can be "Yes","NA","No"
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getSurveyResponseId() {
        return surveyResponseId;
    }

    public void setSurveyResponseId(long surveyResponseId) {
        this.surveyResponseId = surveyResponseId;
    }

    public long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }
}

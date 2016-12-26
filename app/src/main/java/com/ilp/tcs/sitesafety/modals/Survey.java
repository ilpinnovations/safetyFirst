package com.ilp.tcs.sitesafety.modals;

/**
 * Created by 1239940 on 6/1/2016.
 * Survey
 */
public class Survey {

    /**
     * Primary key of the TblSurvey table
     */
    private long surveyId;
    /**
     * Primary key of the TblUser table
     */
    private long userId;
    /**
     * Contains the name of the user
     */
    private String userName;
    /**
     * Contains the Created date & time of the survey
     */
    private long createdDateTime;
    /**
     * Contains the name survey Title.
     */
    private String surveyTitle;
    /**
     * Contains the name of the user who has updated the user.
     */
    private String updatedBy;
    /**
     * Contains the Updated time time & date
     */
    private long updatedDateTime;
    /**
     * Contains the Score of the category
     */
    private long scoreByWeightedCategory;
    /**
     * Contains the Score Of the questions
     */
    private long scoreByWeightedQuestion;
    /**
     * Contains the path of pdf file
     */
    private String pdfPath;

    //======
    private String ofi_excel_path;

    public void setOfi_excel_path(String ofi_excel_path) {
        this.ofi_excel_path = ofi_excel_path;
    }
    public String getOfi_excel_path(){
        return ofi_excel_path;
    }

    private String ofi_pdf_path;
    /**
     * Contains the path of excel file
     */
    private String excelPath;
    /**
     * Contains the path of word file
     */
    private String wordPath;

    private String surveyCompleteStatus = "0";

    private String surveyBranch;

    private String surveyLocation;

    public long getScoreByWeightedCategory() {
        return scoreByWeightedCategory;
    }

    public void setScoreByWeightedCategory(long scoreByWeightedCategory) {
        this.scoreByWeightedCategory = scoreByWeightedCategory;
    }

    public long getScoreByWeightedQuestion() {
        return scoreByWeightedQuestion;
    }

    public void setScoreByWeightedQuestion(long scoreByWeightedQuestion) {
        this.scoreByWeightedQuestion = scoreByWeightedQuestion;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getOFIPdfPath() {
        return this.ofi_pdf_path;
    }

    public void setOFIPdfPath(String ofi_pdf_path) {
        this.ofi_pdf_path = ofi_pdf_path;
    }


    public String getWordPath() {
        return wordPath;
    }

    public void setWordPath(String wordPath) {
        this.wordPath = wordPath;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(long updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getSurveyCompleteStatus() {
        return surveyCompleteStatus;
    }

    public void setSurveyCompleteStatus(String surveyCompleteStatus) {
        this.surveyCompleteStatus = surveyCompleteStatus;
    }

    public String getSurveyBranch() {
        return surveyBranch;
    }

    public void setSurveyBranch(String surveyBranch) {
        this.surveyBranch = surveyBranch;
    }

    public String getSurveyLocation() {
        return surveyLocation;
    }

    public void setSurveyLocation(String surveyLocation) {
        this.surveyLocation = surveyLocation;
    }
}

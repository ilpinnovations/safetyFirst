package com.ilp.tcs.sitesafety.modals;

/**
 * Created by 1244696 on 6/1/2016.
 * Each Point.
 */
public class Point {
    /**
     * Primary key of the TblCategoryPoint table.
     */
    private long pointId;
    /**
     * Primary key of the TblCategoryPoints table.
     */
    private long categoryId;
    /**
     * number of the survey
     */
    private String pointNumber;
    /**
     * Point constraint of the survey.
     */
    private String constraint;

    private int weight = 1;
    /**
     * The Sub category belongs to which elements.
     */
    private String element;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public String getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(String pointNumber) {
        this.pointNumber = pointNumber;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}

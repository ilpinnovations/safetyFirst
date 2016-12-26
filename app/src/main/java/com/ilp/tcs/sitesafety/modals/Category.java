package com.ilp.tcs.sitesafety.modals;

/**
 * Main survey category
 */
public class Category {

    /**
     * primary Key of the {@link com.ilp.tcs.sitesafety.database.TblCategories}
     */
    private long categoryId;
    /**
     * It contain the Category Number of the each Category.
     */
    private String categoryNumber;
    /**
     * It contains the name of each Category.
     */
    private String categoryName;

    /**
     * @return It will return Category Id.
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId primary key of the TblCategories to set
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return CategoryNumber Category number of main categorises of the survey
     */
    public String getCategoryNumber() {
        return categoryNumber;
    }

    /**
     * @param categoryNumber category number to set to main category
     */
    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    /**
     * @return It will return the category name.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName category name to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

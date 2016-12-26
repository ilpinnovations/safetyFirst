package com.ilp.tcs.sitesafety.modals;

import java.util.ArrayList;

/**
 * Created by 1241575 on 5/31/2016.
 * ChildItem for the ExpandableListView
 */
public class ChildItem {
    /**
     * It holds a long type pointId.
     */
    private long pointId;
    /**
     * This String type holds the title
     */
    private String title;
    /**
     * This holds the response for the survey
     */
    private int response;
    /**
     * This hold the element for the survey
     */
    private String element;

    /**
     * Weight associated with each category point
     * Let it be one by default
     */
    private int weight;
    /**
     * this holds the description given by the auditor
     */
    private String description;
    /**
     * It is an arrayList which hold the image object.
     */
    private ArrayList<Image> images;
    /**
     * This boolean holds the response values.
     */
    private boolean isResponseGiven = false;
    /**
     * This boolean holds is Expanded condition/State
     */
    private boolean isExpanded = false;

    public ChildItem() {
        images = new ArrayList<>();
    }

    public ChildItem(long pointId, String title, int response, String element, int weight) {
        images = new ArrayList<>();
        this.pointId = pointId;
        this.title = title;
        this.response = response;
        this.element = element;
        this.weight = weight;
        this.description = description;
    }

    /**
     * @return boolean state of Expandable ListView
     */
    public boolean isExpanded() {
        return isExpanded;
    }

    /**
     * @param isExpanded description expand flag
     */
    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public boolean isResponseGiven() {
        return isResponseGiven;
    }

    public void setIsResponseGiven(boolean isResponseGiven) {
        this.isResponseGiven = isResponseGiven;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void setImage(Image image) {
        this.images.add(image);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}

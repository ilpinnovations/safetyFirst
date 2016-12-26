package com.ilp.tcs.sitesafety.modals;

import java.util.ArrayList;

/**
 * Created by 1241575 on 5/31/2016.
 * GroupItem
 */
public class GroupItem {
    /**
     * This long holds the id of the survey
     */
    private long id;
    /**
     * this holds title of the survey
     */
    private String title;
    /**
     * This hold the integer value of an image
     */
    private int image;
    private ArrayList<ChildItem> childItems;

    public GroupItem() {
        childItems = new ArrayList<>();
    }

    public GroupItem(long id, String title) {
        this.id = id;
        this.childItems = new ArrayList<>();
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    /**
     * @return it returns the counted value for the Expandilbe Listview counter
     */
    public int getCounter() {
        int counter = 0;
        for (ChildItem item : childItems) {
            if (item.isResponseGiven()) {
                counter++;
            }
        }
        return counter;
    }

    public ArrayList<ChildItem> getChildItems() {
        return childItems;
    }

    public void setChildItems(ArrayList<ChildItem> childItems) {
        this.childItems = childItems;
    }

    public void setChildItem(ChildItem child) {
        this.childItems.add(child);
    }

    public int getChildCount() {
        return childItems.size();
    }

    public ChildItem getChildItem(int position) {
        return childItems.get(position);
    }
}

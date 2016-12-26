package com.ilp.tcs.sitesafety.modals;

/**
 * Created by 1119243 on 11/1/2016.
 */

public class OFIBeans {

    private String details;
    private String category;
    private String ref_no;
    //=====================================
    private String remarks;
//=========================================
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }
    //=============================================================
    public void setRemarks(String remarks){this.remarks = remarks; }
    public String getRemarks(){return  remarks;}
    //=============================================================
}

package com.ilp.tcs.sitesafety.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.ilp.tcs.sitesafety.R;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

/**
 * created by 1244690
 * This class is used for preview of pdf generated .This activity extends another activity PdfViewerActivity
 * which is syatem defined activity included inside the external library.
 */
public class PreviewActivity extends PdfViewerActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    /**
     * @return returning an integer value for the drawable image of left_arrow
     */
    public int getPreviousPageImageResource() {
        return R.drawable.left_arrow;
    }

    /**
     * @return returning an integer value for the drawable image of right_arrow
     */
    public int getNextPageImageResource() {
        return R.drawable.right_arrow;
    }

    /**
     * @return returning an integer value for the drawable image of zoom_in
     */
    public int getZoomInImageResource() {
        return R.drawable.zoom_in;
    }

    /**
     * @return returning an integer value for the drawable image of zoom_out
     */
    public int getZoomOutImageResource() {
        return R.drawable.zoom_out;
    }

    /**
     * @return returning an integer value for the drawable image of pdf_file_password
     */
    public int getPdfPasswordLayoutResource() {
        return R.layout.pdf_file_password;
    }

    /**
     * @return returning an integer value for the drawable image of dialog_pagenumber
     */
    public int getPdfPageNumberResource() {
        return R.layout.dialog_pagenumber;
    }

    /**
     * @return returning an integer value for the drawable image of etPassword
     */
    public int getPdfPasswordEditField() {
        return R.id.etPassword;
    }

    /**
     * @return returning an integer value for the drawable image of btOK
     */
    public int getPdfPasswordOkButton() {
        return R.id.btOK;
    }

    /**
     * @return returning an integer value for the drawable image of btExit
     */
    public int getPdfPasswordExitButton() {
        return R.id.btExit;
    }

    /**
     * @return returning an integer value for the drawable image of pagenum_edit
     */
    public int getPdfPageNumberEditField() {
        return R.id.pagenum_edit;
    }
}

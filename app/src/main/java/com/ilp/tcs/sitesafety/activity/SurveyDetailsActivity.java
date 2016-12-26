package com.ilp.tcs.sitesafety.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.adapters.SurveyHistoryDetailsAdapter;
import com.ilp.tcs.sitesafety.database.DbHelper;
import com.ilp.tcs.sitesafety.database.TblSurvey;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GenerateExcel;
import com.ilp.tcs.sitesafety.modals.GenerateOFIExecel;
import com.ilp.tcs.sitesafety.modals.GenerateOFIPdf;
import com.ilp.tcs.sitesafety.modals.GeneratePdf;
import com.ilp.tcs.sitesafety.modals.GroupItem;
import com.ilp.tcs.sitesafety.modals.Survey;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.Util;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import com.ilp.tcs.sitesafety.R;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

//======updated by nikhil...

/**
 * This activity will show survey details and is used for generating report.
 */
public class SurveyDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * This is a String type variable that holds the value of pdfPath.
     */
    String pdfPath;

    String ofi_pdf_path;

    String ofi_excel_path;

    /**
     * This is a String type variable that holds the value of excelPath.
     */
    String excelPath;

    /**
     * This variable holds the current survey details
     */
    Survey mSurvey;

    /**
     * This variable holds the date format
     */
    SimpleDateFormat simpleDateFormat;

    /**
     * This long type variable holds the value of surveyId.
     */
    private long surveyId;

    /**
     * It is a button which will sense the click event for preview of Report Generated.
     */
    private Button btnPreview;


    private Button btnGraph;

    /**
     * This variable holds the pdf file
     */
//    private File mFile;

    /**
     * This variable holds the excel file
     */
//    private File excelFile;

    private Button btnSendReport;

    private Button mBtnMarkAsCompleted;

    private Button mBtnEdit;

    //============================
    private LinearLayout linearLayout;
    //=============================

    ArrayList<GroupItem> groupItemsToList;
    ProgressDialog progressDialog ;

    Handler handler = new Handler();

    //=============================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.survey_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //===================================================


        progressDialog = new ProgressDialog(SurveyDetailsActivity.this);
        //====================================================

        btnPreview = (Button) findViewById(R.id.btnPreview);
        btnSendReport = (Button) findViewById(R.id.btnSendReport);
        mBtnEdit = (Button) findViewById(R.id.editButton);
        btnGraph = (Button) findViewById(R.id.tv_bargraph);
        surveyId = SitePreference.getInstance().getSurveyId();
        btnPreview.setOnClickListener(this);
        btnSendReport.setOnClickListener(this);
        //====================================================
        linearLayout = (LinearLayout) findViewById(R.id.linera_layout_survey_details);
        //====================================================

        mBtnMarkAsCompleted = (Button) findViewById(R.id.btn_mark_as_compltd);

        TextView mTitle = (TextView) findViewById(R.id.tvSurveyTitle);
        mBtnMarkAsCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if (surveyId == 0 || surveyId == -1)
                return;
            //=======================================================
                //  MarkasComplete asynctask  for fetching detail from database u
                new MarkasCompleteTask().execute();

           //========================================================
            }

        });



        if (surveyId != -1) {
            mSurvey = TblSurvey.getSurveyById(surveyId);




            if (mSurvey != null) {
                mTitle.setText(mSurvey.getSurveyTitle());

                pdfPath = mSurvey.getPdfPath();
                if (Util.isEmpty(pdfPath))
                    btnPreview.setText(R.string.generate_report);
                else {
                    btnPreview.setText(R.string.preview_report);




                }
                //============================================================
                if (!Util.isEmpty(mSurvey.getOfi_excel_path())) {
                    ofi_excel_path = mSurvey.getOfi_excel_path();
                }
                //============================================================
                if (!Util.isEmpty(mSurvey.getExcelPath())) {
                    excelPath = mSurvey.getExcelPath();
                }
            }
        }


        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
//        Survey survey = TblSurvey.getSurveyById(surveyId);
        if (mSurvey != null) {
            Map<String, String> surveyDetails = new LinkedHashMap<>();
            surveyDetails.put("Created By", mSurvey.getUserName());
            surveyDetails.put("Created on", simpleDateFormat.format(mSurvey.getCreatedDateTime()));
            surveyDetails.put("Updated By", mSurvey.getUpdatedBy());
            surveyDetails.put("Updated on", simpleDateFormat.format(mSurvey.getUpdatedDateTime()));
            surveyDetails.put("Branch", mSurvey.getSurveyBranch());
            surveyDetails.put("Location", mSurvey.getSurveyLocation());

            ListView lv = (ListView) findViewById(R.id.lvSurveyDetails);
            lv.setAdapter(new SurveyHistoryDetailsAdapter(surveyDetails));
        }

        if (mSurvey != null && mSurvey.getSurveyCompleteStatus().contentEquals("0")) {
            mBtnEdit.setVisibility(View.VISIBLE);
            mBtnEdit.setVisibility(View.VISIBLE);
            mBtnEdit.setOnClickListener(this);

            //================================================
            //   mBtnMarkAsCompleted.setOnClickListener(this);
            //================================================
        } else {
            mBtnEdit.setText(R.string.scorecard);
            mBtnEdit.setOnClickListener(this);
//            mBtnEdit.setVisibility(View.GONE);

           // btnSendReport.setVisibility(View.VISIBLE);
            mBtnMarkAsCompleted.setVisibility(View.GONE);
        }
//================================================================================================
      /*  if (mSurvey !=null && mSurvey.getSurveyCompleteStatus().contentEquals("1")){

            btnSendReport.setVisibility(View.VISIBLE);
        }*/
//=================================================================================================
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //===============================================================================================================================
                if (surveyId != -1) {
                    mSurvey = TblSurvey.getSurveyById(surveyId);
                    if (mSurvey != null) {


                        pdfPath = mSurvey.getPdfPath();
                        if (Util.isEmpty(pdfPath)) {

                            Snackbar snackbar = Snackbar.make(linearLayout, "Generate Report then try again...", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        } else {


                            //  Toast.makeText(SurveyDetailsActivity.this,"btngraph",Toast.LENGTH_SHORT).show();
                            Log.i("BarGraph", "OnClick");

                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.setMessage("generating graph...");

                            ArrayList<GroupItem> groupItemslist = null;

                            new GraphCreationTask().execute(groupItemslist,null,null);

                            //  btnPreview.setText(R.string.preview_report);
                        }
                        //============================================================

                    }
                }


            }

            //===========================================================================================================================
        });
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doOnBackPress();
        }
        return false;
    }*/

  /*  private void doOnBackPress() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_history) {
            startActivity(new Intent(this, CompletedSurveyActivity.class));
        } else if (id == R.id.action_setting) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.action_change_location) {
            startActivity(new Intent(this, LocationSelectionActivity.class));
            finish();
        } else if (id == R.id.action_servey_details_logout) {
            android.app.AlertDialog.Builder logoutDialog = new android.app.AlertDialog.Builder(SurveyDetailsActivity.this);
            logoutDialog.setTitle("Do you want to logout ?");
            logoutDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    try {

                        SurveyDetailsActivity.this.finish();
                        System.exit(0);
                    } catch (Exception ex) {
                        Toast.makeText(SurveyDetailsActivity.this, "Error while logout", Toast.LENGTH_SHORT).show();
                    }
                }

            });

            logoutDialog.setNegativeButton(android.R.string.cancel, null);
            logoutDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.btnPreview) {
            if (Util.isEmpty(pdfPath)) {
                /*String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                File myDir = new File(root + "/HSE");
                if (!myDir.isDirectory()) {
                    //noinspection ResultOfMethodCallIgnored
                    myDir.mkdirs();
                }*/
//                File pdfFile = new File(myDir, "Survey" + DbHelper.getRowCount(DbHelper.getInstance().getWritableDatabase(), TblSurvey.TABLE_NAME) + ".pdf");
//                String path = PathUtil.getPath(SiteSafetyApplication.getContext(), Uri.fromFile(pdfFile));
                String path = SitePreference.getInstance().getPdfPath();

                String ofi_pdf_path = path.substring(0, path.lastIndexOf("/") + 1) + "ofi_" + path.substring(path.lastIndexOf("/") + 1);


                Log.i("surveyid", "=" + surveyId);
                DbHelper.logPrintTable(TblSurvey.TABLE_NAME);

                groupItemsToList = DbHelper.getSurveyDetails(surveyId);

               /* File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/HSE");


                if (!directory.isDirectory()) {
                    //noinspection ResultOfMethodCallIgnored
                    directory.mkdirs();
                }*/

//                final String fileName = "Survey" + DbHelper.getRowCount(DbHelper.getInstance().getWritableDatabase(), TblSurvey.TABLE_NAME) + ".xls";
//                File excelFile = new File(directory, fileName);
//                String excelPath = PathUtil.getPath(SiteSafetyApplication.getContext(), Uri.fromFile(excelFile));
                //===========================================================================================
                String ofiExcelPath = SitePreference.getInstance().getOfiExcelPath();

                if (Util.isEmpty(path) || Util.isEmpty(ofiExcelPath)) {
                    SToast.showSmallToast("something went wrong");
                    return;
                }

                GenerateOFIExecel generateOFIExecel = new GenerateOFIExecel(mSurvey, groupItemsToList, new File(ofiExcelPath));
                generateOFIExecel.execute();
                //============================================================================================
                String excelPath = SitePreference.getInstance().getExcelPath();
                if (Util.isEmpty(path) || Util.isEmpty(excelPath)) {
                    SToast.showSmallToast("Something went wrong");
                    return;
                }
                GenerateExcel generateExcel = new GenerateExcel(mSurvey, groupItemsToList, new File(excelPath));
                generateExcel.execute();

                if (mSurvey != null) {
                    GeneratePdf generatePdf = new GeneratePdf(mSurvey, groupItemsToList, SurveyDetailsActivity.this, new File(path));
                    generatePdf.execute();
                    //  GenerateOFIPdf generate_ofi_Pdf = new GenerateOFIPdf(mSurvey, groupItemsToList, SurveyDetailsActivity.this, new File(ofi_pdf_path));
                    // generate_ofi_Pdf.execute();
                   /* GenerateOFIExecel generateOFIExecel2 = new GenerateOFIExecel(mSurvey,groupItemsToList,new File(ofiExcelPath));
                    generateOFIExecel2.execute();*/

                }


              //  btnGraph.setVisibility(View.VISIBLE);
                //====================================
                this.ofi_excel_path = ofiExcelPath;
                //====================================
                this.pdfPath = path;
                this.ofi_pdf_path = ofi_pdf_path;
                this.excelPath = excelPath;

                btnPreview.setText(R.string.preview_report);

               ContentValues contentValues = new ContentValues();
                contentValues.put(TblSurvey.COLUMN_PDF_PATH, path);
                //contentValues.put(TblSurvey.COLUMN_OFI_PDF_PATH,ofi_pdf_path);
                contentValues.put(TblSurvey.COLUMN_EXCEL_PATH, excelPath);

                //======================================================================
                contentValues.put(TblSurvey.COLUMN_OFI_PDF_PATH, ofi_excel_path);

                //======================================================================
          DbHelper helper = DbHelper.getInstance();

                DbHelper.update(helper.getWritableDatabase(), TblSurvey.TABLE_NAME, contentValues, surveyId);

                //================================================================
                mSurvey.setOfi_excel_path(ofi_pdf_path);
                // mSurvey.setOfi_excel_path(ofiExcelPath);
                //================================================================
                mSurvey.setPdfPath(path);
                // mSurvey.setOFIPdfPath(ofi_pdf_path);
                mSurvey.setExcelPath(excelPath);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Preview report");
                builder.setMessage("Please select report to preview.");
                builder.setPositiveButton("PDF", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Util.isEmpty(pdfPath))
                            return;

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(pdfPath)), "application/pdf");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Intent myIntent = new Intent(SurveyDetailsActivity.this, PreviewActivity.class);
                            myIntent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, pdfPath);
                            startActivity(myIntent);
                        }
                    }
                });
                builder.setNegativeButton("EXCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Util.isEmpty(excelPath))
                            return;
                        Log.i("execl", excelPath);
                        //   Toast.makeText(SurveyDetailsActivity.this,excelPath,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(excelPath)), "application/vnd.ms-excel");

                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            SToast.showSmallToast("No application available to view Excel report");
                        }
                    }
                });

                //===================================================================================

                builder.setNeutralButton("OFI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*if (Util.isEmpty(ofi_excel_path)){
                            SToast.showSmallToast(ofi_excel_path);
                            return;}*/
                        StringTokenizer st = new StringTokenizer(excelPath, ".");
                        String str = st.nextToken() + "ofi.xls";
                        Log.i("OFiexecl", str);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(str)), "application/vnd.ms-excel");

                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            SToast.showSmallToast("No application available to view Excel report");
                        }
                    }
                });
                //===================================================================================

                //neutral button for OFI
          /*      builder.setNeutralButton("OFI", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Util.isEmpty(ofi_pdf_path))
                            return;

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(ofi_pdf_path)),"application/pdf");


                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {

                            Log.i("Exception: ServeyAct",e.getMessage().toString());
                            Intent myIntent = new Intent(SurveyDetailsActivity.this, PreviewActivity.class);
                            myIntent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, ofi_pdf_path);
                            startActivity(myIntent);
                        }
                    }
                });*/
                builder.show();
            }
        } else if (v.getId() == R.id.editButton) {
            if (!mSurvey.getSurveyCompleteStatus().equals("1")) {
                SitePreference.getInstance().setEditFlag(true);
                Intent intent = new Intent(SurveyDetailsActivity.this, MainActivity.class);
                intent.putExtra(TblSurvey._ID, surveyId);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra(TblSurvey._ID, surveyId);
                intent.putExtra(TblSurvey.COLUMN_TITLE, mSurvey.getSurveyTitle());
                startActivity(intent);
            }
        } else if (v.getId() == R.id.btnSendReport && surveyId != -1) {
//            Survey survey = TblSurvey.getSurveyById(surveyId);
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
            if (mSurvey != null && !Util.isEmpty(mSurvey.getPdfPath())) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                // The intent does not have a URI, so declare the "text/plain" MIME type
                emailIntent.setType("text/plain");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Audit reports of " + mSurvey.getSurveyTitle());
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
//                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
                ArrayList<Uri> files = new ArrayList<>();
                /*if (!Util.isEmpty(mSurvey.getPdfPath())) {
                    File pdf = new File(mSurvey.getPdfPath());
                    File rename = new File(pdf.getParent() + File.separator + mSurvey.getSurveyTitle() + ".pdf");
                    if (!pdf.renameTo(rename)) {
                        files.add(Uri.fromFile(pdf));
                    } else {
                        files.add(Uri.fromFile(rename));
                    }
                }
                if (!Util.isEmpty(mSurvey.getExcelPath())) {
                    File excel = new File(mSurvey.getExcelPath());
                    File rename = new File(excel.getParent() + File.separator + mSurvey.getSurveyTitle() + ".xls");
                    if (!excel.renameTo(rename)) {
                        files.add(Uri.fromFile(excel));
                    } else {
                        files.add(Uri.fromFile(rename));
                    }
                }*/

                if (!Util.isEmpty(mSurvey.getPdfPath())) {
                    File pdf = new File(mSurvey.getPdfPath());
                    files.add(Uri.fromFile(pdf));
                }
               /* if (!Util.isEmpty(mSurvey.getOFIPdfPath())) {
                    File ofi_pdf_path = new File(mSurvey.getOFIPdfPath());
                    files.add(Uri.fromFile(ofi_pdf_path));
                }*/
                //============================================================

                if (!Util.isEmpty(mSurvey.getOfi_excel_path())) {
                    Log.i("ofiexecl adding", mSurvey.getOfi_excel_path());
                    File ofiexeclpath = new File(mSurvey.getOfi_excel_path());
                    files.add(Uri.fromFile(ofiexeclpath));
                    //============================================================

                    if (!Util.isEmpty(mSurvey.getExcelPath())) {
                        File excel = new File(mSurvey.getExcelPath());
                        files.add(Uri.fromFile(excel));
                    }
                    emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                    startActivity(emailIntent);
                } else {

                   //===============================================================================================================
                    Snackbar snackbar = Snackbar.make(linearLayout,"Generate Report First Then try Again...",Snackbar.LENGTH_SHORT);
                    snackbar.show();;
                   //===============================================================================================================
                    //SToast.showSmallToast("Please generate report first");
                }
            } else if (v.getId() == R.id.btn_mark_as_compltd) {

                Toast.makeText(SurveyDetailsActivity.this, "cliked mark completed,", Toast.LENGTH_SHORT).show();

            if (surveyId == 0 || surveyId == -1)
                return;


                new MarkasCompleteTask().execute();

         /*       ArrayList<GroupItem> groupItems = DbHelper.getSurveyDetails(surveyId);

                StringBuilder builder = new StringBuilder();
                for (GroupItem groupItem : groupItems) {
                    ArrayList<ChildItem> childItems = groupItem.getChildItems();
                    for (ChildItem childItem : childItems) {
//                    Log.e("response", childItem.getResponse() + "");
                        if (childItem.getResponse() == Constants.UNDEFINED) {        // -1 means NO, 0 means NA and 1 means YES response
                            if (Util.isEmpty(childItem.getDescription())) {      // Undefined response
                                builder.append(childItem.getTitle().subSequence(0, 32)).append("...").append('\n');
                            }
                        }
                    }
                }

                //==================================================
                String details = builder.toString();

                if (!Util.isEmpty(details)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Audit is not complete");
                    dialog.setMessage("You have not done Audit for the following points. \n" + details);
                    dialog.setPositiveButton(android.R.string.ok, null);
                    dialog.show();
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(TblSurvey.IS_COMPLETED, "1");
                    long result = DbHelper.update(DbHelper.getInstance().getWritableDatabase(), TblSurvey.TABLE_NAME, cv, surveyId);

                    if (result > 0) {
                        SToast.showSmallToast("Audit successfully completed");
                        mBtnMarkAsCompleted.setVisibility(View.GONE);
                        mSurvey.setSurveyCompleteStatus("1");
                        mBtnEdit.setText(R.string.scorecard);
                        btnSendReport.setVisibility(View.VISIBLE);
                    } else {
                        SToast.showSmallToast("Audit update failed");
                        mSurvey.setSurveyCompleteStatus("0");
                        mBtnEdit.setVisibility(View.VISIBLE);
                        mBtnEdit.setVisibility(View.VISIBLE);
                    }
                }*/


            }
        }


    }

//========================================AsyncTask for  graph=======================

    private class GraphCreationTask extends AsyncTask<ArrayList<GroupItem> , Void, Void> {

      // ProgressDialog progressDialog = new ProgressDialog(SurveyDetailsActivity.this);
        ArrayList<GroupItem> groupItemsList1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCancelable(false);
            progressDialog.show();
            /*progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("generating graph...");
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.cancel();

        }

        @Override
        protected Void doInBackground(ArrayList<GroupItem>... params) {

           // this.groupItemsList1 = params[0];

            ArrayList<GroupItem> groupItemsList1 = DbHelper.getSurveyDetails(surveyId);

            if (groupItemsList1 != null) {
                // progressDialog.show();
                Log.i("BarGraph", "Inside if");

                Resources resource = SiteSafetyApplication.getContext().getResources();
                ArrayList<String> elements = new ArrayList<>();
                elements.add(resource.getString(R.string.general_safety));
                elements.add(resource.getString(R.string.fire_safety));
                elements.add(resource.getString(R.string.electrical_safety));
                elements.add(resource.getString(R.string.emergency_prep));
                elements.add(resource.getString(R.string.workplace_safety));
                elements.add(resource.getString(R.string.safe_work));
                elements.add(resource.getString(R.string.vehicular_safety));
                elements.add(resource.getString(R.string.contractor_safety));
                elements.add(resource.getString(R.string.health_hygiene));
                elements.add(resource.getString(R.string.Safety_Signage));


                String[] scoreArray = new String[elements.size()];
                int totalYes = 0, totalNo = 0, totalNa = 0, totalPoints = 0;
                int score;
                int i;
                for (i = 0; i < elements.size(); i++) {

                    int no_w = 0, nan_w = 0, yes_w = 0;
                    int no = 0, nan = 0, yes = 0, tp = 0;
                    for (GroupItem groupItem : groupItemsList1) {
                        ArrayList<ChildItem> childItems = groupItem.getChildItems();
                        for (ChildItem childItem : childItems) {
                            if (childItem.getElement().equals(elements.get(i))) {
                                switch (childItem.getResponse()) {
                                    case -1:

                                        no_w += childItem.getWeight();
                                        no += 1;
                                        tp += 1;
                                        break;
                                    case 0:
                                        nan_w += childItem.getWeight();
                                        nan += 1;
                                        break;
                                    case 1:
                                        yes_w += childItem.getWeight();
                                        yes += 1;
                                        tp += 1;
                                        break;
                                }
                            }
                        }

                    }


                    totalYes += yes;
                    totalNo += no;
                    totalNa += nan;
                    totalPoints += tp;
                    score = ((int) (((float) yes / (yes + no)) * 100));

                    scoreArray[i] = String.valueOf(score);

                }


                Intent calIntent = new Intent(SurveyDetailsActivity.this, BarGraphActivity.class);
                calIntent.putExtra("scoreArray", scoreArray);

                startActivity(calIntent);

            }


            return null;
        }

    }

 //==========Aysnctask for mark as completed button====================================================================

    private  class MarkasCompleteTask extends AsyncTask<Void,Void,Void>{
        ProgressDialog pd = new ProgressDialog(SurveyDetailsActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Wait....");
            pd.setCancelable(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           pd.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           pd.cancel();
        }

        @Override
        protected Void doInBackground(Void... params) {



            if (surveyId == 0 || surveyId == -1){
                return null;
            }
            ArrayList<GroupItem> groupItems = DbHelper.getSurveyDetails(surveyId);

            StringBuilder builder = new StringBuilder();
            for (GroupItem groupItem : groupItems) {
                ArrayList<ChildItem> childItems = groupItem.getChildItems();
                for (ChildItem childItem : childItems) {
//                    Log.e("response", childItem.getResponse() + "");
                    if (childItem.getResponse() == Constants.UNDEFINED) {        // -1 means NO, 0 means NA and 1 means YES response
                        if (Util.isEmpty(childItem.getDescription())) {      // Undefined response
                            builder.append(childItem.getTitle().subSequence(0, 32)).append("...").append('\n');
                        }
                    }
                }
            }

         final    String details = builder.toString();

            if (!Util.isEmpty(details)) {
             handler.post(new Runnable() {
                 @Override
                 public void run() {
                     AlertDialog.Builder dialog = new AlertDialog.Builder(SurveyDetailsActivity.this);
                     dialog.setTitle("Audit is not complete");
                     dialog.setMessage("You have not done Audit for the following points. \n" + details);
                     dialog.setPositiveButton(android.R.string.ok, null);
                     dialog.show();
                 }
             });

            } else {
                ContentValues cv = new ContentValues();
                cv.put(TblSurvey.IS_COMPLETED, "1");
                long result = DbHelper.update(DbHelper.getInstance().getWritableDatabase(), TblSurvey.TABLE_NAME, cv, surveyId);

                if (result > 0) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                SToast.showSmallToast("Audit successfully completed");
                                mBtnMarkAsCompleted.setVisibility(View.GONE);
                                mSurvey.setSurveyCompleteStatus("1");
                                mBtnEdit.setText(R.string.scorecard);
                                btnSendReport.setVisibility(View.VISIBLE);
                            }
                        });

                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            SToast.showSmallToast("Audit update failed");
                            mSurvey.setSurveyCompleteStatus("0");
                            mBtnEdit.setVisibility(View.VISIBLE);
                            mBtnEdit.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }
            return null;
        }
    }

//=================================================================================================================

}

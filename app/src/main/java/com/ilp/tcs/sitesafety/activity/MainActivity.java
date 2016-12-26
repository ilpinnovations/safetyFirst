package com.ilp.tcs.sitesafety.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.adapters.ExpandableListAdapter;
import com.ilp.tcs.sitesafety.database.DbHelper;
import com.ilp.tcs.sitesafety.database.SaveTask;
import com.ilp.tcs.sitesafety.database.SaveTaskCallback;
import com.ilp.tcs.sitesafety.database.TblCategories;
import com.ilp.tcs.sitesafety.database.TblCategoryPoints;
import com.ilp.tcs.sitesafety.database.TblSurvey;
import com.ilp.tcs.sitesafety.database.UpdateTask;
import com.ilp.tcs.sitesafety.database.UpdateTaskCallback;
import com.ilp.tcs.sitesafety.listeners.MediaListener;
import com.ilp.tcs.sitesafety.modals.Category;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;
import com.ilp.tcs.sitesafety.modals.Image;
import com.ilp.tcs.sitesafety.modals.Point;
import com.ilp.tcs.sitesafety.modals.Survey;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.utils.PathUtil;
import com.ilp.tcs.sitesafety.utils.PermissionUtil;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.SimpleSpanBuilder;
import com.ilp.tcs.sitesafety.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Modified by Abhishek Gupta on 27/8/2016.
 */
/**
 * Home page
 * This activity contains expandable list view with all survey points
 */
public class MainActivity extends AppCompatActivity implements MediaListener, SaveTaskCallback, UpdateTaskCallback, PermissionUtil.PermissionListener {

    /**
     * this variable stores request code for picking image from gallery
     */


    private static final String TAG = MainActivity.class.getSimpleName();


    private static final int REQUEST_IMAGE_PICK = 2;

    /**
     * this variable stores request code to capture image from camera
     */
    private static final int REQUEST_IMAGE_CAPTURE = 3;

    /**
     * this variable stores request code to update image
     */
    private static final int REQUEST_IMAGE_UPDATE = 4;

    /**
     * This list variable contains list of survey categories
     */
    private ArrayList<GroupItem> groupItems;

    /**
     * This list variable contains list of Survey points
     */
    private ChildItem childItem;


    /**
     * this will hold the expandable listview of survey points
     */
    private ExpandableListAdapter mAdapter;


    /**
     * this variable holds the path of the file in which photos are saved
     */
    private File photoFile;
    private boolean his_discard = false;
    private boolean set_discard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.audit);

        SimpleSpanBuilder spanBuilder = new SimpleSpanBuilder();
        spanBuilder.append("Branch: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(SitePreference.getInstance().getBranchName(), new StyleSpan(Typeface.BOLD))
                .append(" - Location: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(SitePreference.getInstance().getLocationName(), new StyleSpan(Typeface.BOLD));
        TextView mTxtViewBranchAndLocation = (TextView) findViewById(R.id.txt_view_br_loc);
        mTxtViewBranchAndLocation.setText(spanBuilder.build());

        //=========nikhil





        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(null);

        if (!SitePreference.getInstance().getEditFlag()) {

            if (groupItems == null || groupItems.isEmpty()) {

                groupItems = new ArrayList<>();

                ArrayList<Category> categories = TblCategories.getAllCategories();

                if (categories != null) {
                    for (Category c : categories) {
                        GroupItem groupItem = new GroupItem(c.getCategoryId(), c.getCategoryNumber() + " - " + c.getCategoryName());

                        ArrayList<Point> points = TblCategoryPoints.getSubCategoryByCategoryId(c.getCategoryId());

                        if (points != null) {
                            for (Point p : points) {
                                ChildItem childItem = new ChildItem(p.getPointId(), p.getPointNumber() + " - " + p.getConstraint(), Constants.UNDEFINED, p.getElement(), p.getWeight());
                                groupItem.setChildItem(childItem);
                            }
                        }

                        groupItems.add(groupItem);
                    }
                }
            }
        } else if (getIntent().hasExtra(TblSurvey._ID)) {
            long surveyId = getIntent().getLongExtra(TblSurvey._ID, -1);

            if (surveyId != -1) {
                Survey survey = TblSurvey.getSurveyById(surveyId);
                if (survey != null && !Util.isEmpty(survey.getSurveyTitle())) {
                    setTitle(survey.getSurveyTitle());
                } else {
                    setTitle("Survey" + surveyId);
                }
                groupItems = DbHelper.getSurveyDetails(surveyId);
            }
        }

        try {
            mAdapter = new ExpandableListAdapter(this, groupItems, this);
            expandableListView.setAdapter(mAdapter);
        } catch (Exception e) {
            SitePreference.getInstance().setEditFlag(false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_save) {
            String desc = isValidInputs();
            if (!Util.isEmpty(desc)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Description missing");
                builder.setMessage("You have selected NO but not given description for the following responses. \n" + desc);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.show();
            } else if (SitePreference.getInstance().getEditFlag()) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Save audit");

                final EditText input = new EditText(this);
                input.setHint("Enter audit name");
                if (getIntent().hasExtra(TblSurvey._ID)) {
                    long surveyId = getIntent().getLongExtra(TblSurvey._ID, -1);
                    if (surveyId != -1) {
                        Survey survey = TblSurvey.getSurveyById(surveyId);
                        if (survey != null && !Util.isEmpty(survey.getSurveyTitle())) {
                            input.setText(survey.getSurveyTitle());
                        }
                    }
                }

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(10, 12, 10, 12);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String name = input.getText().toString().trim();
                                if (name.length() > 0) {
//                                    if (!isAlreadyExist(name)) {
                                    UpdateTask UpdateTaskObject = new UpdateTask(MainActivity.this, MainActivity.this, groupItems, name);
                                    UpdateTaskObject.execute();
                                    /*} else {
                                        SToast.showSmallToast("Audit already exist");
                                    }*/
                                } else {
                                    SToast.showSmallToast(R.string.survey_check_msg);
                                }
                            }
                        });

                alertDialog.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Save Audit");

                final EditText input = new EditText(this);
                input.setHint("Enter audit name");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(10, 12, 10, 12);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                String name = input.getText().toString().trim();
                                if (name.length() > 0) {
                                    if (!isAlreadyExist(name)) {
                                        SaveTask saveTaskObject = new SaveTask(MainActivity.this, MainActivity.this, groupItems, name);
                                        saveTaskObject.execute();
                                    } else {
                                        SToast.showSmallToast("Audit already exist");
                                    }
                                } else {
                                    SToast.showSmallToast(R.string.survey_check_msg);
                                }
                            }
                        });

                alertDialog.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        } else if (id == R.id.action_history) {
            his_discard = true;
            onBackPressed();
            //startActivity(new Intent(this, CompletedSurveyActivity.class));
        } else if (id == R.id.action_setting) {
           // set_discard = true;
          //  onBackPressed();
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.action_change_location) {
            startActivity(new Intent(this, LocationSelectionActivity.class));
            finish();
        } else if(id == R.id.action_main_activity_logout) {
            android.app.AlertDialog.Builder logoutDialog = new android.app.AlertDialog.Builder(MainActivity.this);
            logoutDialog.setTitle("Do you want to logout ?");
            logoutDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.logout_preference), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(getString(R.string.logout_key), true);
                        editor.commit();
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(MainActivity.this,"Error while logout",Toast.LENGTH_SHORT).show();
                    }
                }

            });

            logoutDialog.setNegativeButton(android.R.string.cancel, null);
            logoutDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isAlreadyExist(String name) {
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/HSE");
        if (!directory.isDirectory()) {
            //noinspection ResultOfMethodCallIgnored
            directory.mkdirs();
        }

        String pdf = name + ".pdf";
        String excel = name + ".xls";
        File pdfFile = new File(directory, pdf);
        File excelFile = new File(directory, excel);
        //==================================================================
        String ofiExecl = name +"ofi" + ".xls";
        File ofiExeclFile = new File(directory,ofiExecl);

        //==================================================================
        // ofiexecelfile.exists() is added by me
        boolean exists = pdfFile.exists() || excelFile.exists()  || ofiExeclFile.exists();

        if (!exists) {
            SitePreference.getInstance().setPdfPath(PathUtil.getPath(SiteSafetyApplication.getContext(), Uri.fromFile(pdfFile)));
            SitePreference.getInstance().setExcelPath(PathUtil.getPath(SiteSafetyApplication.getContext(), Uri.fromFile(excelFile)));
            //=========================================================
            SitePreference.getInstance().setOFiExcelPath(PathUtil.getPath(SiteSafetyApplication.getContext(),Uri.fromFile(ofiExeclFile)));
            //=========================================================
        } else {
            SitePreference.getInstance().removeFilePath();
        }
        return exists;
    }

    private String isValidInputs() {
        StringBuilder builder = new StringBuilder();
        for (GroupItem groupItem : groupItems) {
            ArrayList<ChildItem> childItems = groupItem.getChildItems();
            for (ChildItem childItem : childItems) {
                if (childItem.getResponse() == -1) {        // -1 means NO response, 0 means NA and 1 means YES
                    if (Util.isEmpty(childItem.getDescription())) {      // NO response but description is not given
                        builder.append(childItem.getTitle().subSequence(0, 32)).append("...").append('\n');
                    }
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void onOpenGallery(ChildItem childItem) {
        this.childItem = childItem;
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                     // android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);


        }

        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onOpenCamera(ChildItem childItem) {
        this.childItem = childItem;

        if (PermissionUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openCamera();

        } else if (PermissionUtil.isPermanentlyDisabled(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionUtil.showPermissionDialog(this, "Permission denied", "We need permission to save captured image in sdcard." +
                    " Please enable external storage permission");
        } else {
            PermissionUtil.setPermissionListener(this);
            PermissionUtil.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = Util.getExternalImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
                photoFile = Util.getInternalImageFile();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            SToast.showSmallToast("Can't connect to camera.");
        }
    }

    @Override
    public void onUpdateImage(String path, String caption) {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra(ImageDisplayActivity.IMAGE_PATH, path);
        intent.putExtra(ImageDisplayActivity.EXTRA_CAPTION, caption);
        startActivityForResult(intent, REQUEST_IMAGE_UPDATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        String imgNum = "1";
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && childItem != null) {
            Image image = new Image();
            String path = PathUtil.getPath(this, data.getData());
            if (path != null) {
                File file1;
                File fl = new File(path);
                File file = new File(Environment.getExternalStorageDirectory()+"/HSE/IMAGE"+path.substring(path.lastIndexOf("/"),path.lastIndexOf("."))
                                 +"_"+imgNum+path.substring(path.lastIndexOf(".")));


                Log.i(TAG, "Fl: " + fl + "\nFile: " + file);

                while(file.exists()){
                    String check = file.getAbsolutePath();
                    check = check.substring(check.lastIndexOf("_")+1,check.lastIndexOf("."));
                    int i = Integer.parseInt(check);
                    i+=1;
                    imgNum = String.valueOf(i);

                    file = new File(Environment.getExternalStorageDirectory()+"/HSE/IMAGE"+path.substring(path.lastIndexOf("/"),path.lastIndexOf("."))
                            +"_"+imgNum+path.substring(path.lastIndexOf(".")));

                    Log.i(TAG, "File Loop: "+file);
                }


                file1 = new File(Environment.getExternalStorageDirectory()+"/HSE/IMAGE"+path.substring(path.lastIndexOf("/"),path.lastIndexOf("."))
                        +"_"+imgNum+path.substring(path.lastIndexOf(".")));

                Log.i(TAG, "File1: "+file1);

                String absolutePath = "file:" + file1.getAbsolutePath();
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(fl.getAbsolutePath(),bmOptions);
                int hei = bitmap.getHeight();
                int wid = bitmap.getWidth();
              /*  if(hei > height){
                    bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
                }
                else {
                    bitmap = Bitmap.createScaledBitmap(bitmap,width,hei,true);
                }
*/
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file1);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Problem occur while selecting image",Toast.LENGTH_SHORT).show();
                }
                image.setImagePath(absolutePath);
                image.setLocation(SitePreference.getInstance().getLocationName());
                childItem.setImage(image);
                mAdapter.notifyDataSetChanged();
            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && childItem != null) {
            String absolutePath = "file:" + photoFile.getAbsolutePath();
            /*               compress image and reduce image quality*/
           BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(),bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(photoFile);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Image image = new Image();
            image.setImagePath(absolutePath);
            image.setLocation(SitePreference.getInstance().getLocationName());
            childItem.setImage(image);
            mAdapter.notifyDataSetChanged();
            Util.scanMedia(this, photoFile.getPath());
        } else if (requestCode == REQUEST_IMAGE_UPDATE && resultCode == RESULT_OK) {
            if (data.hasExtra(ImageDisplayActivity.IMAGE_PATH)) {
                String path = data.getStringExtra(ImageDisplayActivity.IMAGE_PATH);
                String caption = data.getStringExtra(ImageDisplayActivity.EXTRA_CAPTION);
                if (data.getBooleanExtra(ImageDisplayActivity.IS_DELETED, false)) {
                    for (GroupItem groupItem : groupItems) {
                        ArrayList<ChildItem> childItems = groupItem.getChildItems();
                        for (ChildItem childItem1 : childItems) {
                            for (int i = 0; i < childItem1.getImages().size(); i++) {
                                if (childItem1.getImages().get(i).getImagePath().equals(path)) {
                                    childItem1.getImages().remove(i);
                                }
                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    for (GroupItem groupItem : groupItems) {
                        ArrayList<ChildItem> childItems = groupItem.getChildItems();
                        for (ChildItem childItem1 : childItems) {
                            for (int i = 0; i < childItem1.getImages().size(); i++) {
                                if (childItem1.getImages().get(i).getImagePath().equals(path)) {
                                    childItem1.getImages().get(i).setCaption(caption);
                                }
                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Audit");
        builder.setMessage("Your audit will be discarded. Continue ?");
        builder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SitePreference.getInstance().setEditFlag(false);
                SitePreference.getInstance().setSurveyId(-1);
                finish();
                if(his_discard){
                  startActivity(new Intent(getBaseContext(), CompletedSurveyActivity.class));
                }
         /*       else if(set_discard){
                    startActivity(new Intent(getBaseContext(), SettingsActivity.class));
                }*/
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    @Override
    public void onSaveComplete(long surveyId) {
        if (surveyId != -1) {
            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
//            DbHelper.logPrintTable(TblImages.TABLE_NAME);
            Intent intent = new Intent(MainActivity.this, SurveyDetailsActivity.class);
            SitePreference.getInstance().setSurveyId(surveyId);
            intent.putExtra(Constants.SURVEY_ID, surveyId);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Could Not Save Survey", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdateComplete(Long surveyId) {
        SitePreference.getInstance().setEditFlag(false);
        if (surveyId != -1) {
            Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show();
            SitePreference.getInstance().setSurveyId(surveyId);
            Intent intent = new Intent(MainActivity.this, SurveyDetailsActivity.class);
            intent.putExtra(Constants.SURVEY_ID, surveyId);
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "Could Not Update Survey", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(String permission) {
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
            openCamera();
        }
    }
//TODO Marshmellow run time permission stack need to implemented
    @Override
    public void onPermissionDenied(String permission) {
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
            PermissionUtil.showPermissionDialog(this, "Permission denied", "We need permission to save captured image in sdcard." +
                    " Please enable external storage permission");
        }
    }
}

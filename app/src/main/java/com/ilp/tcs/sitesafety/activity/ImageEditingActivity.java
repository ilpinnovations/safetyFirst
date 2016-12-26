package com.ilp.tcs.sitesafety.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.database.DbHelper;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.utils.Util;
import com.ilp.tcs.sitesafety.views.PaintView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *  Modified by Abhishek Gupta on 24/8/2016.
 */
/**
 * This class is responsible for image editing activity using drawing paint or canvas
 */
public class ImageEditingActivity extends AppCompatActivity {

    private long surveyId;
    private ArrayList<GroupItem> groupItemsArrayList = new ArrayList<>();


    /**
     * This String contains the path of image
     */
    public static final String IMAGE_PATH = "imagePath";

    /**
     * Layout containing colors that can be selected by user
     */
    private LinearLayout colorPallete;

    /**
     * This variable will hold the initial value of the pallete
     */
    private boolean palleteIsVisible = false;

    /**
     * Output stream to write image after editing
     */
    private OutputStream fOut;

    /**
     * Custom paint view that allow drawing on canvas.
     */
    private PaintView drawView;
    private ImageButton currPaint, brushButton,/* eraseButton,*/ saveBtn;
    private Animation animShow, animHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editing);
        drawView = (PaintView) findViewById(R.id.drawing);
   /*     Intent imageIntent = getIntent();

       final String imagePath = imageIntent.getStringExtra(IMAGE_PATH);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inMutable = true;
       Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

//        Bitmap bitmapRotated = Util.handleExifRotation(bitmap, imagePath);

        DisplayMetrics display = SiteSafetyApplication.getContext().getResources().getDisplayMetrics();
        Bitmap bmScaled = Bitmap.createScaledBitmap(bitmap, display.widthPixels, display.heightPixels, false);
        bitmap.recycle();

        drawView.setCanvasBitmap(bmScaled);
//        eraseButton = (ImageButton) findViewById(R.id.eraser);
        brushButton = (ImageButton) findViewById(R.id.brush);

        saveBtn = (ImageButton) findViewById(R.id.save);
        colorPallete = (LinearLayout) findViewById(R.id.color_pallete_layout);
        initAnimation();
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePallet();
            }
        });
      //  eraseButton.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         drawView.startNew();
      //      }
      //  })
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(ImageEditingActivity.this);
                saveDialog.setTitle("Save Image");
                saveDialog.setMessage("Save Edited Image ?");
                saveDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//Author : Abhishek Gupta(1094048)
//TODO
//* Date On : 16-aug-2016
                        String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                        Log.i("Filepath",filename);
                        String absolutePath2 = imagePath.substring(0,imagePath.lastIndexOf("/")+1)+
                                filename.substring(0,filename.lastIndexOf("."))+"_orig"+filename.substring(filename.lastIndexOf("."));
                        Log.i("Filepath",absolutePath2);
                        File src = new File(imagePath);
                        File dst = new File(absolutePath2);

                        if(src.exists()){
                            try {
                                FileChannel srch = new FileInputStream(src).getChannel();
                                FileChannel dsth = new FileOutputStream(dst).getChannel();
                                dsth.transferFrom(srch,0,srch.size());
                                srch.close();
                                dsth.close();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                        else {
                            Toast.makeText(getBaseContext(),"File Not Exist",Toast.LENGTH_SHORT).show();
                        }

                        drawView.setDrawingCacheEnabled(true);
                        Bitmap bmp = drawView.getDrawingCache();
                        try {
                            String absolutePath = "file:" + new File(imagePath).getAbsolutePath();
                            fOut = getContentResolver().openOutputStream(Uri.parse(absolutePath));
                            // reducing quality to 85 percent
                            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                            File newFile = new File(absolutePath);
                            newFile.setLastModified(new Date().getTime());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fOut.flush();
                            fOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        drawView.destroyDrawingCache();
                        setResult(RESULT_OK);
                        finish();
                        Util.scanMedia(ImageEditingActivity.this, absolutePath2);
                    }
                });
                saveDialog.setNegativeButton(android.R.string.cancel, null);
                saveDialog.show();
            }
        });

        */
    }


    @Override
    protected void onResume() {
        super.onResume();


        Intent imageIntent = getIntent();
        final String imagePath = imageIntent.getStringExtra(IMAGE_PATH);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        DisplayMetrics display = SiteSafetyApplication.getContext().getResources().getDisplayMetrics();
        Bitmap bmScaled = Bitmap.createScaledBitmap(bitmap, display.widthPixels, display.heightPixels, false);
        bitmap.recycle();

        drawView.setCanvasBitmap(bmScaled);
//        eraseButton = (ImageButton) findViewById(R.id.eraser);
        brushButton = (ImageButton) findViewById(R.id.brush);

        saveBtn = (ImageButton) findViewById(R.id.save);
        colorPallete = (LinearLayout) findViewById(R.id.color_pallete_layout);
        initAnimation();
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePallet();
            }
        });
        //  eraseButton.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         drawView.startNew();
        //      }
        //  })
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(ImageEditingActivity.this);
                saveDialog.setTitle("Save Image");
                saveDialog.setMessage("Save Edited Image ?");
                saveDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//Author : Abhishek Gupta(1094048)
//TODO
//* Date On : 16-aug-2016
                        String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                        Log.i("Filepath filename",filename);
                        String absolutePath2 = imagePath.substring(0,imagePath.lastIndexOf("/")+1)+
                                filename.substring(0,filename.lastIndexOf("."))+"_orig"+filename.substring(filename.lastIndexOf("."));
                        Log.i("Filepath absolutePath2",absolutePath2);
                        Log.i("Filepath imagePath",imagePath);
                        File src = new File(imagePath);
                        File dst = new File(absolutePath2);

                        if(src.exists()){
                            try {
                                FileChannel srch = new FileInputStream(src).getChannel();
                                FileChannel dsth = new FileOutputStream(dst).getChannel();

                                dsth.transferFrom(srch,0,srch.size());
                                srch.close();
                                dsth.close();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                        else {
                            Toast.makeText(getBaseContext(),"File Not Exist",Toast.LENGTH_SHORT).show();
                        }

                        drawView.setDrawingCacheEnabled(true);
                        Bitmap bmp = drawView.getDrawingCache();
                        try {
                            String absolutePath = "file:" + new File(imagePath).getAbsolutePath();
                            Log.i("Filepath absolutePath2",absolutePath2);
                            fOut = getContentResolver().openOutputStream(Uri.parse(absolutePath));
                            // reducing quality to 85 percent
                            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                            File newFile = new File(absolutePath);
                            newFile.setLastModified(new Date().getTime());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fOut.flush();
                            fOut.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Util.scanMedia(ImageEditingActivity.this, absolutePath2);

           /*             surveyId = SitePreference.getInstance().getSurveyId();
                        Log.i("serveyId","S:"+surveyId);
                        groupItemsArrayList = DbHelper.getSurveyDetails(surveyId);




                        for (int i = 0; i < groupItemsArrayList.size(); i++) {
                            GroupItem groupItem = groupItemsArrayList.get(i);
                            ArrayList<ChildItem> childItems = groupItem.getChildItems();

                            for (ChildItem childItem : childItems) {

                                ArrayList<com.ilp.tcs.sitesafety.modals.Image> ar = childItem.getImages();
                                for (com.ilp.tcs.sitesafety.modals.Image image : ar) {

                                    Bitmap resizedBitmap = Util.getScaledBitmap(Util.removeFileFromPath(image.getImagePath()), 300, 150);


                                    String root = Environment.getExternalStorageDirectory().toString();
                                    File myDir = new File(root + "/req_images");
                                    myDir.mkdirs();

                                    Random generator = new Random();
                                    int n = 10000;
                                    n = generator.nextInt(n);
                                    String fname = "Image-" + n + ".jpg";
                                    File file = new File(myDir, fname);
                                    Log.i("serveyId", "" + file);
                                    if (file.exists())
                                        file.delete();
                                    try {
                                        FileOutputStream out = new FileOutputStream(file);
                                        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                        out.flush();
                                        out.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }*/
                        drawView.destroyDrawingCache();
                        setResult(RESULT_OK);
                        finish();
                   //     Util.scanMedia(ImageEditingActivity.this, absolutePath2);
                }
                });
                saveDialog.setNegativeButton(android.R.string.cancel, null);
                saveDialog.show();
            }
        });

    }

    public void showColourPallet() {
        colorPallete.startAnimation(animShow);
        colorPallete.setVisibility(View.VISIBLE);
        palleteIsVisible = true;
    }

    public void hideColourPallet() {
        colorPallete.startAnimation(animHide);
        palleteIsVisible = false;
    }

    public void togglePallet() {
        if (!palleteIsVisible) {
            showColourPallet();
        } else {
            hideColourPallet();
        }
    }

    /**
     * @param view Paint button as view
     */
    public void paintClicked(View view) {
//        drawView.setErase(false);
        if (view != currPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);

            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
        }
        hideColourPallet();
    }

    /**
     * Initializing animation
     */
    private void initAnimation() {
        animShow = AnimationUtils.loadAnimation(this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(this, R.anim.view_hide);
    }

}

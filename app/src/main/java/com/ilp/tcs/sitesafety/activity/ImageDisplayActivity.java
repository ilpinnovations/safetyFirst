package com.ilp.tcs.sitesafety.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.utils.Util;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Activity to display image
 */
public class ImageDisplayActivity extends AppCompatActivity {

    private static final String TAG = ImageDisplayActivity.class.getSimpleName();

    /**
     * Request code for image editing
     */
    private static final int REQUEST_EDIT = 12;

    /**
     * String for image path.
     */
    public static final String IMAGE_PATH = "imagePath";


    public static final String EXTRA_CAPTION = "caption";

    /**
     * String for setting delete status.
     */
    public static final String IS_DELETED = "isDeleted";
    /**
     * Imageview to display images.
     */
    private ImageView ivImageDisplay;
    /**
     * String for path of imagepath.
     */
    private EditText etCaption;

    private String path;

    private StringBuilder caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Image");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        path = getIntent().getStringExtra(IMAGE_PATH);

   //     Log.i("Image Display:",path);

        ivImageDisplay = (ImageView) findViewById(R.id.ivImageDisplay);
        etCaption = (EditText) findViewById(R.id.etCaption);
        String c = getIntent().getStringExtra(EXTRA_CAPTION);

    //    Log.v("aaaimagedisp77",c);
        if (!Util.isEmpty(c)) {
            caption = new StringBuilder(c);
            etCaption.setText(c);
        }

        etCaption.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!Util.isEmpty(editable)) {
                    caption = new StringBuilder();
                    caption.append(editable);
               //     Log.v("aaaaimgdis100caption", String.valueOf(caption));
                }
            }
        });

        Picasso.with(ImageDisplayActivity.this)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .centerInside()
                .fit()
                .into(ivImageDisplay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            final ArrayList<Boolean> deleteFromPhone = new ArrayList<>();
            deleteFromPhone.add(0, false);
            String[] array = new String[]{" Delete media from phone "};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete");
            builder.setMultiChoiceItems(array, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int indexSelected, boolean isChecked) {
                    deleteFromPhone.set(0, isChecked);
      //              Log.d(TAG, "checked :" + isChecked);
                }
            });
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (deleteFromPhone.get(0)) {
                        String newPath = Util.removeFileFromPath(path);
                        File file = new File(newPath);
                        boolean result = file.delete();
                        Util.scanMedia(ImageDisplayActivity.this, newPath);
                        if (!result)
                            Toast.makeText(ImageDisplayActivity.this, "Unable to delete image", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent();
                    intent.putExtra(IMAGE_PATH, path);
                    intent.putExtra(IS_DELETED, true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.show();
            return true;
        } else if (id == R.id.action_edit) {
            Intent intent = new Intent(this, ImageEditingActivity.class);
            intent.putExtra(ImageEditingActivity.IMAGE_PATH, Util.removeFileFromPath(path));
            startActivityForResult(intent, REQUEST_EDIT);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        if (path != null && caption != null)
            TblImages.updateCaption(path, caption.toString());
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra(IMAGE_PATH, path);
            intent.putExtra(IS_DELETED, false);
            intent.putExtra(EXTRA_CAPTION, caption != null ? caption.toString() : "");
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(IMAGE_PATH, path);
        intent.putExtra(IS_DELETED, false);
        intent.putExtra(EXTRA_CAPTION, caption != null ? caption.toString() : "");
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}

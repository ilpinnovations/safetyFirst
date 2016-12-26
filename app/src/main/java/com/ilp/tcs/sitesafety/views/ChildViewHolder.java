package com.ilp.tcs.sitesafety.views;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.modals.Image;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by 1241575 on 6/6/2016.
 * ChildViewHolder
 */
public class ChildViewHolder {

    public TextView tvChildTitle;
    public TextView tvElement;
    public RadioGroup rgResponse;
    public RadioButton rbYES;
    public RadioButton rbNA;
    public RadioButton rbNO;
    public ImageButton ibExpand;
    public ImageButton ibSelectImage;
    public EditText etDescription;
    public LinearLayout llImage;
    public LinearLayout llDescriptionView;
    private Context mContext;

    public ChildViewHolder(Context context) {
        this.mContext = context;
    }

    //==============================
    public void setChildTitle(String title) {
        tvChildTitle.setText(title);
    }

    public void setElement(String element) {
        tvElement.setText(element);
    }

    public void markResponse(int response) {
        if (response == 1) {
            tvChildTitle.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            setExpanded(false);
            rgResponse.check(rbYES.getId());
        } else if (response == -1) {
            tvChildTitle.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            setExpanded(true);
            rgResponse.check(rbNO.getId());
        } else if (response == 0) {
            tvChildTitle.setTextColor(ContextCompat.getColor(mContext, R.color.navi_blue));
            setExpanded(false);
            rgResponse.check(rbNA.getId());
        } else {
            rgResponse.check(-1);
            tvChildTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            setExpanded(false);
        }
    }

    public boolean isExpanded() {
        return llDescriptionView.getVisibility() != View.GONE;
    }

    public void setExpanded(boolean isExpanded) {
        if (isExpanded() == isExpanded)
            return;

        if (isExpanded) {
            ibExpand.setImageResource(R.drawable.ic_arrow_up);
            llDescriptionView.setVisibility(View.VISIBLE);
        } else {
            ibExpand.setImageResource(R.drawable.ic_arrow_down);
            llDescriptionView.setVisibility(View.GONE);
        }
    }

    public ImageButton getIbExpand() {
        return ibExpand;
    }

    public ImageButton getIbSelectImage() {
        return ibSelectImage;
    }

    public EditText getDescription() {
        return etDescription;
    }

    public void setDescription(String description) {
        etDescription.setText(description);
    }

    public void addImage(Image image) {
        ImageView iv = new ImageView(mContext);
        Resources resource = mContext.getResources();
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams((int) resource.getDimension(R.dimen.media_size), (int) resource.getDimension(R.dimen.media_size));
        linearParams.setMargins(3, 3, 3, 3);
        iv.setLayoutParams(linearParams);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setTag(R.id.ivImageDisplay, image.getImagePath());
        iv.setTag(R.id.etCaption, image.getCaption());
        Picasso.with(mContext)
                .load(image.getImagePath())
                .placeholder(R.drawable.placeholder)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .error(R.drawable.error_placeholder)
                .fit()
                .into(iv);
        llImage.addView(iv);
    }

    public LinearLayout getImageLayout() {
        return llImage;
    }

    public void removeAllImages() {
        llImage.removeAllViews();
    }

    public RadioGroup groupResponse() {
        return rgResponse;
    }
}

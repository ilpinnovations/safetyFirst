package com.ilp.tcs.sitesafety.views;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;

import java.util.Locale;

/**
 * Created by 1241575 on 6/6/2016.
 * ParentViewHolder
 */
public class ParentViewHolder {
    public TextView tvGroupTitle;
    public ImageView ivArrow;
    public TextView tvCounter;
    public TextView tvGroupHeader;
    public LinearLayout mLayout;

    public void setGroupTitle(String title) {
        tvGroupTitle.setText(title);
    }

    public void setExpanded(boolean isExpanded) {
        if (isExpanded) {
            ivArrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            ivArrow.setImageResource(R.drawable.ic_arrow_down);
        }
    }

    public void setCounter(int counter, int total) {
        tvCounter.setText(String.format(Locale.getDefault(), " (%2d/%2d)", counter, total));
    }
}

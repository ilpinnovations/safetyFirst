package com.ilp.tcs.sitesafety.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.views.ViewHolder;

/**
 * Created by Fedric Antony on 03-07-2016.
 */
public class SettingsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ViewHolder mHolder;

    public SettingsAdapter() {
        mInflater = (LayoutInflater) SiteSafetyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Constants.sList.length;
    }

    @Override
    public Object getItem(int position) {
        return Constants.sList[position];
    }

    @Override
    public long getItemId(int position) {
        return Constants.sList[position].hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_single, parent, false);
            mHolder = new ViewHolder();
            mHolder.mTextViewOne = (TextView) convertView.findViewById(R.id.first_row);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mTextViewOne.setText(Constants.sList[position]);
        return convertView;
    }
}

package com.ilp.tcs.sitesafety.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;
import com.ilp.tcs.sitesafety.modals.Datas;
import com.ilp.tcs.sitesafety.views.ViewHolder;

/**
 * Created by Fedric Antony on 06-07-2016.
 */
public class FilteredLocationAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ViewHolder mHolder;

    public FilteredLocationAdapter() {
        mInflater = (LayoutInflater) SiteSafetyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Datas.listFilteredLocations.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_location, null, false);
            mHolder = new ViewHolder();
            mHolder.mTextViewOne = (TextView) convertView.findViewById(R.id.txt_view_location);
            mHolder.mTextViewTwo = (TextView) convertView.findViewById(R.id.txt_view_branch);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mTextViewOne.setText(Datas.listFilteredLocations.get(position).getName());
        mHolder.mTextViewTwo.setText(Datas.listFilteredLocations.get(position).getBranchName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_location, null, false);
            mHolder = new ViewHolder();
            mHolder.mTextViewOne = (TextView) convertView.findViewById(R.id.txt_view_location);
            mHolder.mTextViewTwo = (TextView) convertView.findViewById(R.id.txt_view_branch);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mTextViewOne.setText(Datas.listFilteredLocations.get(position).getName());
        mHolder.mTextViewTwo.setText("Branch: " + Datas.listFilteredLocations.get(position).getBranchName());
        return convertView;
    }
}

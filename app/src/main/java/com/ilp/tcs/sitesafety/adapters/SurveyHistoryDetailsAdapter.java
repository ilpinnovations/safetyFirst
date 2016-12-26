package com.ilp.tcs.sitesafety.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 1244696 on 6/8/2016.
 * This class will display survey History Details
 */
/**
 *  Modified by Abhishek Gupta on 17/8/2016.
 */
public class SurveyHistoryDetailsAdapter extends BaseAdapter {
    /**
     * This List variable holds the list of survey details
     */
    private ArrayList surveyDetails;

    /**
     * This is the Adapter for Survey History Details list
     *
     * @param surveyDetails
     */
    public SurveyHistoryDetailsAdapter(Map<String, String> surveyDetails) {
        this.surveyDetails = new ArrayList();
        this.surveyDetails.addAll(surveyDetails.entrySet());
    }

    @Override
    public int getCount() {
        return surveyDetails.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) surveyDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder myholder;
        if (convertView == null) {
            myholder = new viewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_survey_details, parent, false);
            myholder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            myholder.tvValue = (TextView) convertView.findViewById(R.id.tvValue);
            convertView.setTag(myholder);
        } else
            myholder = (viewHolder) convertView.getTag();
        Map.Entry<String, String> item = getItem(position);
        myholder.tvContent.setText(item.getKey());
        myholder.tvValue.setText(item.getValue());
        return convertView;
    }

    /**
     * This class holds the View Holder of Survey History Details List
     */
    private class viewHolder {
        public TextView tvContent;
        public TextView tvValue;
    }

}

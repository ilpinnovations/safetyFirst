package com.ilp.tcs.sitesafety.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.ParcelableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.activity.CompletedSurveyActivity;
import com.ilp.tcs.sitesafety.activity.SurveyDetailsActivity;
import com.ilp.tcs.sitesafety.database.DbHelper;
import com.ilp.tcs.sitesafety.database.TblSurvey;
import com.ilp.tcs.sitesafety.modals.Survey;
import com.ilp.tcs.sitesafety.preference.SitePreference;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.utils.SimpleSpanBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by 1239940 on 6/7/2016.
 * SurveyListAdapter
 */
public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.ViewHolder> {

    /**
     * This variable holds the instance of date
     */
    SimpleDateFormat simpleDateFormat;
    /**
     * This list variable holds the List of Surveys
     */
    private ArrayList<Survey> surveyHistoryList;
    /**
     * This variable holds the context of Survey list
     */
    private Context mContext;

    /**
     * This is the constructor of the Survey history table
     *
     * @param surveyHistoryList all survey list
     * @param context           given context
     */
    public SurveyListAdapter(ArrayList<Survey> surveyHistoryList, Context context) {
        this.surveyHistoryList = surveyHistoryList;
        this.mContext = context;
        this.simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_survey, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Survey survey = surveyHistoryList.get(position);

        SimpleSpanBuilder spanBuilder = new SimpleSpanBuilder();
        holder.surveyTitle.setText(survey.getSurveyTitle());

        spanBuilder.append("Created On: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(simpleDateFormat.format(survey.getCreatedDateTime()), new StyleSpan(Typeface.BOLD));
        holder.surveyCreatedTime.setText(spanBuilder.build());

        spanBuilder.clear();
        spanBuilder.append("Updated On: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(simpleDateFormat.format(survey.getUpdatedDateTime()), new StyleSpan(Typeface.BOLD));
        holder.surveyUpdatedTime.setText(spanBuilder.build());

        spanBuilder.clear();
        spanBuilder.append("Created By: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(survey.getUserName(), new StyleSpan(Typeface.BOLD));
        holder.surveyCreatedBy.setText(spanBuilder.build());

        spanBuilder.clear();
        spanBuilder.append("Branch: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(survey.getSurveyBranch(), new StyleSpan(Typeface.BOLD))
                .append(" - Location: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(survey.getSurveyLocation(), new StyleSpan(Typeface.BOLD));
        holder.surveyBranchLoc.setText(spanBuilder.build());

        spanBuilder.clear();
        spanBuilder.append("Status: ", new ForegroundColorSpan(Color.LTGRAY))
                .append(getCompleteStatus(survey.getSurveyCompleteStatus()), getStatusColor(survey.getSurveyCompleteStatus()), new StyleSpan(Typeface.BOLD));
        holder.surveyStatus.setText(spanBuilder.build());
    }

    private ParcelableSpan getStatusColor(String s) {
        if (s == null) {
            return new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.red));
        } else if (s.contentEquals("0")) {
            return new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.red));
        } else {
            return new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.green));
        }
    }

    private String getCompleteStatus(String s) {

        if (s == null) {
            return "Pending";
        } else if (s.contentEquals("0")) {
            return "Pending";
        } else {
            return "Completed";
        }
    }

    @Override
    public long getItemId(int position) {
        return surveyHistoryList.get(position).getSurveyId();
    }

    @Override
    public int getItemCount() {
        return surveyHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView surveyTitle;
        private TextView surveyCreatedTime;
        private TextView surveyUpdatedTime;
        private TextView surveyStatus;
        private TextView surveyCreatedBy;
        private TextView surveyBranchLoc;
        private ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            surveyTitle = (TextView) itemView.findViewById(R.id.tvSurveyTitle);
            surveyCreatedTime = (TextView) itemView.findViewById(R.id.tvCreatedTime);
            surveyUpdatedTime = (TextView) itemView.findViewById(R.id.tvUpdatedTime);
            surveyCreatedBy = (TextView) itemView.findViewById(R.id.tvCreatedBy);
            surveyBranchLoc = (TextView) itemView.findViewById(R.id.txt_view_branch_loc);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);

            surveyStatus = (TextView) itemView.findViewById(R.id.txt_view_status);
            itemView.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ivDelete) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete the Audit?");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long id = surveyHistoryList.get(getAdapterPosition()).getSurveyId();
                        DbHelper.delete(DbHelper.getInstance().getWritableDatabase(), TblSurvey.TABLE_NAME, id);
                        surveyHistoryList.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
            } else {
                Intent intent = new Intent(mContext, SurveyDetailsActivity.class);
                SitePreference.getInstance().setSurveyId(surveyHistoryList.get(getAdapterPosition()).getSurveyId());
                intent.putExtra(Constants.SURVEY_ID, surveyHistoryList.get(getAdapterPosition()).getSurveyId());
                ((CompletedSurveyActivity) mContext).onBackPressed();
                mContext.startActivity(intent);
            }
        }
    }
}

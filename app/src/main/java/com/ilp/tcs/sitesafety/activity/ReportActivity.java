package com.ilp.tcs.sitesafety.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.database.DbHelper;
import com.ilp.tcs.sitesafety.database.TblSurvey;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = ReportActivity.class.getSimpleName();
    private TableLayout tableLayout;
    private long surveyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        surveyId = getIntent().getLongExtra(TblSurvey._ID, -1);
        String title = getIntent().getStringExtra(TblSurvey.COLUMN_TITLE);

        setTitle(title);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<GroupItem> survey = DbHelper.getSurveyDetails(surveyId);

        if (survey != null) {
            addHeaders();
            generateScorecard(survey);
        }
    }

    private void generateScorecard(ArrayList<GroupItem> survey) {
        int totalYes = 0, totalNo = 0, totalNa = 0, totalPoints = 0;

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

        for (String element : elements) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            int no = 0, na = 0, yes = 0, tp = 0, score;
            for (GroupItem groupItem : survey) {
                ArrayList<ChildItem> childItems = groupItem.getChildItems();
                for (ChildItem childItem : childItems) {
                    if (childItem.getElement().equals(element)) {
                        switch (childItem.getResponse()) {
                            case -1:
                                no += childItem.getWeight();
                                break;
                            case 0:
                                na += childItem.getWeight();
                                break;
                            case 1:
                                yes += childItem.getWeight();
                                break;
                        }
                    }
                }
            }

            totalYes += yes;
            totalNo += no;
            totalNa += na;
            tp += (yes + no);
            score = (int) (((float) yes / (yes + no)) * 100);
            totalPoints += tp;

//            Log.e(TAG, "NA :" + na + " |Yes :" + yes + " |No :" + no + " |TP :" + (yes + no) + " |Score : " + ((float) yes / (yes + no)) * 100);

            row.addView(getTextView(element));
            row.addView(getTextView(na));
            row.addView(getTextView(yes));
            row.addView(getTextView(no));
            row.addView(getTextView(yes + no));
            row.addView(getTextView(score + "%"));
            tableLayout.addView(row);
        }

        int totalScore = (int) ((float) totalYes / totalPoints * 100);
        addFooter(totalNa, totalYes, totalNo, totalPoints, totalScore);
//        Log.e(TAG, "Total Score :" + );
    }

    private TextView getTextView(int value) {
        return getTextView(String.valueOf(value));
    }

    private TextView getTextView(String text) {
        TextView tv = new TextView(this);
        int padding = getResources().getDimensionPixelOffset(R.dimen.padding_large);
        int margin = getResources().getDimensionPixelOffset(R.dimen.border);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, margin, margin, margin);
        tv.setLayoutParams(params);
        tv.setPadding(padding, padding, padding, padding);
        tv.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        tv.setTextColor(ContextCompat.getColor(this, R.color.white));
        tv.setText(text);
        return tv;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void addHeaders() {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row.addView(getHeaderTitle("Safety elements"));
        row.addView(getHeaderTitle(getString(R.string.na)));
        row.addView(getHeaderTitle(getString(R.string.yes)));
        row.addView(getHeaderTitle(getString(R.string.no)));
        row.addView(getHeaderTitle("Total points"));
        row.addView(getHeaderTitle("Score"));

        tableLayout.addView(row);
    }

    private void addFooter(int totalNa, int totalYes, int totalNo, int totalPoints, int totalScore) {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row.addView(getHeaderTitle(""));
        row.addView(getHeaderTitle(totalNa));
        row.addView(getHeaderTitle(totalYes));
        row.addView(getHeaderTitle(totalNo));
        row.addView(getHeaderTitle(totalPoints));
        row.addView(getHeaderTitle(totalScore + "%"));

        tableLayout.addView(row);
    }

    private TextView getHeaderTitle(int value) {
        return getHeaderTitle(String.valueOf(value));
    }

    private TextView getHeaderTitle(String title) {
        TextView elementTitle = getTextView(title);
        elementTitle.setBackgroundColor(ContextCompat.getColor(this, R.color.navi_blue));
        elementTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
        return elementTitle;
    }
}

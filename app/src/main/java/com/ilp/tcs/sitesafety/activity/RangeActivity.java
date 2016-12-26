package com.ilp.tcs.sitesafety.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ilp.tcs.sitesafety.R;

public class RangeActivity extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        addHeaders();

        TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row1.addView(getTextView("<50%"));
        row1.addView(getTextView("Close down the facility"));
        tableLayout.addView(row1);

        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row2.addView(getTextView("50-70%"));
        row2.addView(getTextView("Show some improvements rightaway"));
        tableLayout.addView(row2);

        TableRow row3 = new TableRow(this);
        row3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row3.addView(getTextView("70-80%"));
        row3.addView(getTextView("Needs improvements"));
        tableLayout.addView(row3);

        TableRow row4 = new TableRow(this);
        row4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row4.addView(getTextView("80-90%"));
        row4.addView(getTextView(""));
        tableLayout.addView(row4);

        TableRow row5 = new TableRow(this);
        row5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row5.addView(getTextView("90-94%"));
        row5.addView(getTextView(""));
        tableLayout.addView(row5);

        TableRow row6 = new TableRow(this);
        row6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row6.addView(getTextView("94-97%"));
        row6.addView(getTextView(""));
        tableLayout.addView(row6);

        TableRow row7 = new TableRow(this);
        row7.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row7.addView(getTextView("97-100%"));
        row7.addView(getTextView(""));
        tableLayout.addView(row7);

        TableRow row8 = new TableRow(this);
        row8.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row8.addView(getTextView("100%"));
        row8.addView(getTextView("Safest facility"));
        tableLayout.addView(row8);
    }

    private void addHeaders() {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row.addView(getHeaderTitle("Range"));
        row.addView(getHeaderTitle("Interpretation"));

        tableLayout.addView(row);
    }

    private TextView getHeaderTitle(String title) {
        TextView elementTitle = getTextView(title);
        elementTitle.setBackgroundColor(ContextCompat.getColor(this, R.color.navi_blue));
        elementTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
        return elementTitle;
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

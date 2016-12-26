package com.ilp.tcs.sitesafety.activity;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.ilp.tcs.sitesafety.R;

import java.util.ArrayList;

public class BarGraphActivity extends AppCompatActivity {

    private BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        barChart = (BarChart)findViewById(R.id.bg_calculation);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        String[] score = (String[]) getIntent().getSerializableExtra("scoreArray");


        barEntries.add(new BarEntry(Float.parseFloat(score[0]),0));
        barEntries.add(new BarEntry(Float.parseFloat(score[1]),1));
        barEntries.add(new BarEntry(Float.parseFloat(score[2]),2));
        barEntries.add(new BarEntry(Float.parseFloat(score[3]),3));
        barEntries.add(new BarEntry(Float.parseFloat(score[4]),4));
        barEntries.add(new BarEntry(Float.parseFloat(score[5]),5));
        barEntries.add(new BarEntry(Float.parseFloat(score[6]),6));
        barEntries.add(new BarEntry(Float.parseFloat(score[7]),7));
        barEntries.add(new BarEntry(Float.parseFloat(score[8]),8));
        barEntries.add(new BarEntry(Float.parseFloat(score[9]),9));

        BarDataSet barDataSet = new BarDataSet(barEntries,"");
        barDataSet.setBarSpacePercent(20);

        int[] color = {ResourcesCompat.getColor(getResources(),R.color.redbar,null),
                         ResourcesCompat.getColor(getResources(),R.color.orangebar,null),
                         ResourcesCompat.getColor(getResources(),R.color.greenbar,null),
                         ResourcesCompat.getColor(getResources(),R.color.yellowbar,null),
                         ResourcesCompat.getColor(getResources(),R.color.bg_screen5,null),
                         ResourcesCompat.getColor(getResources(),R.color.bg_screen7,null),
                         ResourcesCompat.getColor(getResources(),R.color.bg_screen1,null),
                         ResourcesCompat.getColor(getResources(),R.color.bg_screen2,null),
                         ResourcesCompat.getColor(getResources(),R.color.bg_screen3,null),
                         ResourcesCompat.getColor(getResources(),R.color.bg_screen4,null),
                       };
        ColorTemplate.createColors(color);
        //ColorTemplate.createColors(R.color.app_color, color[0]);
        barDataSet.setColors(ColorTemplate.createColors(color));
       // barDataSet.setBarShadowColor(R.color.app_color);

     /*   ArrayList<String> category = new ArrayList<String>();
        category.add("GS");
        category.add("Fs");
        category.add("ES");
        category.add("Emer");
        category.add("WS");
        category.add("SW");
        category.add("VS");
        category.add("CS");
        category.add("HH");
        category.add("SS");*/

        ArrayList<String> category = new ArrayList<String>();
        category.add("");
        category.add("");
        category.add("");
        category.add("");
        category.add("");
        category.add("");
        category.add("");
        category.add("");
        category.add("");
        category.add("");

        BarData barData = new BarData(category,barDataSet);



//        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//
//
//                //        category.add("General safety");
////        category.add("Fire Safety");
//                //        category.add("Electical Safety");
////        category.add("Emergency");
////        category.add("Workplace Safety");
////        category.add("Safe Work");
////        category.add("Vehicular Safety");
////        category.add("Contractor Safety");
////        category.add("Health & Hygiene");
////        category.add("Safety Signage");
//
//                if(e.getXIndex()==0)
//                    show("General safety"," ");
//                else if(e.getXIndex()==1)
//                    show("Fire Safety"," ");
//                else if(e.getXIndex()==2)
//                    show("Electical Safety"," ");
//                else if(e.getXIndex()==3)
//                    show("Emergency"," ");
//                else if(e.getXIndex()==4)
//                    show("Workplace Safety"," ");
//                else if(e.getXIndex()==5)
//                    show("Safe Work"," ");
//                else if(e.getXIndex()==6)
//                    show("Vehicular Safety"," ");
//                else if(e.getXIndex()==7)
//                    show("Contractor Safety"," ");
//                else if(e.getXIndex()==8)
//                    show("Health & Hygiene"," ");
//                else if(e.getXIndex()==9)
//                    show("Safety Signage"," ");
//
//
//
//
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

        barChart.setDescription("");
        barChart.setDrawBarShadow(false);
        barChart.setBottom(30);
        barChart.setData(barData);
        //============================================
        barChart.setDrawHighlightArrow(true);
       // barChart.zoom(2,4,10,10);
        //barChart.zoomIn();
      //  barChart.setContentDescription("kjhaf");
       // barChart.setBottom(20);
        //=============================================
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.animateY(3000);


        Log.i("BarGraph","Done");



    }


    public void show(String tilte, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(tilte);
        builder.setMessage(message);
        builder.show();
    }
}


//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.github.mikephil.charting.utils.Highlight;
//import com.github.mikephil.charting.utils.PercentFormatter;
//import com.ilp.tcs.sitesafety.R;
//
//import java.util.ArrayList;
//
//
//public class BarGraphActivity extends AppCompatActivity {
//
//    private RelativeLayout relativeLayout;
//    private PieChart pieChart;
//
//    private float[] yData = new float[10];
//    private String[] xData = {"General safety","Fire Safety","Electical Safety",
//            "Emergency","Workplace Safety","Safe Work","Vehicular Safety","Contractor Safety","Health & Hygiene","Safety Signage"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        pieChart = new PieChart(this);
//
//        setContentView(pieChart);
//
//        relativeLayout = (RelativeLayout)findViewById(R.id.activity_bar_graph);
//
//
////        relativeLayout.addView(pieChart);
////        relativeLayout.setBackgroundColor(Color.LTGRAY);
//
//        pieChart.setUsePercentValues(true);
//        pieChart.setDescription("S-Smart App");
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleColorTransparent(true);
//        pieChart.setHoleRadius(7);
//        pieChart.setTransparentCircleRadius(10);
//        pieChart.setRotationAngle(0);
//        pieChart.setRotationEnabled(true);
//
//        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//
//                if(e==null)
//                    return;
//
//                Toast.makeText(BarGraphActivity.this,xData[e.getXIndex()]+"="+e.getVal()+"%",
//                        Toast.LENGTH_SHORT).show();
//
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//
//        addData();
//
//        Legend i = pieChart.getLegend();
//        i.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        i.setXEntrySpace(7);
//        i.setYEntrySpace(5);
//
//
//
//    }
//
//    public void addData()
//    {
//
//        String[] score = (String[]) getIntent().getSerializableExtra("scoreArray");
//        ArrayList<Entry> yval = new ArrayList<>();
//
//        for(int i=0; i<yData.length; i++) {
//            yData[i] = Float.parseFloat(score[i]);
//            yval.add(new Entry(yData[i], i));
//            Log.i("PieDataSet",": "+yData[i]);
//
//        }
//
//        ArrayList<String> xval = new ArrayList<>();
//
//        for(int i=0; i<xData.length; i++)
//            xval.add(xData[i]);
//
//        PieDataSet dataSet = new PieDataSet(yval,"Marker Share");
//        dataSet.setSliceSpace(3);
//        dataSet.setSelectionShift(5);
//
//        ArrayList<Integer> colors = new ArrayList<>();
//
//        for(int c: ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for(int c: ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for(int c: ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for(int c: ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for(int c: ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//        dataSet.setColors(colors);
//
//        PieData data = new PieData(xval,dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.GRAY);
//
//        pieChart.setData(data);
//
//        pieChart.highlightValues(null);
//
//        pieChart.invalidate();
//
//
//
//    }
//}



package com.ilp.tcs.sitesafety.modals;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;
import com.ilp.tcs.sitesafety.preference.SitePreference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by 1294414 on 12/19/2016.
 */

public class GenerateOFIExecel extends AsyncTask<Void, Void, Void> {
    /**
     * ArrayList containg all data required for PDF generation.
     */

    private ArrayList<OFIBeans> ofiBeansArrayList = new ArrayList<>();

    private ArrayList<GroupItem> groupItemsArrayList;

    private File mFile;

    private Survey mSurvey;

    private String date;

    /**
     * @param groupItemsArrayList ArrayList containing all data required for excel generation.
     */
    public GenerateOFIExecel(Survey survey, ArrayList<GroupItem> groupItemsArrayList, File file) {
        this.groupItemsArrayList = groupItemsArrayList;
        this.mFile = file;
        mSurvey = survey;
    }


    /**
     * @throws IOException    throwing Input output exception.
     * @throws WriteException throwing write exception when file cannot be written into document.
     */
    public void generateExcel() throws IOException, WriteException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;

        if(mFile.exists()){
            //Log.v("AAAAfilexists",""+mFile.getAbsolutePath());
            // mFile.delete();
            if(mFile.exists()){
                //          Log.v("AAAAAfileExist","asfadf");
            }
        }

        workbook = Workbook.createWorkbook(mFile, wbSettings);
        //Excel sheet name. 0 represents first sheet
    //    WritableSheet sheet = workbook.createSheet("Checklist", 1);

              //*       Heading Formatter      *//*
       /* WritableFont font_top = new WritableFont(WritableFont.TIMES, 20,WritableFont.BOLD);
        font_top.setColour(Colour.BLACK);//font color
*/
        /*
        WritableCellFormat cellFormat_Top = new WritableCellFormat(font_top);
        cellFormat_Top.setBackground(Colour.AQUA);//background color
        cellFormat_Top.setAlignment(Alignment.CENTRE);
        cellFormat_Top.setVerticalAlignment(VerticalAlignment.CENTRE);

        int col_0 = 0;
        int width_0 = 2;
        sheet.setColumnView(col_0,width_0);

        //    int row = 3;
        //     int heightInPoints = 32*20;
        //    sheet.setRowView(row, heightInPoints);
        sheet.addCell(new Label(1,0,"HSE Checklist - Safety",cellFormat_Top));
        sheet.mergeCells(1,0,4,0);*/

        Calendar calendar = Calendar.getInstance();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        date = sdf.format(calendar.getTime());

        WritableFont font_detail = new WritableFont(WritableFont.TIMES,15,WritableFont.BOLD);
        font_detail.setColour(Colour.BLACK);//font color

        WritableCellFormat cellFormat_delails = new WritableCellFormat(font_detail);
        cellFormat_delails.setAlignment(Alignment.GENERAL);

      /*  sheet.addCell(new Label(1,1,"  Done By : "+mSurvey.getUserName(),cellFormat_delails));
        sheet.addCell(new Label(2,1,"Date  : "+ sdf.format(mSurvey.getCreatedDateTime()),cellFormat_delails));
        sheet.addCell(new Label(1,2,"  Branch : "+ SitePreference.getInstance().getBranchName(),cellFormat_delails));
        sheet.addCell(new Label(2,2,"Updated on: " + sdf.format(mSurvey.getUpdatedDateTime()),cellFormat_delails));
        sheet.addCell(new Label(1,3,"  Location: " + SitePreference.getInstance().getLocationName(),cellFormat_delails));*/

        /*WritableCellFormat cellFormat_1_1 = new WritableCellFormat(font_top);
        cellFormat_1_1.setBackground(Colour.GREEN);//background color
        cellFormat_1_1.setAlignment(Alignment.GENERAL);*/

        /*sheet.addCell(new Label(1, 4, "I.   Building - External",cellFormat_1_1));
        sheet.mergeCells(1,4,4,4);*/

/*
        WritableCellFormat cellFormat_subhead = new WritableCellFormat();
        cellFormat_subhead.setBackground(Colour.YELLOW);//background color
        cellFormat_subhead.setAlignment(Alignment.LEFT);
*/

      /*  sheet.getSettings().setDefaultColumnWidth(30);
        sheet.getSettings().setDefaultRowHeight(28 * 20);

        sheet.addCell(new Label(2, 5, "Element",cellFormat_subhead));
        sheet.addCell(new Label(3, 5, "Complaint",cellFormat_subhead));
        sheet.addCell(new Label(4, 5, "Remarks",cellFormat_subhead));
*/
   /*     WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
        cellFont.setColour(Colour.BLACK);//font color

        WritableFont font = new WritableFont(WritableFont.TIMES, 12);
        font.setColour(Colour.BLACK);//font color

        WritableFont font_genr = new WritableFont(WritableFont.TIMES, 12,WritableFont.BOLD);
        font.setColour(Colour.BLACK);//font color*/


      /*  WritableCellFormat cellFormat_grey = new WritableCellFormat(font);
        cellFormat_grey.setBackground(Colour.GREY_40_PERCENT);//background color
        cellFormat_grey.setBorder(Border.ALL, BorderLineStyle.THIN);
        //    cellFormat_grey.setBorder(Border.NONE, BorderLineStyle.NONE);
        cellFormat_grey.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_yellow = new WritableCellFormat(font);
        cellFormat_yellow.setBackground(Colour.YELLOW);//background color
        cellFormat_yellow.setBorder(Border.ALL, BorderLineStyle.THIN);
        //   cellFormat_yellow.setBorder(Border.NONE, BorderLineStyle.NONE);
        cellFormat_yellow.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_yellow_gen = new WritableCellFormat(font_genr);
        cellFormat_yellow_gen.setBackground(Colour.YELLOW);//background color
        cellFormat_yellow_gen.setBorder(Border.ALL, BorderLineStyle.THIN);
        //   cellFormat_yellow_gen.setBorder(Border.NONE, BorderLineStyle.NONE);
        cellFormat_yellow_gen.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_Red = new WritableCellFormat(cellFont);
        cellFormat_Red.setBackground(Colour.RED);//background color
        cellFormat_Red.setBorder(Border.ALL, BorderLineStyle.THIN);
        //   cellFormat_Red.setBorder(Border.NONE, BorderLineStyle.NONE);
        cellFormat_Red.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_green = new WritableCellFormat(cellFont);
        cellFormat_green.setBackground(Colour.BRIGHT_GREEN);//background color
        cellFormat_green.setBorder(Border.ALL, BorderLineStyle.THIN);
        //  cellFormat_green.setBorder(Border.NONE, BorderLineStyle.NONE);
        cellFormat_green.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_blue = new WritableCellFormat(cellFont);
        cellFormat_blue.setBackground(Colour.BLUE);//background color
        cellFormat_blue.setBorder(Border.ALL, BorderLineStyle.THIN);
        //  cellFormat_blue.setBorder(Border.NONE, BorderLineStyle.NONE);
        cellFormat_blue.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_grey_wrap = new WritableCellFormat(font);
        cellFormat_grey_wrap.setBackground(Colour.GREY_40_PERCENT);//background color
        cellFormat_grey_wrap.setBorder(Border.ALL, BorderLineStyle.THIN);
        //    cellFormat_grey_wrap.setBorder(Border.NONE, BorderLineStyle.NONE);
        //    cellFormat_grey_wrap.setWrap(true);
        cellFormat_grey_wrap.setAlignment(Alignment.LEFT);
        cellFormat_grey_wrap.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_Red_wrap = new WritableCellFormat(cellFont);
        cellFormat_Red_wrap.setBackground(Colour.RED);//background color
        cellFormat_Red_wrap.setBorder(Border.ALL, BorderLineStyle.THIN);
        //    cellFormat_Red_wrap.setBorder(Border.NONE, BorderLineStyle.NONE);
        //    cellFormat_Red_wrap.setWrap(true);
        cellFormat_Red_wrap.setAlignment(Alignment.LEFT);
        cellFormat_Red_wrap.setVerticalAlignment(VerticalAlignment.CENTRE);

        WritableCellFormat cellFormat_green_wrap = new WritableCellFormat(cellFont);
        cellFormat_green_wrap.setBackground(Colour.BRIGHT_GREEN);//background color
        cellFormat_green_wrap.setBorder(Border.ALL, BorderLineStyle.THIN);
        //     cellFormat_green_wrap.setBorder(Border.NONE, BorderLineStyle.NONE);
        //   cellFormat_green_wrap.setWrap(true);
        cellFormat_green_wrap.setAlignment(Alignment.LEFT);
        cellFormat_green_wrap.setVerticalAlignment(VerticalAlignment.CENTRE);*/

        String check="";
     //   int k = 5;
        for (int i = 0; i < groupItemsArrayList.size(); i++) {
            GroupItem groupItem = groupItemsArrayList.get(i);
            int sLength0 = groupItem.getTitle().length();
          /*  if(k!=5)
             //   sheet.mergeCells(1, k, 4, k);//merging cells colum1-4 and row k-k
            check = groupItem.getTitle();
            check = check.substring(0,2);*/
            //   Log.v("AAAAASTRING",check);
          /*  if(check.equals("10")){
              *//*  sheet.addCell(new Label(1, k, "II.   Building - Internal",cellFormat_1_1));
                sheet.mergeCells(1,k,4,k);*//*
                k++;
            }*/
           /* else if(check.equals("21")){
               *//* sheet.addCell(new Label(1, k, "III.   Processes & Practices",cellFormat_1_1));
                sheet.mergeCells(1,k,4,k);*//*
                k++;
            }*/
          //  sheet.addCell(new Label(1, k, groupItem.getTitle(), cellFormat_yellow_gen));
          /*  if(k!=5)
               // sheet.mergeCells(1,k,4,k);
            k++;*/
            ArrayList<ChildItem> childItems = groupItem.getChildItems();

            /*for (int j = 0; j < childItems.size(); j++, k++) {
                ChildItem childItem = childItems.get(j);


                int sLength = childItem.getTitle().length();
                sheet.setColumnView(0, sLength * 256);
                sheet.addCell(new Label(0, k, childItem.getTitle()));

                if (childItem.getResponse() == 1) {

                    sheet.addCell(new Label(1, k, "YES", cellFormat_green));
                } else if (childItem.getResponse() == -1) {

                    sheet.addCell(new Label(1, k, "NO", cellFormat_Red));
                } else {
                    sheet.addCell(new Label(1, k, "NA", cellFormat_blue));
                }
                //int sLength2=childItem.getDescription().length();
                sheet.setColumnView(0, 150);
                sheet.addCell(new Label(2, k, childItem.getDescription()));


                sheet.setColumnView(0, 30);
                sheet.addCell(new Label(3, k, "Image" + (i + 1)));

            }*/

          /*  WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setAlignment(Alignment.LEFT);
            cellFormat.setWrap(true);*/


            for (int j = 0; j < childItems.size(); j++) {
                ChildItem childItem = childItems.get(j);


          /*      int sLength = childItem.getTitle().length();
                //     sheet.setColumnView(0, sLength * 256);

                int col0 = 0;
                int widthInChars0 = 2;
                sheet.setColumnView(col0, widthInChars0);

                int col = 1;
                int widthInChars = 116;
                sheet.setColumnView(col, widthInChars);
                sheet.addCell(new Label(1, k, childItem.getTitle()));
                //    sheet.setColumnView(1,256);

                sheet.addCell(new Label(2, k,"   "+childItem.getElement()));*/

                if (childItem.getResponse() == 1) {
               /*     sheet.addCell(new Label(3, k, "YES", cellFormat_green));
                    WritableCell c1 = sheet.getWritableCell(1,k);
                    WritableCell c2 = sheet.getWritableCell(2,k);
                    c1.setCellFormat(cellFormat_green_wrap);
                    c2.setCellFormat(cellFormat_green);
                    sheet.setColumnView(0, 150);
                    sheet.addCell(new Label(4, k,childItem.getDescription(),cellFormat_green_wrap));*/

                } else if (childItem.getResponse() == -1) {
                 /*   sheet.addCell(new Label(3, k, "NO", cellFormat_Red));
                    WritableCell c1 = sheet.getWritableCell(1,k);
                    WritableCell c2 = sheet.getWritableCell(2,k);
                    c1.setCellFormat(cellFormat_Red_wrap);
                    c2.setCellFormat(cellFormat_Red);
                    sheet.setColumnView(0, 150);
                    sheet.addCell(new Label(4, k,childItem.getDescription(),cellFormat_Red_wrap));
*/


                 //   ArrayList<ChildItem> childItems = groupItem.getChildItems();

                    OFIBeans ofiBeans = new OFIBeans();
                    ofiBeans.setDetails(childItem.getTitle());
                    ofiBeans.setCategory(childItem.getElement());
                  //===========================================================
                    ofiBeans.setRemarks(childItem.getDescription());
                   //==========================================================

                    String ref_no = childItem.getTitle().substring(0,childItem.getTitle().indexOf("."));

                    switch (ref_no)
                    {
                        case "1":
                            ofiBeans.setRef_no("1.1-1.17");
                            break;
                        case "2":
                            ofiBeans.setRef_no("2.1-2.13");
                            break;
                        case "3":
                            ofiBeans.setRef_no("3.1-1.12");
                            break;
                        case "4":
                            ofiBeans.setRef_no("4.1-4.19");
                            break;
                        case "5":
                            ofiBeans.setRef_no("5.1-5.38");
                            break;
                        case "6":
                            ofiBeans.setRef_no("6.1-6.26");
                            break;
                        case "7":
                            ofiBeans.setRef_no("7.1-1.43");
                            break;
                        case "8":
                            ofiBeans.setRef_no("8.1-8.9");
                            break;
                        case "9":
                            ofiBeans.setRef_no("9.1-1.13");
                            break;
                        case "10":
                            ofiBeans.setRef_no("10.1-10.24");
                            break;
                        case "11":
                            ofiBeans.setRef_no("11.1-11.4");
                            break;
                        case "12":
                            ofiBeans.setRef_no("12.1-12.8");
                            break;
                        case "13":
                            ofiBeans.setRef_no("13.1-13.12");
                            break;
                        case "14":
                            ofiBeans.setRef_no("14.1-14.5");
                            break;
                        case "15":
                            ofiBeans.setRef_no("15.1-15.32");
                            break;
                        case "16":
                            ofiBeans.setRef_no("16.1-16.11");
                            break;
                        case "17":
                            ofiBeans.setRef_no("17.1-17.100");
                            break;
                        case "18":
                            ofiBeans.setRef_no("18.1-18.8");
                            break;
                        case "19":
                            ofiBeans.setRef_no("19.1-19.6");
                            break;
                        case "20":
                            ofiBeans.setRef_no("20.1-20.33");
                            break;
                        case "21":
                            ofiBeans.setRef_no("21.1-21.12");
                            break;
                        case "22":
                            ofiBeans.setRef_no("22.1-22.9");
                            break;
                        case "23":
                            ofiBeans.setRef_no("23.1-23.6");
                            break;
                        case "24":
                            ofiBeans.setRef_no("24.1-24.5");
                            break;
                        case "25":
                            ofiBeans.setRef_no("25.1-25.7");
                            break;
                        case "26":
                            ofiBeans.setRef_no("26.1-26.12");
                            break;
                        default:
                            ofiBeans.setRef_no("0");
                            break;


                    }



                    //             String ref_no = childItem.getTitle().substring(0,1);

                    ofiBeansArrayList.add(ofiBeans);

                }
                //int sLength2=childItem.getDescription().length();


            //    sheet.setColumnView(0, 30);
//                sheet.addCell(new Label(4, k, "Image" + (i + 1)));

            }

            //k++;
        }

      //  writeCalculations(workbook);
        writeOFI(workbook,ofiBeansArrayList);
        workbook.write();
        workbook.close();
    }

    private void writeCalculations(WritableWorkbook workbook) throws WriteException {

        WritableSheet sheet = workbook.createSheet("Calculations", 2);

        WritableFont font_gen = new WritableFont(WritableFont.TIMES);

        WritableFont font_scr_top = new WritableFont(WritableFont.TIMES,11,WritableFont.BOLD);
        font_scr_top.setColour(Colour.BLACK);

        WritableCellFormat cellFormat_gen = new WritableCellFormat(font_gen);
        cellFormat_gen.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);


        WritableCellFormat cellFormat_scr_top = new WritableCellFormat(font_scr_top);
        cellFormat_scr_top.setBackground(Colour.RED);
        cellFormat_scr_top.setAlignment(Alignment.LEFT);

        sheet.setColumnView(1,36);
        sheet.addCell(new Label(1, 0, "Safety score with weighted categories",cellFormat_scr_top));

        sheet.addCell(new Label(2, 1, "NA",cellFormat_gen));
        sheet.addCell(new Label(3, 1, "Yes",cellFormat_gen));
        sheet.addCell(new Label(4, 1, "No",cellFormat_gen));
        sheet.addCell(new Label(5, 1, "TP",cellFormat_gen));
        sheet.addCell(new Label(6, 1, "Score",cellFormat_gen));

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

//        ArrayList<GroupItem> survey = DbHelper.getSurveyDetails(surveyId);

//        if (survey == null) {
//            return;
//        }

        int totalYes = 0, totalNo = 0, totalNa = 0, totalPoints = 0;
        int totalYes_w = 0, totalNo_w = 0, totalNa_w = 0, totalPoints_w = 0;

        int i;
        for (i = 0; i < elements.size(); i++) {

            int no_w = 0, nan_w = 0, yes_w = 0, tp_w = 0, score;
            int no=0, nan=0 ,yes=0 , tp=0 ;
            for (GroupItem groupItem : groupItemsArrayList) {
                ArrayList<ChildItem> childItems = groupItem.getChildItems();
                for (ChildItem childItem : childItems) {
                    if (childItem.getElement().equals(elements.get(i))) {
                        switch (childItem.getResponse()) {
                            case -1:

                                no_w += childItem.getWeight();
                                no+=1;
                                tp+=1;
                                break;
                            case 0:
                                nan_w += childItem.getWeight();
                                nan+=1;
                                break;
                            case 1:
                                yes_w += childItem.getWeight();
                                yes+=1;
                                tp+=1;
                                break;
                        }
                    }
                }

            }

            // totalYes_w += yes_w;
            // totalNo_w += no_w;
            // totalNa_w += nan_w;
            // tp_w += (yes_w + no_w);
            // totalPoints_w += tp_w;
            // score = (int) (((float) yes_w / (yes_w + no_w)) * 100);
            totalYes +=yes;
            totalNo+= no;
            totalNa+=nan;
            totalPoints +=tp;
            score = (int) (((float) yes / (yes + no)) * 100);
            //   Log.e("AAAAA values", "NA :" + nan + " |Yes :" + yes + " |No :" + no + " |TP :" + (yes + no) + " |Score : " + ((float) yes / (yes + no)) * 100);

            WritableCellFormat cellFormat_scr_element = new WritableCellFormat();
            cellFormat_scr_element.setAlignment(Alignment.LEFT);
            cellFormat_scr_element.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);

            //           if (i==3){cellFormat_scr_element.setBackground(Colour.GREEN);}
            //           else if (i==4){cellFormat_scr_element.setBackground(Colour.ROSE);}
            //           else if(i==5){cellFormat_scr_element.setBackground(Colour.YELLOW);}


            sheet.addCell(new Label(1, i + 2, elements.get(i),cellFormat_scr_element));
            // sheet.addCell(new Label(2, i + 2, String.valueOf(nan)));
            // sheet.addCell(new Label(3, i + 2, String.valueOf(yes)));
            // sheet.addCell(new Label(4, i + 2, String.valueOf(no)));
            // sheet.addCell(new Label(5, i + 2, String.valueOf(tp)));
            // sheet.addCell(new Label(6, i + 2, score + "%"));
            sheet.addCell(new Number(2, i + 2, nan,cellFormat_gen));
            sheet.addCell(new Number(3, i + 2, yes,cellFormat_gen));
            sheet.addCell(new Number(4, i + 2, no,cellFormat_gen));
            sheet.addCell(new Number(5, i + 2,tp,cellFormat_gen));
            WritableCellFormat cellFormat_scr_element_score = new WritableCellFormat();
            cellFormat_scr_element_score.setAlignment(Alignment.RIGHT);
            cellFormat_scr_element_score.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);

            sheet.addCell(new Label(6, i + 2, score + "%",cellFormat_scr_element_score));


        }

        WritableCellFormat cellFormat_scr_total = new WritableCellFormat(font_scr_top);
        cellFormat_scr_total.setAlignment(Alignment.CENTRE);
        cellFormat_scr_total.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);

        //  sheet.addCell(new Label(1, i + 2, "TOTAL",cellFormat_scr_total));
        //  sheet.addCell(new Label(2, i + 2, String.valueOf(totalNa)));
        //  sheet.addCell(new Label(3, i + 2, String.valueOf(totalYes)));
        //  sheet.addCell(new Label(4, i + 2, String.valueOf(totalNo)));
        //  sheet.addCell(new Label(5, i + 2, String.valueOf(totalPoints)));
        //  sheet.addCell(new Label(6, i + 2, (int) (((float) totalYes / (totalYes + totalNo)) * 100) + "%"));

        sheet.addCell(new Number(2, i + 2, totalNa,cellFormat_gen));
        sheet.addCell(new Number(3, i + 2, totalYes,cellFormat_gen));
        sheet.addCell(new Number(4, i + 2, totalNo,cellFormat_gen));
        sheet.addCell(new Number(5, i + 2, totalPoints,cellFormat_gen));
//        WritableCellFormat cellFormat_scr_total_scr = new WritableCellFormat(font_scr_top);
//        cellFormat_scr_total_scr.setAlignment(Alignment.RIGHT);
//        cellFormat_scr_total_scr.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
//        sheet.addCell(new Label(6, i + 2, (int) (((float) totalYes / (totalYes + totalNo)) * 100) + "%",cellFormat_scr_total_scr));

        WritableCellFormat cellFormat_overall = new WritableCellFormat(font_scr_top);
        cellFormat_overall.setAlignment(Alignment.LEFT);
        cellFormat_overall.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
        cellFormat_overall.setBackground(Colour.RED);
        sheet.addCell(new Label(3, i + 5,"Overall Score  =",cellFormat_overall));
        sheet.mergeCells(3,i+5,4,i+5);

        WritableCellFormat cellFormat_overall_scr = new WritableCellFormat(font_scr_top);
        cellFormat_overall_scr.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
        cellFormat_overall_scr.setBackground(Colour.AQUA);
        sheet.addCell(new Number(5, i + 5, (int) (((float) totalYes / (totalYes + totalNo)) * 100),cellFormat_overall_scr));

    }


    private void writeOFI(WritableWorkbook workbook, ArrayList<OFIBeans> ofiBeansArrayList) throws WriteException {


        WritableSheet sheet = workbook.createSheet("OFI", 3);


        WritableFont font_scr_top = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
        font_scr_top.setColour(Colour.BLACK);

        WritableCellFormat cellFormat_scr_top = new WritableCellFormat(font_scr_top);
        cellFormat_scr_top.setBackground(Colour.YELLOW);
        cellFormat_scr_top.setAlignment(Alignment.CENTRE);
        cellFormat_scr_top.setVerticalAlignment(VerticalAlignment.CENTRE);

        cellFormat_scr_top.setWrap(true);
        sheet.setColumnView(1,40);
        sheet.setColumnView(2,20);
        sheet.setColumnView(3,20);

      //====================================
       // sheet.setColumnView(4,30);
      //====================================
        sheet.addCell(new Label(0, 0, "Site Safety Review- Tracker for Non Compliances", cellFormat_scr_top));
        sheet.mergeCells(0,0,3,0);


        WritableFont font_scr_heading = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD);
        font_scr_heading.setColour(Colour.BLACK);

        WritableCellFormat cellFormat_scr_heading = new WritableCellFormat(font_scr_heading);
        cellFormat_scr_heading.setAlignment(Alignment.CENTRE);
        cellFormat_scr_heading.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat_scr_heading.setWrap(true);


        WritableFont font_gen = new WritableFont(WritableFont.TIMES);
        font_gen.setColour(Colour.BLACK);

        WritableCellFormat cellFormat_gen = new WritableCellFormat(font_gen);
        cellFormat_gen.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
        cellFormat_gen.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat_gen.setWrap(true);


        sheet.addCell(new Label(0, 1, "Sr. No.", cellFormat_scr_heading));
      //  sheet.addCell(new Label(1, 1, "Details of Finding/Non compliance reported* ", cellFormat_scr_heading));

       //=================to remve details of Finding/non Compliance =======================================
        sheet.addCell(new Label(1,1,"Remarks*",cellFormat_scr_heading));
        //========================================================
        sheet.addCell(new Label(2, 1, "Category* ", cellFormat_scr_heading));
        sheet.addCell(new Label(3, 1, "Ref section Number*", cellFormat_scr_heading));
       //===========================================================================

            //adding remarks
       // sheet.addCell(new Label(4, 1, "Remarks*", cellFormat_scr_heading));
        //==========================================================================


        for (int i = 0; i < ofiBeansArrayList.size(); i++) {


            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setAlignment(Alignment.LEFT);
            cellFormat.setWrap(true);

            OFIBeans ofiBeans = ofiBeansArrayList.get(i);

            sheet.addCell(new Label(0,i+2,String.valueOf(i+1),cellFormat_gen));
         //==========remve detaill and complaince questions=============================
            //   sheet.addCell(new Label(1,i+2,ofiBeans.getDetails(),cellFormat_gen));
          //============================================================================
            sheet.addCell(new Label(1,i+2,ofiBeans.getRemarks(),cellFormat_gen));
            sheet.addCell(new Label(2,i+2,ofiBeans.getCategory(),cellFormat_gen));
            sheet.addCell(new Label(3,i+2,ofiBeans.getRef_no(),cellFormat_gen));
            //sheet.addCell(new Label(4,i+2,ofiBeans.getRemarks(),cellFormat_gen));



        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            generateExcel();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
        return null;
    }


}

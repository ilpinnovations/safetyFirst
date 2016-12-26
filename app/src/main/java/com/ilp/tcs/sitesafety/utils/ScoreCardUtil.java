package com.ilp.tcs.sitesafety.utils;

import android.content.res.Resources;
import android.util.Log;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;
import com.ilp.tcs.sitesafety.modals.ChildItem;
import com.ilp.tcs.sitesafety.modals.GroupItem;

import java.util.ArrayList;

/**
 * Created by 1239940 on 6/9/2016.
 * ScoreCardUtil
 */
public class ScoreCardUtil {


    private static final String TAG = ScoreCardUtil.class.getSimpleName();

    public ArrayList<Integer> calculateScore(ArrayList<GroupItem> groupItemArrayList) {
        ArrayList<Integer> score = new ArrayList<>();
        int i = 0, swt = 0, swu = 0, sno = 0, stp = 0, scoreWeightedCategory, scoreWeightedQuestion;
        int scoreCategory[] = new int[10];
        int scoreQuestion[] = new int[10];
        int w[] = {2, 7, 5, 4, 4, 6, 2, 3, 5, 2};
        ArrayList<String> elements = new ArrayList<>();
        Resources resource = SiteSafetyApplication.getContext().getResources();
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

        for (String elem : elements) {
            int na = 0, no = 0, yes = 0, wt, wu, tq, tp;
            for (GroupItem grp : groupItemArrayList) {
                ArrayList<ChildItem> childItemArrayList = grp.getChildItems();
                for (ChildItem chld : childItemArrayList) {
                    if (chld.getElement().equals(elem)) {
                        switch (chld.getResponse()) {
                            case -1:
                                no++;
                                break;
                            case 0:
                                na++;
                                break;
                            case 1:
                                yes++;
                                break;
                        }
                    }
                }
            }
            wu = w[i] * no;
            tq = yes + no;
            wt = w[i] * tq;
            if (wt != 0)
                scoreCategory[i] = ((wt - wu) / wt) * 100;
            else
                scoreCategory[i] = 0;
            tp = yes + no;
            if (tp != 0)
                scoreQuestion[i] = (yes / tp) * 100;
            else
                scoreQuestion[i] = 0;
            Log.d(elements.get(i), yes + "  " + no + "  " + na + "  " + tq + "  " + w[i] + "  " + wu + "  " + wt + "  " + scoreCategory[i]);
            Log.d(elements.get(i), yes + "  " + no + "  " + na + "  " + tp + "  " + scoreQuestion[i]);
            swt = swt + wt;
            swu = swu + wu;
            sno = sno + no;
            stp = stp + tp;
            i++;
        }
        if (swt != 0)
            scoreWeightedCategory = ((swt - swu) / swt) * 100;
        else
            scoreWeightedCategory = 0;
        if (stp != 0)
            scoreWeightedQuestion = (sno / stp) * 100;
        else
            scoreWeightedQuestion = 0;
        score.add(scoreWeightedCategory);
        score.add(scoreWeightedQuestion);
        return score;
    }

    public static int getScore(ArrayList<GroupItem> survey) {
        int score = 0;
        int totalYes = 0, totalNo = 0, totalNa = 0;

        Resources resource = SiteSafetyApplication.getContext().getResources();
        ArrayList<String> elements = new ArrayList<>();
        elements.add(resource.getString(R.string.general_safety));
        elements.add(resource.getString(R.string.fire_safety));
        elements.add(resource.getString(R.string.electrical_safety));
        elements.add(resource.getString(R.string.first_aid));
        elements.add(resource.getString(R.string.emergency_prep));
        elements.add(resource.getString(R.string.workplace_safety));
        elements.add(resource.getString(R.string.safe_work));
        elements.add(resource.getString(R.string.vehicular_safety));
        elements.add(resource.getString(R.string.contractor_safety));
        elements.add(resource.getString(R.string.health_hygiene));
        elements.add(resource.getString(R.string.plant_machinery_euip));
        elements.add(resource.getString(R.string.use_of_PPEs));
        elements.add(resource.getString(R.string.Chemical_Safety));
        elements.add(resource.getString(R.string.Safety_Signage));
        elements.add(resource.getString(R.string.housekeeping));

        for (String element : elements) {
            int no = 0, na = 0, yes = 0;
            for (GroupItem groupItem : survey) {
                ArrayList<ChildItem> childItems = groupItem.getChildItems();
                for (ChildItem childItem : childItems) {
                    if (childItem.getElement().equals(element)) {
                        switch (childItem.getResponse()) {
                            case -1:
                                no++;
                                break;
                            case 0:
                                na++;
                                break;
                            case 1:
                                yes++;
                                break;
                        }
                    }
                }
            }

            totalYes += yes;
            totalNo += no;
            totalNa += na;

            Log.e(TAG, "NA :" + na + " |Yes :" + yes + " |No :" + no + " |TP :" + (yes + no) + " |Score : " + ((float) yes / (yes + no)) * 100);
        }

        Log.e(TAG, "Total Score :" + (float) totalYes / (totalYes + totalNo) * 100);

        return score;
    }
}

package com.ilp.tcs.sitesafety.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.activity.SiteSafetyApplication;
import com.ilp.tcs.sitesafety.database.BranchDb;
import com.ilp.tcs.sitesafety.modals.Datas;
import com.ilp.tcs.sitesafety.views.ViewHolder;
import com.ilp.tcs.sitesafety.vo.Branch;

/**
 * Created by Fedric Antony on 7/5/2016.
 */

/**
 *  Modified by Abhishek Gupta on 17/8/2016.
 */


public class BranchAdapter extends BaseAdapter {

    private static final String TAG = BranchAdapter.class.getName();
    private LayoutInflater mInflater;
    private ViewHolder mHolder;
    private boolean enableDelete;
    private Context context;

    public BranchAdapter(boolean enableDelete) {
        mInflater = (LayoutInflater) SiteSafetyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.enableDelete = enableDelete;
    }

    @Override
    public int getCount() {
        return Datas.listBranches.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        context = parent.getContext();

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_branch, null, false);
            mHolder = new ViewHolder();
            mHolder.mTextViewOne = (TextView) convertView.findViewById(R.id.first_row);
            mHolder.delete = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.mTextViewOne.setText(Datas.listBranches.get(position).getName());
        if (enableDelete)
            mHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Branch b = Datas.listBranches.get(position);
//                    BranchDb.getInstance().deletes(Long.parseLong(b.getId()));
//                    Datas.listBranches.remove(position);
//                    notifyDataSetChanged();
  /*  Author   : Abhishek Gupta
*    Dated On : 16-aug-2016*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm Delete...?");
                    builder.setMessage("Are you sure you want delete Branch \n"+Datas.listBranches.get(position).getName());
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            Branch b = Datas.listBranches.get(position);
                            BranchDb.getInstance().deletes(Long.parseLong(b.getId()));
                            Datas.listBranches.remove(position);
                            notifyDataSetChanged();
                            // Write your code here to invoke YES event
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            //Toast.makeText(context, "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    builder.show();


                }
            });

        if (!enableDelete)
            mHolder.delete.setVisibility(View.GONE);
        else
            mHolder.delete.setVisibility(View.VISIBLE);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_branch, null, false);
            mHolder = new ViewHolder();
            mHolder.mTextViewOne = (TextView) convertView.findViewById(R.id.first_row);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        try {
            mHolder.mTextViewOne.setText(Datas.listBranches.get(position).getName());
        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }

        return convertView;
    }
}

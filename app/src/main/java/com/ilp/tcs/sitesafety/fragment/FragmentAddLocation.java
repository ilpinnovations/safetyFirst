package com.ilp.tcs.sitesafety.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.adapters.BranchAdapter;
import com.ilp.tcs.sitesafety.database.LocationDb;
import com.ilp.tcs.sitesafety.listeners.OnActivityFragmentCommunication;
import com.ilp.tcs.sitesafety.listeners.OnDataReceived;
import com.ilp.tcs.sitesafety.modals.Datas;
import com.ilp.tcs.sitesafety.tasks.AddAsyncTask;
import com.ilp.tcs.sitesafety.tasks.RetrieveAsyncTask;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnActivityFragmentCommunication} interface
 * to handle interaction events.
 */
public class FragmentAddLocation extends Fragment implements View.OnClickListener, OnDataReceived {

    private OnActivityFragmentCommunication mListener;

    private Spinner mSpnBranches;

    private TextInputLayout mTxtInputLayoutLocation;

    private Button mBtnAddLocation;

    private View mView;

    private BranchAdapter mAdapter;

    private String mBranchId;

    public FragmentAddLocation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_add_location, null, false);
        initializeComponents();
        return mView;
    }

    private void initializeComponents() {

        mSpnBranches = (Spinner) mView.findViewById(R.id.spn_branches);

        mAdapter = new BranchAdapter(false);

        mSpnBranches.setAdapter(mAdapter);

        mSpnBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mBranchId = Datas.listBranches.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBranchId = "";
            }
        });

        mTxtInputLayoutLocation = (TextInputLayout) mView.findViewById(R.id.txtinput_location);

        mBtnAddLocation = (Button) mView.findViewById(R.id.btn_add_location);
        mBtnAddLocation.setOnClickListener(this);

        if (Datas.listBranches.size() == 0) {
            new RetrieveAsyncTask(Constants.LIST_BRANCH, this).execute();
        }
    }

    private void insertLocation() {

        if (!Util.isEmpty(mTxtInputLayoutLocation) && !Util.isEmpty(mBranchId)) {

            ContentValues cv = new ContentValues();
            cv.put(LocationDb.NAME, Util.getString(mTxtInputLayoutLocation));
            cv.put(LocationDb.BRANCH_ID, mBranchId);
            new AddAsyncTask(Constants.ADD_LOCATION, cv, this).execute();
        } else {
            SToast.showSmallToast(R.string.reg_error);
            Util.showError(mTxtInputLayoutLocation, R.string.reg_error);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnActivityFragmentCommunication) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_add_location) {
            insertLocation();
        }
    }

    @Override
    public void onDataReceived(int result, long id, String msg) {

        if (result == Constants.ADD_LOCATION) {

            if (id > 0) {
                SToast.showSmallToast(R.string.location_success_msg);
                mListener.onActivityCommunication(Constants.LIST_LOCATION);
            } else {
                SToast.showSmallToast(R.string.location_failure_msg);
            }
        } else if (result == Constants.LIST_BRANCH) {
            mAdapter.notifyDataSetChanged();
        }
    }
}

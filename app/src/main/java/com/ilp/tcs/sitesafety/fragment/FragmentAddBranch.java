package com.ilp.tcs.sitesafety.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.database.BranchDb;
import com.ilp.tcs.sitesafety.listeners.OnActivityFragmentCommunication;
import com.ilp.tcs.sitesafety.listeners.OnDataReceived;
import com.ilp.tcs.sitesafety.tasks.AddAsyncTask;
import com.ilp.tcs.sitesafety.utils.Constants;
import com.ilp.tcs.sitesafety.utils.SToast;
import com.ilp.tcs.sitesafety.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnActivityFragmentCommunication} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class FragmentAddBranch extends Fragment implements View.OnClickListener, OnDataReceived {

    private OnActivityFragmentCommunication mListener;

    private TextInputLayout mTxtInputLayoutBranchName;

    private Button mBtnAdd;

    private View mView;

    public FragmentAddBranch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_add_branch, null, false);
        initializeComponents();
        return mView;
    }

    private void initializeComponents() {

        mTxtInputLayoutBranchName = (TextInputLayout) mView.findViewById(R.id.txtinput_branch);

        mBtnAdd = (Button) mView.findViewById(R.id.btn_add_branch);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnActivityFragmentCommunication) context;
    }

    private void insertBranch() {

        if (!Util.isEmpty(mTxtInputLayoutBranchName)) {
            ContentValues cv = new ContentValues();
            cv.put(BranchDb.NAME, Util.getString(mTxtInputLayoutBranchName));
            new AddAsyncTask(Constants.ADD_BRANCH, cv, this).execute();
        } else {
            Util.showError(mTxtInputLayoutBranchName, R.string.reg_error);
            SToast.showSmallToast(R.string.reg_error);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_add_branch) {

            insertBranch();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDataReceived(int result, long id, String msg) {

        if (result == Constants.ADD_BRANCH) {

            if (id > 0) {
                SToast.showSmallToast(R.string.branch_success_msg);
                mListener.onActivityCommunication(Constants.LIST_BRANCH);
            } else {
                SToast.showSmallToast(R.string.branch_failure_msg);
            }
        }
    }
}

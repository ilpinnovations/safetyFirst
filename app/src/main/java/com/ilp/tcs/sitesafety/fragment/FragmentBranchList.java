package com.ilp.tcs.sitesafety.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.adapters.BranchAdapter;
import com.ilp.tcs.sitesafety.listeners.OnDataReceived;
import com.ilp.tcs.sitesafety.modals.Datas;
import com.ilp.tcs.sitesafety.tasks.RetrieveAsyncTask;
import com.ilp.tcs.sitesafety.utils.Constants;

/**
 * Created by Fedric Antony on 7/5/2016.
 */
public class FragmentBranchList extends Fragment implements OnDataReceived {

    private ListView mListViewBranches;

    private View mView;

    private BranchAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_list_branch, null, false);
        initializeComponents();
        return mView;
    }

    private void initializeComponents() {

        Datas.listBranches.clear();
        mListViewBranches = (ListView) mView.findViewById(R.id.list_view_branches);

        mAdapter = new BranchAdapter(true);

        Datas.listBranches.clear();
        mListViewBranches.setAdapter(mAdapter);
        new RetrieveAsyncTask(Constants.LIST_BRANCH, this).execute();

    }

    @Override
    public void onDataReceived(int result, long id, String msg) {

        if (result == Constants.LIST_BRANCH) {
            mAdapter.notifyDataSetChanged();
        }
    }
}

package com.ilp.tcs.sitesafety.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ilp.tcs.sitesafety.R;
import com.ilp.tcs.sitesafety.adapters.LocationAdapter;
import com.ilp.tcs.sitesafety.listeners.OnDataReceived;
import com.ilp.tcs.sitesafety.modals.Datas;
import com.ilp.tcs.sitesafety.tasks.RetrieveAsyncTask;
import com.ilp.tcs.sitesafety.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListLocation extends Fragment implements OnDataReceived {


    private ListView mListViewLocations;

    private View mView;

    private LocationAdapter mAdapter;

    public FragmentListLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_list_location, container, false);
        initializeComponents();
        return mView;
    }

    private void initializeComponents() {

        Datas.listLocations.clear();
        mListViewLocations = (ListView) mView.findViewById(R.id.list_view_location);

        mAdapter = new LocationAdapter(true);

        Datas.listLocations.clear();
        mListViewLocations.setAdapter(mAdapter);

        new RetrieveAsyncTask(Constants.LIST_LOCATION, this).execute();
    }

    @Override
    public void onDataReceived(int result, long id, String msg) {

        if (result == Constants.LIST_LOCATION) {
            mAdapter.notifyDataSetChanged();
        }
    }
}

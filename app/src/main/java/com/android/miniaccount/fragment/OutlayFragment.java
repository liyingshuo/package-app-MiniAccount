package com.android.miniaccount.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.miniaccount.AccountApplication;
import com.android.miniaccount.R;
import com.android.miniaccount.activity.AccountEditActivity;
import com.android.miniaccount.adapter.OutlayRecyclerViewAdapter;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.entity.AccountItem;

import java.util.List;

public class OutlayFragment extends Fragment {

    private View mRootView;
    private RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OutlayFragment() {
    }

    public static OutlayFragment newInstance(String param1, String param2) {
        OutlayFragment fragment = new OutlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_outlay, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView_outlay);

        Button btOutlay = (Button)mRootView.findViewById(R.id.btAddOutlayItem);
        btOutlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddOnClick();
            }
        });

        refresh();
    }
    private void buttonAddOnClick() {
        Intent intent = new Intent(this.getActivity(), AccountEditActivity.class);
        intent.putExtra("isIncome",false);
        startActivity(intent);
    }

    private void refresh() {
        AccountApplication app = (AccountApplication)this.getActivity().getApplication();
        AccountDao dao = app.getDatabaseManager();
        List<AccountItem> AccountOutlayList = dao.getOutlayList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        OutlayRecyclerViewAdapter outlayRecyclerViewAdapter = new OutlayRecyclerViewAdapter(AccountOutlayList,this.getActivity());
        recyclerView.setAdapter(outlayRecyclerViewAdapter);

        TextView textViewOutlaySummary = (TextView)mRootView.findViewById(R.id.textViewOutlaySummary);
        textViewOutlaySummary.setText(String.valueOf(dao.getSumOutlay()));
    }
}
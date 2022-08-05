package com.android.miniaccount.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.miniaccount.AccountApplication;
import com.android.miniaccount.R;
import com.android.miniaccount.activity.AccountEditActivity;
import com.android.miniaccount.adapter.IncomeListViewAdapter;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.entity.AccountItem;

import java.util.List;

public class IncomeFragment extends Fragment {

    private View mRootView;
    private ListView listView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public IncomeFragment() {
    }

    public static IncomeFragment newInstance(String param1, String param2) {
        IncomeFragment fragment = new IncomeFragment();
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
        mRootView = inflater.inflate(R.layout.fragment_income, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        listView = (ListView)mRootView.findViewById(R.id.listView_income);

        Button btIncome = (Button)mRootView.findViewById(R.id.btAddIncomeItem);
        btIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddOnClick();
            }
        });

        refresh();
    }
    private void buttonAddOnClick() {
        Intent intent = new Intent(this.getActivity(), AccountEditActivity.class);
        intent.putExtra("isIncome",true);
        startActivity(intent);
    }

    private void refresh() {
        AccountApplication app = (AccountApplication)this.getActivity().getApplication();
        AccountDao dao = app.getDatabaseManager();
        List<AccountItem> AccountIncomeList = dao.getIncomeList();

        IncomeListViewAdapter incomeListViewAdapter = new IncomeListViewAdapter(AccountIncomeList, this.getActivity());
        listView.setAdapter(incomeListViewAdapter);

        TextView textViewIncomeSummary = (TextView)mRootView.findViewById(R.id.textViewIncomeSummary);
        textViewIncomeSummary.setText(String.valueOf(dao.getSumIncome()));
    }
}
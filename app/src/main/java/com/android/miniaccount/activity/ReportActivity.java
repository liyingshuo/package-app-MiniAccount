package com.android.miniaccount.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.android.miniaccount.AccountApplication;
import com.android.miniaccount.R;
import com.android.miniaccount.adapter.IncomeListViewAdapter;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.entity.AccountItem;

import java.util.List;

public class ReportActivity extends AppCompatActivity {
    EditText etBeginDate;
    EditText etEndDate;
    RadioGroup radioGroup;
    Button btOK;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        etBeginDate = (EditText)this.findViewById(R.id.etTimeFrom);
        etEndDate = (EditText)this.findViewById(R.id.etTimeTo);
        radioGroup = (RadioGroup)this.findViewById(R.id.radio_group);
        btOK = (Button)this.findViewById(R.id.OK);
        listView = (ListView)this.findViewById(R.id.listView);

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
    }

    private void query() {
        String beginDate = etBeginDate.getText().toString().trim();
        String endDate = etEndDate.getText().toString().trim();

        AccountApplication application = (AccountApplication)this.getApplication();
        AccountDao dao = application.getDatabaseManager();

        List<AccountItem> list ;
        if (radioGroup.getCheckedRadioButtonId() == R.id.bt_income)
            list = dao.queryIncomeList(beginDate,endDate);
        else
            list = dao.queryOutlayList(beginDate,endDate);

        //ArrayAdapter<AccountItem> adapter = new ArrayAdapter<AccountItem>(this,R.layout.income_list_view_item,list);
        IncomeListViewAdapter adapter = new IncomeListViewAdapter(list, this);
        listView.setAdapter(adapter);
    }
}
package com.android.miniaccount.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.miniaccount.AccountApplication;
import com.android.miniaccount.MainActivity;
import com.android.miniaccount.R;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.entity.AccountCategory;
import com.android.miniaccount.entity.AccountItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountEditActivity extends AppCompatActivity {
    private List<AccountCategory> categoryList;
    private String[] mIncomeFrom = {"icon","title"};
    private int[] mIncomeTo = {R.id.ivCategory,R.id.tvCategory};
    private TextView tvSelectType;
    private boolean isIncome;
    EditText etMoney;
    EditText etRemark;
    EditText etDate;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        tvSelectType = (TextView)this.findViewById(R.id.tvSelectType);
        etMoney = (EditText) this.findViewById(R.id.etMoney);
        etRemark = (EditText) this.findViewById(R.id.etRemark);
        etDate = (EditText) this.findViewById(R.id.etDate);
        bt = (Button)this.findViewById(R.id.btOK);

        isIncome = this.getIntent().getBooleanExtra("isIncome",true);
        if (isIncome)
            tvSelectType.setText("工资");
        else
            tvSelectType.setText("住房");
        initView();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick();
            }
        });
    }

    private void buttonOnClick() {
        AccountItem item = new AccountItem();
        item.setCategory(tvSelectType.getText().toString());
        item.setRemark(etRemark.getText().toString());
        item.setMoney(Double.parseDouble(etMoney.getText().toString()));
        item.setDate(etDate.getText().toString());

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //item.setDate(sdf.format(new Date()));

        AccountApplication app = (AccountApplication)this.getApplication();
        AccountDao dao = app.getDatabaseManager();
        if (isIncome)
            dao.addIncomeList(item);
        else
            dao.addOutlayList(item);

        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        if (isIncome)
            intent.putExtra("fragmentFlag",1);
        else
            intent.putExtra("fragmentFlag",2);
        startActivity(intent);
    }

    private void initView() {
        AccountApplication app = (AccountApplication)this.getApplication();
        AccountDao dao = app.getDatabaseManager();
        if (isIncome)
            categoryList = dao.getIncomeType();
        else
            categoryList = dao.getOutlayType();

        GridView gridView = this.findViewById(R.id.gv);
        List<Map<String,Object>> outlayList = new ArrayList<Map<String,Object>>();
        for (AccountCategory c:categoryList){
            Map<String,Object> map = new HashMap<>();
            map.put("icon",c.getIcon());
            map.put("title",c.getCategory());
            outlayList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,outlayList,R.layout.category_item,mIncomeFrom,mIncomeTo);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridViewOnItemClick(position);
            }
        });
    }

    private void gridViewOnItemClick(int position) {
        tvSelectType.setText(categoryList.get(position).toString());
    }
}
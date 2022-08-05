package com.android.miniaccount.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.android.miniaccount.AccountApplication;
import com.android.miniaccount.R;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.entity.AccountCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    private GridView gvCategoryIncome;
    private GridView gvCategoryOutlay;
    private Button btIncomeType;
    private Button btOutlayType;

    private String[] mIncomeFrom = {"icon","title"};
    private int[] mIncomeTo = {R.id.ivCategory,R.id.tvCategory};
    private String[] mOutlayFrom = {"icon","title"};
    private int[] mOutlayTo = {R.id.ivCategory,R.id.tvCategory};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        gvCategoryIncome = (GridView)this.findViewById(R.id.gvIncome);
        gvCategoryOutlay = (GridView)this.findViewById(R.id.gvOutlay);
        btIncomeType = (Button)this.findViewById(R.id.btAddIncomeCategory);
        btOutlayType = (Button)this.findViewById(R.id.btAddOutlayCategory);

        btIncomeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonAddIncomeCategoryOnClick();
            }
        });
        btOutlayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonAddOutlayCategoryOnClick();
            }
        });

        refresh(gvCategoryIncome, gvCategoryOutlay);
    }

    private void ButtonAddIncomeCategoryOnClick() {
        Intent intent = this.getIntent();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加收入类别");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = input.getText().toString();
                AccountCategory item = new AccountCategory();
                item.setId(0);
                item.setCategory(category);
                int icon = R.drawable.ic_launcher_foreground;
                item.setIcon(icon);
                AccountApplication app = (AccountApplication)SettingActivity.this.getApplication();
                AccountDao dao = app.getDatabaseManager();
                dao.addIncomeType(item);
                refresh(gvCategoryIncome, gvCategoryOutlay);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void ButtonAddOutlayCategoryOnClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加支出类别");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = input.getText().toString();
                AccountCategory item = new AccountCategory();
                item.setId(0);
                item.setCategory(category);
                int icon = R.drawable.ic_launcher_background;
                item.setIcon(icon);
                AccountApplication app = (AccountApplication)SettingActivity.this.getApplication();
                AccountDao dao = app.getDatabaseManager();
                dao.addOutlayType(item);
                refresh(gvCategoryIncome, gvCategoryOutlay);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void refresh(GridView gvCategoryIncome, GridView gvCategoryOutlay) {
        AccountApplication app = (AccountApplication)this.getApplication();
        AccountDao dao = app.getDatabaseManager();

        List<AccountCategory> incomeCategoryList = dao.getIncomeType();
        List<Map<String,Object>> incomeList = new ArrayList<Map<String,Object>>();
        for (AccountCategory c:incomeCategoryList){
            Map<String,Object> map = new HashMap<>();
            map.put("icon",c.getIcon());
            map.put("title",c.getCategory());
            incomeList.add(map);
        }
        SimpleAdapter adapterIncome = new SimpleAdapter(this,incomeList,R.layout.category_item,mIncomeFrom,mIncomeTo);
        gvCategoryIncome.setAdapter(adapterIncome);

        List<AccountCategory> outlayCategoryList = dao.getOutlayType();
        List<Map<String,Object>> outlayList = new ArrayList<Map<String,Object>>();
        for (AccountCategory c:outlayCategoryList){
            Map<String,Object> map = new HashMap<>();
            map.put("icon",c.getIcon());
            map.put("title",c.getCategory());
            outlayList.add(map);
        }
        SimpleAdapter adapterOutlay = new SimpleAdapter(this,outlayList,R.layout.category_item,mOutlayFrom,mOutlayTo);
        gvCategoryOutlay.setAdapter(adapterOutlay);
    }
}
package com.android.miniaccount.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.miniaccount.AccountApplication;
import com.android.miniaccount.R;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.entity.AccountItem;

import java.util.List;

public class IncomeListViewAdapter extends BaseAdapter {

    public class IncomeItemViewHolder{
        TextView tvCategory;
        TextView tvRemark;
        TextView tvMoney;
        TextView tvDate;
        ImageView ivIcon;

        public IncomeItemViewHolder(@NonNull View view) {
            tvCategory = (TextView)view.findViewById(R.id.textViewCategory);
            tvRemark = (TextView)view.findViewById(R.id.textViewRemark);
            tvMoney = (TextView)view.findViewById(R.id.textViewMoney);
            tvDate = (TextView)view.findViewById(R.id.textViewDate);
            ivIcon = (ImageView)view.findViewById(R.id.imageViewIcon);
        }
    }

    private List<AccountItem> mItems;
    private final LayoutInflater layoutInflater;
    private AlertDialog.Builder mDialogBuilder;
    private Activity activity;

    public IncomeListViewAdapter(List<AccountItem> mItems, Activity context) {
        this.mItems = mItems;
        this.layoutInflater = LayoutInflater.from(context);
        this.mDialogBuilder = new AlertDialog.Builder(context);
        this.activity = context;
    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.income_list_view_item,null);
        IncomeListViewAdapter.IncomeItemViewHolder incomeItemViewHolder = new IncomeListViewAdapter.IncomeItemViewHolder(view);

        AccountItem item = mItems.get(position);
        incomeItemViewHolder.tvCategory.setText(item.getCategory());
        incomeItemViewHolder.tvRemark.setText(item.getRemark());
        incomeItemViewHolder.tvMoney.setText(String.valueOf(item.getMoney()));
        incomeItemViewHolder.tvDate.setText(item.getDate());
        int icon = R.drawable.ic_menu_slideshow;
        if(icon > 0){
            incomeItemViewHolder.ivIcon.setImageResource(icon);
        }
        view.setTag(item.getId());

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int id = (int)v.getTag();
                deleteItem(id);
                return true;
            }
        });
        return view;
    }

    private void deleteItem(int id) {
        mDialogBuilder.setTitle("提示");
        mDialogBuilder.setMessage("确认要删除选择的数据吗?");
        mDialogBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRecord(id);
            }
        });
        mDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mDialogBuilder.show();
    }

    private void deleteRecord(int id) {
        for (int i=mItems.size()-1;i>=0; i--){
            if (mItems.get(i).getId() == id){
                mItems.remove(i);
            }
        }
        AccountApplication app = (AccountApplication)activity.getApplication();
        AccountDao dao = app.getDatabaseManager();
        dao.deleteIncomeList(id);
        this.notifyDataSetChanged();
    }
}

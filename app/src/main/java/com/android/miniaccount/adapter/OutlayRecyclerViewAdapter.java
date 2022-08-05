package com.android.miniaccount.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.miniaccount.AccountApplication;
import com.android.miniaccount.R;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.entity.AccountItem;

import java.util.List;

public class OutlayRecyclerViewAdapter extends RecyclerView.Adapter<OutlayRecyclerViewAdapter.OutlayItemViewHolder> {

    public class OutlayItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategory;
        TextView tvRemark;
        TextView tvMoney;
        TextView tvDate;
        ImageView ivIcon;
        ImageView ivDelete;

        public OutlayItemViewHolder(@NonNull View view) {
            super(view);
            tvCategory = (TextView)view.findViewById(R.id.textViewCategory);
            tvRemark = (TextView)view.findViewById(R.id.textViewRemark);
            tvMoney = (TextView)view.findViewById(R.id.textViewMoney);
            tvDate = (TextView)view.findViewById(R.id.textViewDate);
            ivIcon = (ImageView)view.findViewById(R.id.imageViewIcon);
            ivDelete = (ImageView)view.findViewById(R.id.imageViewDelete);
        }
    }

    private List<AccountItem> mItems;
    private final LayoutInflater layoutInflater;
    private AlertDialog.Builder mDialogBuilder;
    private Activity activity;


    public OutlayRecyclerViewAdapter(List<AccountItem> mItems, Activity context) {
        this.mItems = mItems;
        this.layoutInflater = LayoutInflater.from(context);
        this.mDialogBuilder = new AlertDialog.Builder(context);
        this.activity = context;
    }

    @NonNull
    @Override
    public OutlayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.outlay_recycler_view_item,null);
        OutlayItemViewHolder outlayItemViewHolder = new OutlayItemViewHolder(view);
        return outlayItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OutlayItemViewHolder outlayItemViewHolder, int position) {
        AccountItem item = mItems.get(position);
        outlayItemViewHolder.tvCategory.setText(item.getCategory());
        outlayItemViewHolder.tvRemark.setText(item.getRemark());
        outlayItemViewHolder.tvMoney.setText(String.valueOf(item.getMoney()));
        outlayItemViewHolder.tvDate.setText(item.getDate());
        int icon = R.drawable.ic_menu_slideshow;
        if(icon > 0){
            outlayItemViewHolder.ivIcon.setImageResource(icon);
        }
        outlayItemViewHolder.ivDelete.setTag(item.getId());

        outlayItemViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = (int)v.getTag();
                deleteItem(id);
            }
        });
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
        dao.deleteOutlayList(id);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }
}

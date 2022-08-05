package com.android.miniaccount.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.miniaccount.entity.AccountCategory;
import com.android.miniaccount.entity.AccountItem;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final DataBaseHelper helper;
    private final SQLiteDatabase db;

    public AccountDao(Context context){
        helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void onCreate(){
        helper.onCreate(db);
    }
    public void onDelete(){
        helper.onDelete(db);
    }

    public List<AccountItem> getIncomeList(){
        ArrayList<AccountItem> result = new ArrayList<>();
        Cursor cursor = db.query("accountIncome",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            AccountItem item = new AccountItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            item.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            item.setDate(cursor.getString(cursor.getColumnIndex("date")));
            item.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
            result.add(item);
        }
        cursor.close();
        return result;
    }
    public List<AccountItem> getOutlayList(){
        ArrayList<AccountItem> result = new ArrayList<>();
        Cursor cursor = db.query("accountOutlay",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            AccountItem item = new AccountItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            item.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            item.setDate(cursor.getString(cursor.getColumnIndex("date")));
            item.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public List<AccountCategory> getIncomeType(){
        ArrayList<AccountCategory> result = new ArrayList<>();
        Cursor cursor = db.query("accountIncomeType",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            AccountCategory item = new AccountCategory();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            item.setIcon(cursor.getInt(cursor.getColumnIndex("icon")));
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public List<AccountCategory> getOutlayType(){
        ArrayList<AccountCategory> result = new ArrayList<>();
        Cursor cursor = db.query("accountOutlayType",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            AccountCategory item = new AccountCategory();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            item.setIcon(cursor.getInt(cursor.getColumnIndex("icon")));
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public void addIncomeList(AccountItem item) {
        String sql;
        sql = String.format("insert into accountIncome(id,category,remark,date,money) values(null,'%s','%s','%s',%1.0f)",item.getCategory(),item.getRemark(),item.getDate(),item.getMoney());
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void addOutlayList(AccountItem item) {
        String sql;
        sql = String.format("insert into accountOutlay(id,category,remark,date,money) values(null,'%s','%s','%s',%1.0f)",item.getCategory(),item.getRemark(),item.getDate(),item.getMoney());
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void addIncomeType(AccountCategory item) {
        String sql;
        sql = String.format("insert into accountIncomeType(id,category,icon) values(null,'%s',%d)",item.getCategory(),item.getIcon());
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void addOutlayType(AccountCategory item) {
        String sql;
        sql = String.format("insert into accountOutlayType(id,category,icon) values(null,'%s',%d)",item.getCategory(),item.getIcon());
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void deleteIncomeList(long id) {
        String sql;
        sql = String.format("delete from accountIncome where id=%d",id);
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void deleteOutlayList(long id) {
        String sql;
        sql = String.format("delete from accountOutlay where id=%d",id);
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public List<AccountItem> queryIncomeList(String beginDate, String endDate){
        ArrayList<AccountItem> result = new ArrayList<>();
        String sql = String.format("select id,category,remark,date,money from accountIncome where date>='%s' and date<='%s'",beginDate,endDate);
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            AccountItem item = new AccountItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            item.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            item.setDate(cursor.getString(cursor.getColumnIndex("date")));
            item.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public List<AccountItem> queryOutlayList(String beginDate, String endDate){
        ArrayList<AccountItem> result = new ArrayList<>();
        String sql = String.format("select id,category,remark,date,money from accountIncome where date>='%s' and date<='%s'",beginDate,endDate);
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            AccountItem item = new AccountItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("id")));
            item.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            item.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            item.setDate(cursor.getString(cursor.getColumnIndex("date")));
            item.setMoney(cursor.getInt(cursor.getColumnIndex("money")));
            result.add(item);
        }
        cursor.close();
        return result;
    }

    public double getSumIncome(){
        double result = 0;
        String sql = "select sum(money) as money from accountIncome";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            result = cursor.getDouble(cursor.getColumnIndex("money"));
        }
        cursor.close();
        return result;
    }
    public double getSumOutlay(){
        double result = 0;
        String sql = "select sum(money) as money from accountOutlay";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToNext()){
            result = cursor.getDouble(cursor.getColumnIndex("money"));
        }
        cursor.close();
        return result;
    }
}

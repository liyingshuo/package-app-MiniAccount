package com.android.miniaccount.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.miniaccount.R;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "account.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "create table accountIncomeType(id integer primary key autoincrement, category text, icon integer)";
        db.execSQL(sql);
        sql = "create table accountOutlayType(id integer primary key autoincrement, category text, icon integer)";
        db.execSQL(sql);
        sql = "create table accountIncome(id integer primary key autoincrement, category text, remark text, date text, money real)";
        db.execSQL(sql);
        sql = "create table accountOutlay(id integer primary key autoincrement, category text, remark text, date text, money real)";
        db.execSQL(sql);
        initData(db);
    }

    public void onDelete(SQLiteDatabase db){
        String sql;
        sql = "drop table accountIncomeType";
        db.execSQL(sql);
        sql = "drop table accountOutlayType";
        db.execSQL(sql);
        sql = "drop table accountIncome";
        db.execSQL(sql);
        sql = "drop table accountOutlay";
        db.execSQL(sql);
    }

    private void initData(SQLiteDatabase db) {
        Log.d("TAG", "initData: DataBaseHelper");
        String sql;
        sql = String.format("insert into accountIncomeType(category,icon) values('工资',%d)",R.drawable.ic_menu_camera);
        db.execSQL(sql);
        sql = String.format("insert into accountIncomeType(category,icon) values('奖金',%d)",R.drawable.ic_menu_gallery);
        db.execSQL(sql);
        sql = String.format("insert into accountIncomeType(category,icon) values('兼职',%d)",R.drawable.ic_menu_slideshow);
        db.execSQL(sql);
        sql = String.format("insert into accountIncomeType(category,icon) values('其他',%d)",R.drawable.ic_menu_camera);
        db.execSQL(sql);

        sql = String.format("insert into accountOutlayType(category,icon) values('住房',%d)",R.drawable.ic_menu_camera);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlayType(category,icon) values('食物',%d)",R.drawable.ic_menu_gallery);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlayType(category,icon) values('交通',%d)",R.drawable.ic_menu_slideshow);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlayType(category,icon) values('购物',%d)",R.drawable.ic_menu_slideshow);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlayType(category,icon) values('其他',%d)",R.drawable.ic_menu_camera);
        db.execSQL(sql);

        sql = String.format("insert into accountIncome(category,remark,date,money) values('工资','aaa','2021-09-14',%d)",10000);
        db.execSQL(sql);
        sql = String.format("insert into accountIncome(category,remark,date,money) values('奖金','bbb','2021-09-14',%d)",20000);
        db.execSQL(sql);
        sql = String.format("insert into accountIncome(category,remark,date,money) values('兼职','ccc','2021-09-10',%d)",1000);
        db.execSQL(sql);
        sql = String.format("insert into accountIncome(category,remark,date,money) values('工资','ddd','2021-08-14',%d)",10000);
        db.execSQL(sql);
        sql = String.format("insert into accountIncome(category,remark,date,money) values('兼职','eee','2021-08-10',%d)",2000);
        db.execSQL(sql);

        sql = String.format("insert into accountOutlay(category,remark,date,money) values('住房','aaa','2021-09-14',%d)",2000);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlay(category,remark,date,money) values('食物','bbb','2021-09-14',%d)",1000);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlay(category,remark,date,money) values('交通','ccc','2021-09-10',%d)",1000);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlay(category,remark,date,money) values('购物','ddd','2021-08-14',%d)",2000);
        db.execSQL(sql);
        sql = String.format("insert into accountOutlay(category,remark,date,money) values('食物','eee','2021-08-10',%d)",1000);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

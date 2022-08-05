package com.android.miniaccount.entity;

public class AccountItem {
    private int id;
    private String category;
    private String remark;
    private String date;
    private double money;

    public AccountItem() {
    }

    public AccountItem(String category, String remark, String date, double money) {
        this.category = category;
        this.remark = remark;
        this.date = date;
        this.money = money;
    }

    public AccountItem(int id, String category, String remark, String date, double money) {
        this.id = id;
        this.category = category;
        this.remark = remark;
        this.date = date;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return this.category + this.remark + this.date + this.money;
    }
}

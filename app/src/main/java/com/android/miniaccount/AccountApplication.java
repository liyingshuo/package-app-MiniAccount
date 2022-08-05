package com.android.miniaccount;

import android.app.Application;

import com.android.miniaccount.db.AccountDao;

public class AccountApplication extends Application {
    private AccountDao mDatabaseManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabaseManager = new AccountDao(this);
    }

    public AccountDao getDatabaseManager() {
        return mDatabaseManager;
    }
}

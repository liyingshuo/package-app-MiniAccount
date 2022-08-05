package com.android.miniaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.android.miniaccount.activity.ReportActivity;
import com.android.miniaccount.activity.SettingActivity;
import com.android.miniaccount.adapter.ViewPagerAdapter;
import com.android.miniaccount.db.AccountDao;
import com.android.miniaccount.fragment.IncomeFragment;
import com.android.miniaccount.fragment.OutlayFragment;
import com.android.miniaccount.fragment.SummaryFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    private int fragmentFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //this.setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        viewPager = (ViewPager)this.findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView)this.findViewById(R.id.nav_menu);
        navigationView = (NavigationView)this.findViewById(R.id.nav_view);

        setupViewPage(viewPager);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gallery, R.id.nav_share, R.id.nav_send, R.id.nav_help, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mAppBarConfiguration.getFallbackOnNavigateUpListener();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_summary:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_income:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_outlay:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                        viewPager.setCurrentItem(0);
                        return false;
                }
                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()){
                    case R.id.nav_gallery:
                        intent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_share:
                        AccountApplication app = (AccountApplication)MainActivity.this.getApplication();
                        AccountDao dao = app.getDatabaseManager();
                        String str = String.format("收入汇总数据为%f, 支出汇总数据为%f",dao.getSumIncome(),dao.getSumOutlay());
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT,str);
                        startActivity(intent);
                        break;
                    case R.id.nav_send:
                        intent = new Intent(MainActivity.this, ReportActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_help:
                        break;
                    case R.id.nav_about:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        //dao.onCreate has been include in helper.getWritableDatabase(), so we dont need onCreate here!!!
        //AccountApplication app = (AccountApplication)this.getApplication();
        //AccountDao dao = app.getDatabaseManager();
        //dao.onDelete();
        //dao.onCreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setupViewPage(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        SummaryFragment summaryFragment = new SummaryFragment();
        IncomeFragment incomeFragment = new IncomeFragment();
        OutlayFragment outlayFragment = new OutlayFragment();

        adapter.addFragment(summaryFragment);
        adapter.addFragment(incomeFragment);
        adapter.addFragment(outlayFragment);

        viewPager.setAdapter(adapter);

        fragmentFlag = this.getIntent().getIntExtra("fragmentFlag",0);
        if(fragmentFlag == 1){
            //bottomNavigationView.setSelectedItemId(1);
            viewPager.setCurrentItem(1);
        }
        else if(fragmentFlag == 2){
            //bottomNavigationView.setSelectedItemId(2);
            viewPager.setCurrentItem(2);
        }else{
            //bottomNavigationView.setSelectedItemId(0);
            viewPager.setCurrentItem(0);
        }
    }

}
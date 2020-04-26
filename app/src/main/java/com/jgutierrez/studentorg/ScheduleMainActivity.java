package com.jgutierrez.studentorg;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
//import android.support.v4.view.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.jgutierrez.studentorg.adapters.MainActivityFragmentsAdapter;

public class ScheduleMainActivity extends BaseActivity {

    MainActivityFragmentsAdapter mainActivityFragmentsAdapter;
    ViewPager viewPager;
    String SAVE_INSTANCE_STATE_KEY = "MainActivityFragmentConfig";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity_main);
        mainActivityFragmentsAdapter = new MainActivityFragmentsAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.setAdapter(mainActivityFragmentsAdapter);
        if(savedInstanceState != null ) {
            position = savedInstanceState.getInt(SAVE_INSTANCE_STATE_KEY);
            viewPager.setCurrentItem(position);
        }
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        setTabIcons(tabLayout);
        ActivityCompat.requestPermissions(ScheduleMainActivity.this, new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if(savedInstanceState != null ) {
            int position = savedInstanceState.getInt(SAVE_INSTANCE_STATE_KEY);
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    int position;
    @Override
    protected void onPause() {
        super.onPause();
        position = viewPager.getCurrentItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(position);
    }

    public void setTabIcons(TabLayout tabLayout) {
        int icons[]= {
                R.drawable.ic_calendar,


        };
        for(int i = 0; i < icons.length; i++) {
          tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MAIN ACTIVITY", "Saving instance state");
        outState.putInt(SAVE_INSTANCE_STATE_KEY, viewPager.getCurrentItem());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}

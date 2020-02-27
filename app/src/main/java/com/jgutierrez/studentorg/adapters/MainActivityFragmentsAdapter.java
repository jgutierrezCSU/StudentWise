package com.jgutierrez.studentorg.adapters;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.jgutierrez.studentorg.fragments.ScheduleFragment;


public class MainActivityFragmentsAdapter extends FragmentStatePagerAdapter {
    int FRAGMENT_COUNT = 1;

    public MainActivityFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        Bundle args = new Bundle();
        switch (i) {
            case 0: fragment = ScheduleFragment.getInstance(); break;

            default: fragment = new ScheduleFragment(); break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String titles[] = {
                "SCHED"
        };
        return titles[position];
    }
}

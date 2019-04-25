package com.example.instituteapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.instituteapp.Fragments.LeaveAccepted;
import com.example.instituteapp.Fragments.LeaveReq;

public class pagerAdapter extends FragmentStatePagerAdapter {


    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new LeaveReq();
            case 1:
                return new LeaveAccepted();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Request";

            case 1:
                return "Approved";

            default:
                return null;
        }
    }
}

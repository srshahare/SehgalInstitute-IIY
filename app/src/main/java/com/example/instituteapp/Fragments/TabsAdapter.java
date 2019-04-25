package com.example.instituteapp.Fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {


    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ieltsFrag();
            case 1:
                return new pteFrag();
            case 2:
                return new celpipFrag();
            case 3:
                return new spokenFrag();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "IELTS";

            case 1:
                return "PTE";

            case 2:
                return "Celpip";

            case 3:
                return "Spoken English";

            default:
                return null;
        }
    }
}

package com.iiysoftware.instituteapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

public class ReadRequest extends AppCompatActivity {

    private  RecyclerView myRecyclerView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_request);
        final Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Requests");

        ViewPager viewPager = (ViewPager) findViewById(R.id.read_req_viewpager);
        pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.read_req_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


}

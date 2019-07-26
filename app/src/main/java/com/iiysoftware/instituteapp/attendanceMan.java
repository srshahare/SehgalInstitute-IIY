package com.iiysoftware.instituteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.iiysoftware.instituteapp.Fragments.TabsAdapter;

public class attendanceMan extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_man);
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Attendance");


        ViewPager pager = (ViewPager) findViewById(R.id.filter_pager);
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.filter_tabs);
        tabLayout.setupWithViewPager(pager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.att_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.marked_att_item:
                Intent lsi = new Intent(attendanceMan.this, allAttendenceList.class);
                startActivity(lsi);
                default:
                    return super.onOptionsItemSelected(item);

        }

    }
}

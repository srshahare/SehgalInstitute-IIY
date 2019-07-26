package com.iiysoftware.instituteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class fetchcsmadmin extends AppCompatActivity {
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchcsmadmin);
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);

        data=(TextView)findViewById(R.id.cmstext);
        fetchdatabackground fetchdataback=new fetchdatabackground();
        fetchdataback.execute();


    }

}

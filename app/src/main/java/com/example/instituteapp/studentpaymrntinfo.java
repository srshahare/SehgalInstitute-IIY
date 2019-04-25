package com.example.instituteapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class studentpaymrntinfo extends AppCompatActivity {
    public static TextView stupayinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentpaymrntinfo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        stupayinfo=(TextView)findViewById(R.id.stupayinfotext);
        studentpaymentinfoback studentpaymentinfobac=new studentpaymentinfoback();
        studentpaymentinfobac.execute();



    }

}

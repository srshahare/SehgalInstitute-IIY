package com.iiysoftware.instituteapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class studentpaymrntinfo extends AppCompatActivity {
    public static TextView stupayinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentpaymrntinfo);
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        stupayinfo=(TextView)findViewById(R.id.stupayinfotext);
        studentpaymentinfoback studentpaymentinfobac=new studentpaymentinfoback();
        studentpaymentinfobac.execute();



    }

}

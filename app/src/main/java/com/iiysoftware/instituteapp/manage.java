package com.iiysoftware.instituteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class manage extends AppCompatActivity implements View.OnClickListener {
    private static Button atten,uploadBtn,fetchNotes,requestbtn,paymentdetails,viewStudents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        atten=(Button)findViewById(R.id.attendance);
        atten.setOnClickListener(this);
        uploadBtn=(Button)findViewById(R.id.UploadNotes);
        uploadBtn.setOnClickListener(this);
        fetchNotes=(Button)findViewById(R.id.fetchNotes);
        fetchNotes.setOnClickListener(this);
        requestbtn=(Button)findViewById(R.id.requestsbtn);
        requestbtn.setOnClickListener(this);
        paymentdetails=(Button)findViewById(R.id.payment);
        paymentdetails.setOnClickListener(this);
        viewStudents=(Button)findViewById(R.id.allstudent);
        viewStudents.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.attendance:
                Intent i3 = new Intent(this, attendanceMan.class);
                startActivity(i3);
                break;
            case R.id.UploadNotes   :
                Intent i4 = new Intent(this, PremiumActivity.class);
                startActivity(i4);
                break;
            case R.id.fetchNotes:
                startActivity(new Intent(getApplicationContext(),fetchNotes.class));
                break;
            case R.id.requestsbtn:
                startActivity(new Intent(getApplicationContext(),ReadRequest.class));
                break;
            case R.id.payment:
                //startActivity(new Intent(getApplicationContext(),fetchcsmadmin.class));
                startActivity(new Intent(getApplicationContext(),payment.class));
                break;
            case R.id.allstudent:
                startActivity(new Intent(getApplicationContext(),fetchallStudents.class));
                break;
        }
    }
}

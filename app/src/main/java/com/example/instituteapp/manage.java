package com.example.instituteapp;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class manage extends AppCompatActivity implements View.OnClickListener {
    private static Button add,delete,atten,uploadBtn,fetchNotes,requestbtn,paymentdetails,viewStudents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        add=(Button)findViewById(R.id.addStudent);
        delete=(Button)findViewById(R.id.deleteStudent);
        atten=(Button)findViewById(R.id.attendance);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
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
            case R.id.addStudent:
                Intent i = new Intent(this, addStudent.class);
                startActivity(i);
                break;
            case R.id.deleteStudent:
                Intent i1 = new Intent(this, deleteStudent.class);
                startActivity(i1);
                break;
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

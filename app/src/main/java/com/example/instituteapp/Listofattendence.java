package com.example.instituteapp;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Listofattendence extends AppCompatActivity {

    private FirebaseFirestore db;
    private static  String url,filename;
    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listofattendence);

        TextView ilts = findViewById(R.id.ielts_btn);
        TextView pte = findViewById(R.id.pte_btn);
        TextView celpip = findViewById(R.id.celpip_btn);
        TextView spoken = findViewById(R.id.spoken_btn);
        db= FirebaseFirestore.getInstance();

        ilts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(Listofattendence.this, AllStudentAtt.class);
                listIntent.putExtra("id", "ilts");
                startActivity(listIntent);

            }
        });

        pte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(Listofattendence.this, AllStudentAtt.class);
                listIntent.putExtra("id", "pte");
                startActivity(listIntent);

            }
        });
        celpip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(Listofattendence.this, AllStudentAtt.class);
                listIntent.putExtra("id", "celpip");
                startActivity(listIntent);

            }
        });

        spoken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(Listofattendence.this, AllStudentAtt.class);
                listIntent.putExtra("id", "spoken");
                startActivity(listIntent);

            }
        });



    }
}

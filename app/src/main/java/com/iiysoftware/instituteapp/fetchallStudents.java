package com.iiysoftware.instituteapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class fetchallStudents extends AppCompatActivity {
    //public static TextView allstudents;

    private FirebaseFirestore db;
    private static  String url,filename;
    private RecyclerView myRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetchall_students);
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Students");
        //allstudents=(TextView)findViewById(R.id.allstudenttext);
        fetchallStudentBack fetchallstudentback=new fetchallStudentBack();
        fetchallstudentback.execute();

        db= FirebaseFirestore.getInstance();
        myRecyclerView=(RecyclerView)findViewById(R.id.all_students_list);


        db.collection("Students").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fetch",document.getId() + " => " + document.getData());
                                filename=document.getData().get("Id").toString();
                                String name =document.getData().get("Name").toString();
                                String course =document.getData().get("Course").toString();

                                ((fetchStudentsAdapter)myRecyclerView.getAdapter()).update(filename, name, course);

                            }
                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchStudentsAdapter adapter=new fetchStudentsAdapter(myRecyclerView,getApplicationContext(),new ArrayList<String>()
                , new ArrayList<String>(), new ArrayList<String>());
        myRecyclerView.setAdapter(adapter);

    }
}

package com.example.instituteapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class allAttendenceList extends AppCompatActivity {

    private  FirebaseFirestore db;
    private static  String url,filename;
    private  RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_attendence_list);

        db= FirebaseFirestore.getInstance();
        myRecyclerView=(RecyclerView)findViewById(R.id.attend_list);


        db.collection("Attendance").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fetch",document.getId() + " => " + document.getData());
                                filename=document.getData().get("date").toString();
                                ((attFetchAdapter)myRecyclerView.getAdapter()).update(filename);

                            }
                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        attFetchAdapter adapter=new attFetchAdapter(myRecyclerView,getApplicationContext(),new ArrayList<String>());
        myRecyclerView.setAdapter(adapter);

    }
}

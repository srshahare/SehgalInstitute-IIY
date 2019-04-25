package com.example.instituteapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class fetchNotes extends AppCompatActivity {
private  FirebaseFirestore db;
private static  String url,filename;
private  RecyclerView myRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db= FirebaseFirestore.getInstance();
        myRecyclerView=(RecyclerView)findViewById(R.id.fetchRecyclerView);


        db.collection("Premium")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fetch",document.getId() + " => " + document.getData());
                                url=document.getData().get("Url").toString();
                                filename=document.getData().get("FileName").toString();
                                ((fetchAdapter)myRecyclerView.getAdapter()).update(filename,url);

                            }
                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchAdapter fetchadapter=new fetchAdapter(myRecyclerView,getApplicationContext(),new ArrayList<String>(),new ArrayList<String>());
        myRecyclerView.setAdapter(fetchadapter);

    }

}

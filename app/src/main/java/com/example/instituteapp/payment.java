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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class payment extends AppCompatActivity {

    private FirebaseFirestore db;
    private static  String url,filename;
    private RecyclerView myRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db= FirebaseFirestore.getInstance();
        myRecyclerView=(RecyclerView)findViewById(R.id.payment_list);


        db.collection("Payments").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fetch",document.getId() + " => " + document.getData());
                                filename=document.getData().get("Id").toString();
                                String name =document.getData().get("Name").toString();
                                String amount =document.getData().get("Total Amount").toString();

                                ((fetchPayments)myRecyclerView.getAdapter()).update(filename, name, amount);

                            }
                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchPayments adapter=new fetchPayments(myRecyclerView,getApplicationContext(),new ArrayList<String>()
        , new ArrayList<String>(), new ArrayList<String>());
        myRecyclerView.setAdapter(adapter);

    }

}

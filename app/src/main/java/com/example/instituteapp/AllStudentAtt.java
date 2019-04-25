package com.example.instituteapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllStudentAtt extends AppCompatActivity {

    private FirebaseFirestore db;
    private static  String url,filename;
    private RecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student_att);

        final String intent_id = getIntent().getStringExtra("id");

        db= FirebaseFirestore.getInstance();
        myRecyclerView=(RecyclerView)findViewById(R.id.attendence_list);

        db.collection("Attendance").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fetch",document.getId() + " => " + document.getData());
                                filename=document.getData().get("date").toString();
                                if (intent_id.equals("ilts")){
                                    db.collection("Attendance").document(filename)
                                            .collection("IELTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String name = document.getData().toString();

                                                ((studentAttfetchAdapter)myRecyclerView.getAdapter()).update(name);

                                            }

                                        }
                                    });

                                }else if (intent_id.equals("pte")){
                                    db.collection("Attendance").document(filename)
                                            .collection("PTE").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String name = document.getData().toString();

                                                ((studentAttfetchAdapter)myRecyclerView.getAdapter()).update(name);

                                            }

                                        }
                                    });
                                }else if (intent_id.equals("celpip")){
                                    db.collection("Attendance").document(filename)
                                            .collection("Celpip").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String name = document.getData().toString();

                                                ((studentAttfetchAdapter)myRecyclerView.getAdapter()).update(name);

                                            }

                                        }
                                    });
                                }else if (intent_id.equals("spoken")){
                                    db.collection("Attendance").document(filename)
                                            .collection("Spoken English").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String name = document.getData().toString();

                                                ((studentAttfetchAdapter)myRecyclerView.getAdapter()).update(name);

                                            }

                                        }
                                    });
                                }


                            }
                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAttfetchAdapter adapter=new studentAttfetchAdapter(myRecyclerView,getApplicationContext(),new ArrayList<String>());
        myRecyclerView.setAdapter(adapter);

    }
}

package com.example.instituteapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.instituteapp.R;
import com.example.instituteapp.mainlayout;
import com.example.instituteapp.myAttendance;
import com.example.instituteapp.recyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class pteFrag extends Fragment implements View.OnClickListener{


    public pteFrag() {
        // Required empty public constructor
    }
    private static ArrayList<myAttendance> myAtten;
    private static RecyclerView recyclerView;
    private static RecyclerView.LayoutManager layoutManager;
    private static recyclerAdapter adapter;
    private static String TAG="Firebase";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Button save;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pte, container, false);

        save=(Button)view.findViewById(R.id.save_pte_att_btn);
        save.setOnClickListener(this);

        recyclerView=(RecyclerView)view.findViewById(R.id.pte_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAtten = new ArrayList<>();
        db.collection("Students").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String sub = document.getData().get("Course").toString();
                                if (sub.equals("PTE")){
                                    myAttendance m=new myAttendance(

                                            document.getString("Name")
                                    );

                                    myAtten.add(m);
                                    Log.d(TAG,m.getEmail());
                                }

                            }
                            adapter=new recyclerAdapter(myAtten,getContext());
                            recyclerView.setAdapter(adapter);

                            for(myAttendance m:myAtten){
                                Log.d("myop",m.getEmail());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        return view;
    }

    @Override
    public void onClick(View v) {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("date", formattedDate);

        db.collection("Attendance").document(formattedDate).set(hashMap).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        db.collection("Attendance").document(formattedDate)
                                .collection("PTE").document("List")
                                .set(adapter.Attendancedb)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        Toast.makeText(getContext(),"Saved",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getContext(), mainlayout.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });

                    }
                }
        );



    }

}

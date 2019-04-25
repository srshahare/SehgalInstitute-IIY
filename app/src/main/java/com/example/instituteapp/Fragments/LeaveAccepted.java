package com.example.instituteapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instituteapp.R;
import com.example.instituteapp.readAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaveAccepted extends Fragment {


    public LeaveAccepted() {
        // Required empty public constructor
    }
    private FirebaseFirestore db;
    private static  String url,filename, from1, to1;
    private RecyclerView myRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave_accepted, container, false);


        myRecyclerView = (RecyclerView) view.findViewById(R.id.accepted_list);

        db= FirebaseFirestore.getInstance();

        db.collection("accepted_req")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fetch",document.getId() + " => " + document.getData());
                                url=document.getData().get("name").toString();
                                filename=document.getData().get("reason").toString();
                                from1 = document.getData().get("from").toString();
                                to1 = document.getData().get("to").toString();
                                ((ReqAcceptedAdapter)myRecyclerView.getAdapter()).update(filename,url, from1, to1);

                            }
                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ReqAcceptedAdapter readadapter=new ReqAcceptedAdapter(myRecyclerView,getContext(),new ArrayList<String>(),new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<String>());
        myRecyclerView.setAdapter(readadapter);


        return view;
    }

}

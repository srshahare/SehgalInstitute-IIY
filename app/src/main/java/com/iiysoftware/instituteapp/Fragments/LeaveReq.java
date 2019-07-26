package com.iiysoftware.instituteapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iiysoftware.instituteapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaveReq extends Fragment {


    public LeaveReq() {
        // Required empty public constructor
    }

    private RecyclerView myRecyclerView;
    private FirebaseFirestore db;
    private allRequestAdapter readAdapter;
    private LinearLayoutManager layoutManager;
    private static  String url,filename, from1, to1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave_req, container, false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.read_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);

        db= FirebaseFirestore.getInstance();

       /* db.collection("requests")
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
                                ((readAdapter)myRecyclerView.getAdapter()).update(filename,url, from1, to1);

                            }
                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }
                });
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readAdapter fetchadapter=new readAdapter(myRecyclerView,getContext(),new ArrayList<String>(),new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<String>());
        myRecyclerView.setAdapter(fetchadapter);*/

        Query query = db.collection("requests");
        FirestoreRecyclerOptions<requests> options = new FirestoreRecyclerOptions.Builder<requests>()
                .setQuery(query, requests.class)
                .build();

        readAdapter = new allRequestAdapter(getContext(), options);
        myRecyclerView.setAdapter(readAdapter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        readAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        readAdapter.stopListening();
    }
}

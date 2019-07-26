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
public class LeaveAccepted extends Fragment {


    public LeaveAccepted() {
        // Required empty public constructor
    }
    private FirebaseFirestore db;
    private static  String url,filename, from1, to1;
    private RecyclerView myRecyclerView;
    private LinearLayoutManager layoutManager;
    private allApprovedReq adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leave_accepted, container, false);


        myRecyclerView = (RecyclerView) view.findViewById(R.id.accepted_list);
        layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);

        db= FirebaseFirestore.getInstance();

        /*db.collection("accepted_req").document("MainData")
                .collection("Names")
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
        myRecyclerView.setAdapter(readadapter);*/

        Query query = db.collection("accepted_req").document("MainData").collection("Names");
        FirestoreRecyclerOptions<requests> options = new FirestoreRecyclerOptions.Builder<requests>()
                .setQuery(query, requests.class)
                .build();

        adapter = new allApprovedReq(getContext(), options);
        myRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

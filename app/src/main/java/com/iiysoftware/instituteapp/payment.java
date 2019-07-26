package com.iiysoftware.instituteapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class payment extends AppCompatActivity {

    private FirebaseFirestore db;
    private static String url, filename, name, amount;
    private RecyclerView myRecyclerView;
    private WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        db = FirebaseFirestore.getInstance();
        myRecyclerView = (RecyclerView) findViewById(R.id.payment_list);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final TextView amountShow = findViewById(R.id.amount_text);

        db.collection("Payments").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fetch", document.getId() + " => " + document.getData());

                                filename = document.getData().get("Id").toString();
                                name = document.getData().get("Name").toString();
                                amount = document.getData().get("Pending Amount").toString();
                                String Uid = document.getData().get("Uid").toString();

                                if (mAuth.getUid().equals("IHGjkEA8X0faYg61FE7lCzcQqL12")) {
                                    ((fetchPayments) myRecyclerView.getAdapter()).update(filename, name, amount);

                                } else {
                                    amountShow.setVisibility(View.VISIBLE);
                                    if (mAuth.getCurrentUser().getUid().equals(Uid)) {

                                        String my_amount = document.getData().get("Pending Amount").toString();
                                        amountShow.setText("Your Pending Amount is:\n" + my_amount + "Rs");

                                    }
                                }
                            }

                        } else {
                            Log.d("fetch", "Error getting documents: ", task.getException());
                        }
                    }

                });

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchPayments adapter = new fetchPayments(myRecyclerView, getApplicationContext(), new ArrayList<String>()
                , new ArrayList<String>(), new ArrayList<String>());
        myRecyclerView.setAdapter(adapter);

    }


}

package com.example.instituteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class deleteStudent extends AppCompatActivity implements View.OnClickListener {
private static Button b1;
private static EditText e1;
private static FirebaseFirestore db=FirebaseFirestore.getInstance();
private static String TAG="firebase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        b1=(Button)findViewById(R.id.buttondelete);
        b1.setOnClickListener(this);
        e1=(EditText)findViewById(R.id.namedelete);

    }

    @Override
    public void onClick(View v) {
        String name=e1.getText().toString();
        final Query docRef = db.collection("Students").whereEqualTo("Name",name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            db.collection("Students").document(document.getId())
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                            Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_SHORT);
                                            Intent i=new Intent(getApplicationContext(),manage.class);
                                            startActivity(i);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error deleting document", e);
                                            Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_SHORT);
                                        }
                                    });


                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    Toast.makeText(getApplicationContext(),"Record Not Found",Toast.LENGTH_SHORT);
                    }
                }

        });




    }
}

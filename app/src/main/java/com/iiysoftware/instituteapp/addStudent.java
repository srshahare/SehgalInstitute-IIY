package com.iiysoftware.instituteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addStudent extends AppCompatActivity implements View.OnClickListener {
private static EditText e1,e2,e3,e4,e5,e6;
private static Button b1;
private static FirebaseFirestore db;
private FirebaseAuth mAuth1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();
        e1=(EditText)findViewById(R.id.name);
        e2=(EditText)findViewById(R.id.stdclass);
        e3=(EditText)findViewById(R.id.email1);
        e4=(EditText)findViewById(R.id.id);
        e5=(EditText)findViewById(R.id.password);
        e6=(EditText)findViewById(R.id.mobno);
        b1=(Button)findViewById(R.id.save);
        b1.setOnClickListener(this);
        mAuth1 = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        String name=e1.getText().toString();
        String stdclass = e2.getText().toString();
       final String email1=e3.getText().toString();
        String id=e4.getText().toString();
       final String pass=e5.getText().toString();
        String mob=e6.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("Name",name);
        user.put("Class", stdclass);
        user.put("Email", email1);
        user.put("Id", id);
        user.put("Password", pass);
        user.put("Mobile", mob);


        db.collection("Students")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot added with ID: " + documentReference.getId());

                        mAuth1.createUserWithEmailAndPassword(email1, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getApplicationContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),manage.class);
                                startActivity(i);
                                mAuth1.signOut();

                            }
                        });



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error", "Error adding document", e);
                    }
                });
    }
}

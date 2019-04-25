package com.example.instituteapp;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class request extends AppCompatActivity {
private static EditText nameR,reason, from, to;
private static Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameR=(EditText)findViewById(R.id.nameR);
        reason=(EditText)findViewById(R.id.reason);
        from = (EditText) findViewById(R.id.from_edittext);
        to = (EditText) findViewById(R.id.to_edittext);
        send=(Button)findViewById(R.id.send);

        //--------choose date from-to------------------

        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,rea, from_date, to_date;
                name=nameR.getText().toString();
                rea=reason.getText().toString();
                from_date = from.getText().toString();
                to_date = to.getText().toString();

                if (name!=null && rea!=null && from!=null && to!=null )
                {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("name", name);
                    obj.put("reason", rea);
                    obj.put("from", from_date);
                    obj.put("to", to_date);
                    db.collection("requests").document(name)
                            .set(obj)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("request", "DocumentSnapshot successfully written!");
                                    Toast.makeText(getApplicationContext(),"Request Send",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Request", "Error writing document", e);
                                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Fill all the details",Toast.LENGTH_LONG).show();
                }


            }
        });


    }

}

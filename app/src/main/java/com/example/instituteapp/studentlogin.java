package com.example.instituteapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class studentlogin extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private RelativeLayout r1;
    private EditText e1, e2;
    private static String TAG = "error msg", email, password;
    public static boolean bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        r1 = (RelativeLayout) findViewById(R.id.loginBtn);
        r1.setOnClickListener((View.OnClickListener) this);
        e1 = (EditText) findViewById(R.id.username);
        e2 = (EditText) findViewById(R.id.password);

    }
    @Override
    public void onClick(View v) {
        email = e1.getText().toString();
        password = e2.getText().toString();
        // Log.d("hello",email+"/"+password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid=user.getUid();
                            Intent i=new Intent(getApplicationContext(),mainlayout.class);
                            startActivity(i);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(studentlogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }
   /* public void update(boolean t)
    {
        NavigationView navigationView= findViewById(R.id.nav_view);

        Menu menuNav=navigationView.getMenu();
        MenuItem navManage,navPremium,navRequest,navPayment;
        navManage= menuNav.findItem(R.id.nav_manage);
        navPayment=menuNav.findItem(R.id.nav_payment);
        navRequest=menuNav.findItem(R.id.nav_request);
        navPremium=menuNav.findItem(R.id.nav_premium);
        // navManage.setEnabled(false);

       if(t)
       {
           navManage.setVisible(true);
           navPayment.setVisible(false);
           navRequest.setVisible(false);
           navPremium.setVisible(false);
       }
    }*/
}
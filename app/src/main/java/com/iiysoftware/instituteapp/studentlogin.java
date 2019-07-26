package com.iiysoftware.instituteapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class studentlogin extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private RelativeLayout r1;
    private EditText e1, e2;
    private static String TAG = "error msg", email, password;
    public static boolean bool;
    ProgressDialog dialog;

    String data="";
    String dataparsed="",singleparsed="";
    public static String URL = "https://service.softsmart.in/Demo/XYZ/index.php/Staff_controller/checkValidity?email=jatin@gmail.com&password=jatin&type=student";

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

       // fetchallStudentBack fetchallstudentback=new fetchallStudentBack();
       // fetchallstudentback.execute();

        dialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        email = e1.getText().toString();
        password = e2.getText().toString();
        dialog.setMessage("Logging");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        // Log.d("hello",email+"/"+password);

        db.collection("Students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String d_email = document.getData().get("Email").toString();
                        String d_pass = document.getData().get("Password").toString();
                        final String d_name = document.getData().get("Name").toString();
                        if (d_email.equals(email) && d_pass.equals(password)){
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    final HashMap<String, Object> map = new HashMap<>();
                                    map.put("Uid", mAuth.getCurrentUser().getUid());
                                    db.collection("Students").document(d_name).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            db.collection("Payments").document(d_name).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(studentlogin.this, "synced", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });

                                }
                            });
                        }
                    }
                }
            }
        });


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            Intent i = new Intent(getApplicationContext(), mainlayout.class);
                            startActivity(i);
                        } else {
                            dialog.hide();
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
    @Override
    protected void onStop() {
        super.onStop();
    }

}
package com.example.instituteapp;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class gallerylayout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private WebView wb;
    private ClipData.Item item1;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    MenuItem navManage, navPremium, navRequest, navPayment, navlogout, navlogin, contactus;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerylayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        NavigationView navigationView = findViewById(R.id.nav_view);

        Menu menuNav = navigationView.getMenu();

        navManage = menuNav.findItem(R.id.nav_manage);
        navPayment = menuNav.findItem(R.id.nav_payment);
        navRequest = menuNav.findItem(R.id.nav_request);
        navPremium = menuNav.findItem(R.id.nav_premium);
        navlogout = menuNav.findItem(R.id.nav_logout);
        navlogin = menuNav.findItem(R.id.nav_login);
        contactus = menuNav.findItem(R.id.nav_contact);
        // navManage.setEnabled(false);
        diasble();


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_login:
                Intent i = new Intent(this, studentlogin.class);
                startActivity(i);
                break;
            case R.id.nav_manage:
                Intent i1 = new Intent(this, manage.class);
                startActivity(i1);
                break;
            case R.id.nav_payment:
                Intent i2 = new Intent(this, studentpaymrntinfo.class);
                startActivity(i2);
                break;
            case R.id.nav_premium:
                Intent i3 = new Intent(this, fetchNotes.class);
                startActivity(i3);
                break;
            case R.id.nav_request:
                startActivity(new Intent(this, request.class));
                break;
           /* case R.id.nav_logout:
                mAuth.signOut();
                diasble();
                break;*/
            case R.id.nav_about:
                Intent i4 = new Intent(this, aboutusact.class);
                startActivity(i4);
                break;
            case R.id.nav_ielts:
                Intent i5 = new Intent(this, ieltsact.class);
                startActivity(i5);
                break;
            case R.id.nav_cdielts:
                Intent i6 = new Intent(this, cdielts.class);
                startActivity(i6);
                break;
            case R.id.nav_pte:
                Intent i7 = new Intent(this, pteact.class);
                startActivity(i7);
                break;
            case R.id.nav_gallery:
                Intent intent8 = new Intent(this, gallerylayout.class);
                startActivity(intent8);
                break;
            case R.id.nav_contact:
                Intent intent9=new Intent(this,contactus.class);
                startActivity(intent9);
                break;
            case R.id.spkn_eng:
                Intent intent10 = new Intent(this,spknenglish.class);
                startActivity(intent10);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        //update(currentUser);
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DocumentReference docRef = db.collection("admin").document(uid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("login", "DocumentSnapshot data: " + document.getData());
                            update(true);

                        } else {
                            Log.d("login", "No such document");
                            update(false);
                        }

                    } else {
                        Log.d("login", "get failed with ", task.getException());
                    }
                }
            });
        }


    }

    public void diasble() {
        navlogin.setVisible(true);
        navManage.setVisible(false);
        navPayment.setVisible(false);
        navRequest.setVisible(false);
        navPremium.setVisible(false);
        navlogout.setVisible(false);
    }

    public void update(boolean b) {
        navlogin.setVisible(false);
        navlogout.setVisible(true);
        if (b) {
            navManage.setVisible(true);
            navPayment.setVisible(true);
            navRequest.setVisible(true);
            navPremium.setVisible(true);
        } else {
            navManage.setVisible(false);
            navPayment.setVisible(true);
            navRequest.setVisible(true);
            navPremium.setVisible(true);
        }
    }
}

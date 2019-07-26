package com.iiysoftware.instituteapp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class studentpaymentinfoback extends AsyncTask<Void,Void,Void> {
    String data2="",singleparsed2="",dataparsed2="",id;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private static String Id;
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        studentpaymrntinfo.stupayinfo.setText(this.dataparsed2);
    }



    @Override
    protected Void doInBackground(Void... voids) {

        try {

            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            currentUser = mAuth.getCurrentUser();
            //update(currentUser);
            if (currentUser != null) {
                String email = currentUser.getEmail();
                db.collection("Students")
                        .whereEqualTo("Email", email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("studentpayment", document.getId() + " => " + document.getData().get("Id"));
                                        Id=document.getData().get("Id").toString();

                                    }
                                } else {
                                    Log.d("studentpayment", "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
            Thread.sleep(5000);
            URL url=new URL("https://service.softsmart.in/Demo/XYZ/index.php/Client_controller/getPendingPayments");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line ="";

            while (line!=null)
            {
                line=bufferedReader.readLine();
                data2=data2+line;
            }
            JSONArray jsonArray =new JSONArray(data2);
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=(JSONObject) jsonArray.get(i);
                id = jsonObject.get("id").toString();
                singleparsed2 = "Id:-" + jsonObject.get("id") + "\n" +
                        "Name:-" + jsonObject.get("name") + "\n" +
                        "Total Amount:" + jsonObject.get("total_amount") + "\n" +
                        "Paid Amount:-:" + jsonObject.get("paid_amount") + "\n" +
                        "Pending Amount:-" + jsonObject.get("pending_amount") + "\n";
                Log.d("redmierror","in");
                if (id.equals(Id))
                {   Log.d("redmierror","if");
                    dataparsed2=singleparsed2;break;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
    private void getdata()
    {

    }

}


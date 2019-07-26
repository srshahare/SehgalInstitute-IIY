package com.iiysoftware.instituteapp;

import android.os.AsyncTask;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class fetchdatabackground extends AsyncTask<Void,Void,Void>
{   String data="";
    String dataparsed="",singleparsed="";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //fetchcsmadmin.data.setText(this.dataparsed);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL("https://service.softsmart.in/Demo/XYZ/index.php/Client_controller/getPendingPayments");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while (line!=null)
            {
                line=bufferedReader.readLine();
                data=data+line;
            }
            JSONArray jsonArray =new JSONArray(data);
            for (int i=0;i<jsonArray.length();i++)
            {
                final JSONObject jsonObject=(JSONObject) jsonArray.get(i);
                singleparsed="Id:-"+jsonObject.get("id")+"\n"+
                        "Name:-"+jsonObject.get("name")+"\n"+
                        "Total Amount:"+jsonObject.get("total_amount")+"\n"+
                        "Paid Amount:-:"+jsonObject.get("paid_amount")+"\n"+
                        "Pending Amount:-"+jsonObject.get("pending_amount")+"\n";
                dataparsed=dataparsed+singleparsed+"\n";

                String name = jsonObject.get("name").toString();

                final FirebaseFirestore db=FirebaseFirestore.getInstance();
                Map<String, Object> obj = new HashMap<>();
                obj.put("Id", jsonObject.get("id"));
                obj.put("Name", jsonObject.get("name"));
                obj.put("Total Amount", jsonObject.get("total_amount"));
                obj.put("Paid Amount", jsonObject.get("paid_amount"));
                obj.put("Pending Amount", jsonObject.get("pending_amount"));
                obj.put("Uid", "No Information");

                db.collection("Payments").document(name)
                        .set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

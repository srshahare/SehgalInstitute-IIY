package com.example.instituteapp;

import android.os.AsyncTask;
import android.widget.TextView;

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

public class fetchallStudentBack extends AsyncTask<Void,Void,Void> {
    String data1="",singleparsed1="",dataparsed1="";


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //fetchallStudents.allstudents.setText(dataparsed1);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL("https://service.softsmart.in/Demo/XYZ/index.php/Client_controller/getAllAdmissions");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while (line!=null)
            {
                line=bufferedReader.readLine();
                data1=data1+line;
            }
            JSONArray jsonArray =new JSONArray(data1);
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject=(JSONObject) jsonArray.get(i);
                singleparsed1="Id:-"+jsonObject.get("id")+"\n"+
                        "Name:-"+jsonObject.get("name")+"\n"+
                        "Phone No:-:"+jsonObject.get("phone_number")+"\n"+
                        "City:-:"+jsonObject.get("city")+"\n"+
                        "Course:-"+jsonObject.get("course")+"\n"+
                        "Code:-"+jsonObject.get("code")+"\n";
                dataparsed1=dataparsed1+singleparsed1+"\n";

                String value = jsonObject.get("name").toString();

                //-------------firebase feature------------
                final FirebaseFirestore db=FirebaseFirestore.getInstance();
                Map<String, Object> obj = new HashMap<>();
                obj.put("Id", jsonObject.get("id"));
                obj.put("Name", jsonObject.get("name"));
                obj.put("Phone No", jsonObject.get("phone_number"));
                obj.put("City", jsonObject.get("city"));
                obj.put("Course", jsonObject.get("course"));
                obj.put("Code", jsonObject.get("code"));

                db.collection("Students").document(value).set(obj)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
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

package com.example.instituteapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.app.ProgressDialog.STYLE_HORIZONTAL;

public class PremiumActivity extends AppCompatActivity {
    final static int PICK_PDF_CODE = 2342;
    private static Button upload,select;
    private static TextView notification;
    Uri pdfUrl;
    String myUrl;
    private static ProgressDialog progress;

    StorageReference mStorageReference;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();


        upload=(Button)findViewById(R.id.UploadFile);
        select=(Button)findViewById(R.id.SelectFile);
        notification=(TextView)findViewById(R.id.notification);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(PremiumActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))== PackageManager.PERMISSION_GRANTED)
                {
                    selectpdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(PremiumActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUrl!=null)
                    uploadFile(pdfUrl);
                else
                    Toast.makeText(getApplicationContext(),"Select the File",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void uploadFile(Uri url)
    {
        progress=new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setTitle("Uploading Data...");
        progress.setProgress(0);
        progress.show();

        final String filename=System.currentTimeMillis()+"";
        mStorageReference= FirebaseStorage.getInstance().getReference();
        final StorageReference mSR=mStorageReference.child("Premium").child(filename);


       // UploadTask uploadTask = mSR.putFile(url);
        mSR.putFile(url)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        mSR.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri uri) {
                                Map<String, Object> mapUrl = new HashMap<>();
                                final String downloadUrl = uri.toString();
                                mapUrl.put("FileName",filename);
                                mapUrl.put("Url",downloadUrl);
                                db.collection("Premium").document(filename)
                                        .set(mapUrl)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Upload", "DocumentSnapshot successfully written!");
                                                Toast.makeText(getApplicationContext(),"File Successfully Uploaded",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Upload", "Error writing document", e);
                                                Toast.makeText(getApplicationContext(),"File Not Uploaded",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });

                        //Uri downlodUrl= URL;
                       // String x=mSR.getDownloadUrl().toString();
                       // Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        //String y=u.toString();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"File Not Uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    int currentProgress=(int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progress.setProgress(currentProgress);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectpdf();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Plese Provide Permission",Toast.LENGTH_SHORT).show();
        }
    }
    public void selectpdf()
    {
        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};
        Intent intent=new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        //intent.setType("file/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==86 && resultCode==RESULT_OK && data!=null)
        {
            pdfUrl=data.getData();
            //notification.setText(pdfUrl.getLastPathSegment());
            notification.setText(pdfUrl.getPath());
            File file = new File(getPackageName());

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Plese Select File",Toast.LENGTH_SHORT).show();
        }
    }
}

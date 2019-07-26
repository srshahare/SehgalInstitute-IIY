package com.iiysoftware.instituteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class fullimageactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimageactivity);

        Intent i = getIntent();

        int position = i.getExtras().getInt("id");
        imageAdapter adapter = new imageAdapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.imageWiew);
        imageView.setImageResource(adapter.images[position]);
    }
}

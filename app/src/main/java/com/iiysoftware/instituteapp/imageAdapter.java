package com.iiysoftware.instituteapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class imageAdapter extends BaseAdapter {
    private Context context;

    public Integer[] images = {
        R.drawable.file1,R.drawable.file2,
            R.drawable.file3,R.drawable.file4,
            R.drawable.file5,R.drawable.file6,
            R.drawable.file7,R.drawable.file8,
            R.drawable.file9,R.drawable.file10,
            R.drawable.file11,R.drawable.file12,
            R.drawable.file13,R.drawable.file14,
            R.drawable.file15,R.drawable.file16,
            R.drawable.file17,R.drawable.file18,
            R.drawable.file19,R.drawable.file20,
            R.drawable.file21,R.drawable.file22,
            R.drawable.file23,R.drawable.file24,
            R.drawable.file25,R.drawable.file26,
            R.drawable.file27,R.drawable.file28,
            R.drawable.file29,R.drawable.file30,
            R.drawable.file31,R.drawable.file32,
            R.drawable.file33,R.drawable.file34,
            R.drawable.file35,R.drawable.file36,
            R.drawable.file37,R.drawable.file38,
            R.drawable.file39
    };
    public imageAdapter (Context c){
        context = c;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new GridView.LayoutParams(240,240));
        return imageView;
    }
}

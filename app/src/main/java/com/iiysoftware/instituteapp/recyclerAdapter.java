package com.iiysoftware.instituteapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder>{
    private List<myAttendance> myAttend;
    private Context context;
    public static Map<String, Object> Attendancedb = new HashMap<>();

    public recyclerAdapter(List<myAttendance> myAttend, Context context) {

        this.myAttend = myAttend;
        this.context = context;

    }


    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.textview_layout,viewGroup,false);
        Log.d("inrecadapter","inflated");

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        myAttendance myhistory=myAttend.get(position);

        holder.email.setText("Name"+":"+myhistory.getEmail());
        holder.id.setText("Id"+":"+ myhistory.getId() + "/");
        final myAttendance suggestion = myAttend.get(position);
        final String name=myhistory.getEmail();
        String uid = myhistory.getId();
        Attendancedb.put(name,"A");
        holder.mCheckBox.setOnCheckedChangeListener(null);
        holder.mCheckBox.setSelected(suggestion.isSelected());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    suggestion.setSelected(true);
                    Attendancedb.put(name,"P");
                        Log.d("morshi",name);

                }else {

                    suggestion.setSelected(false);

                }
            }
        });
        holder.mCheckBox.setChecked(suggestion.isSelected());

    }



    @Override
    public int getItemCount() {
        return myAttend.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView email, id;
        public CheckBox mCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            email=(TextView)itemView.findViewById(R.id.email);
            id = itemView.findViewById(R.id.id);
            mCheckBox = (CheckBox)itemView.findViewById(R.id.checkBox);



        }
    }
}

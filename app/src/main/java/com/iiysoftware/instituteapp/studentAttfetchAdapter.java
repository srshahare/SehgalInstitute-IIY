package com.iiysoftware.instituteapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class studentAttfetchAdapter extends RecyclerView.Adapter<studentAttfetchAdapter.ViewAdapter>
{  private RecyclerView recyclerView;
   private Context context;
    private ArrayList<String> items;
    private FirebaseFirestore db;


    public studentAttfetchAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items)
    {
        this.context=context;
        this.items=items;
        this.recyclerView=recyclerView;
    }


    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendence_list_item,parent,false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAdapter holder, int position) {
        holder.nameofFile.setText(items.get(position));
        db= FirebaseFirestore.getInstance();
    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    public void update(String filename)
    {
        items.add(filename);
        notifyDataSetChanged();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        TextView nameofFile;


        public ViewAdapter(final View itemView) {
            super(itemView);
            nameofFile=itemView.findViewById(R.id.att_item);
        }
    }
}

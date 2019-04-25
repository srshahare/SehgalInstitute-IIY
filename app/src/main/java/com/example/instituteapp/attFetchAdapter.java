package com.example.instituteapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class attFetchAdapter extends RecyclerView.Adapter<attFetchAdapter.ViewAdapter>
{  private RecyclerView recyclerView;
   private Context context;
    private ArrayList<String> items;
    private FirebaseFirestore db;


    public attFetchAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items)
    {
        this.context=context;
        this.items=items;
        this.recyclerView=recyclerView;
    }


    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fetchitems,parent,false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAdapter holder, int position) {
        holder.nameofFile.setText(items.get(position));
        db= FirebaseFirestore.getInstance();
        holder.delete.setVisibility(View.GONE);
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
        ImageView delete;


        public ViewAdapter(final View itemView) {
            super(itemView);
            nameofFile=itemView.findViewById(R.id.nameofFiles);
            delete = itemView.findViewById(R.id.delete_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, Listofattendence.class);
                    context.startActivity(i);

                }
            });
        }
    }
}

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

public class fetchAdapter extends RecyclerView.Adapter<fetchAdapter.ViewAdapter>
{  private RecyclerView recyclerView;
   private Context context;
    private ArrayList<String> items;
   private ArrayList<String> urls;
    private FirebaseFirestore db;


    public fetchAdapter(RecyclerView recyclerView,Context context,ArrayList<String> items,ArrayList<String> urls)
    {
        this.context=context;
        this.items=items;
        this.recyclerView=recyclerView;
        this.urls=urls;
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = holder.nameofFile.getText().toString();
                final Query docRef = db.collection("Premium").whereEqualTo("FileName",Id);
                docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        Log.d("fetch",doc.getId() + " => " + doc.getData());

                                        db.collection("Premium").document(doc.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "error deleting document", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    Log.d("fetch", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    public void update(String filename,String url)
    {
        items.add(filename);
        urls.add(url);
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
                    int positon=recyclerView.getChildLayoutPosition(v);
                    Intent i=new Intent();
                    i.setDataAndType(Uri.parse(urls.get(positon)),Intent.ACTION_VIEW);

                    context.startActivity(i);
                }
            });

        }
    }
}

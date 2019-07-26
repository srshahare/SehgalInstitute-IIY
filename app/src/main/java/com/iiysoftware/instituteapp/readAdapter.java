package com.iiysoftware.instituteapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class readAdapter extends RecyclerView.Adapter<readAdapter.ViewAdapter> {
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> items;
    private ArrayList<String> urls;
    private ArrayList<String> from_date;
    private ArrayList<String> to_date;
    private  FirebaseFirestore db;
    private String id;


    public readAdapter(RecyclerView recyclerView,Context context,
                       ArrayList<String> items,ArrayList<String> urls,
                       ArrayList<String> from_date, ArrayList<String> to_date)
    {
        this.context=context;
        this.items=items;
        this.recyclerView=recyclerView;
        this.urls=urls;
        this.from_date = from_date;
        this.to_date = to_date;
    }


    @NonNull
    @Override
    public readAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.readtems,parent,false);
        return new readAdapter.ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final readAdapter.ViewAdapter holder, int position) {
        holder.nameofRequest.setText(urls.get(position));
        holder.reasonofRequest.setText(items.get(position));
        holder.from.setText(from_date.get(position));
        holder.to.setText(to_date.get(position));

        db = FirebaseFirestore.getInstance();

        db.collection("requests").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot doc : task.getResult()){

                        holder.approve.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                id = doc.getId();
                                final String url=doc.getData().get("name").toString();
                                final String filename=doc.getData().get("reason").toString();
                                final String from1 = doc.getData().get("from").toString();
                                final String to1 = doc.getData().get("to").toString();
                                final String uid = doc.getData().get("Uid").toString();
                                holder.approve.setText("Accepted");
                                holder.approve.setBackground(context.getResources().getDrawable(R.drawable.accepted_back));
                                holder.approve.setTextColor(Color.BLACK);

                                db.collection("requests").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("from", from1);
                                        hashMap.put("to", to1);
                                        hashMap.put("name", url);
                                        hashMap.put("reason", filename);
                                        hashMap.put("Uid", uid);

                                        db.collection("accepted_req").document("MainData")
                                                .collection("Names").document(id).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "request accepted", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });

                            }
                        });

                        holder.reject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                db.collection("requests").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    public void update(String filename,String url, String from1, String to1)
    {
        items.add(filename);
        urls.add(url);
        from_date.add(from1);
        to_date.add(to1);
        notifyDataSetChanged();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        TextView nameofRequest,reasonofRequest, from, to;
        Button approve, reject;

        public ViewAdapter(final View itemView) {
            super(itemView);
            nameofRequest=itemView.findViewById(R.id.nameofRequest);
            reasonofRequest=itemView.findViewById(R.id.reasonofRequest);
            from = itemView.findViewById(R.id.from_date_fetch);
            to = itemView.findViewById(R.id.to_date_fetch);
            approve = (Button) itemView.findViewById(R.id.approve_btn);
            reject = (Button) itemView.findViewById(R.id.reject_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*int positon=recyclerView.getChildLayoutPosition(v);
                    Intent i=new Intent();
                    i.setDataAndType(Uri.parse(urls.get(positon)),Intent.ACTION_VIEW);

                    context.startActivity(i);*/
                }
            });

        }
    }
}

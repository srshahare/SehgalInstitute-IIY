package com.iiysoftware.instituteapp.Fragments;

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

import com.iiysoftware.instituteapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class allRequestAdapter extends FirestoreRecyclerAdapter<requests, allRequestAdapter.ViewHolder> {

    private Context mContext;

    OnItemClick onItemClick;
    private String uId;
    FirebaseFirestore db;

    public allRequestAdapter(Context mContext, FirestoreRecyclerOptions<requests> options)  {
        super(options);
        this.mContext = mContext;
    }

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{

        void getPosition(int position, String name, String reas, String fro, String to, String uid);

    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final requests model) {

        holder.nameofRequest.setText(model.getName());
        holder.reasonofRequest.setText(model.getReason());
        holder.from.setText(model.getFrom());
        holder.to.setText(model.getTo());
        holder.date.setText(model.getDate());

        db = FirebaseFirestore.getInstance();

        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.approve.setText("Accepted");
                holder.approve.setBackground(mContext.getResources().getDrawable(R.drawable.accepted_back));
                holder.approve.setTextColor(Color.BLACK);

                db.collection("requests").document(model.getDate()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("from", model.getFrom());
                        hashMap.put("to", model.getTo());
                        hashMap.put("name", model.getName());
                        hashMap.put("reason", model.getReason());
                        hashMap.put("Uid", model.getUid());
                        hashMap.put("date", model.getDate());

                        db.collection("accepted_req").document("MainData")
                                .collection("Names").document(model.getDate()).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "request accepted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("requests").document(model.getDate()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mContext, "Rejected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.readtems, viewGroup, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameofRequest,reasonofRequest, from, to, date;
        Button approve, reject;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameofRequest=itemView.findViewById(R.id.nameofRequest);
            reasonofRequest=itemView.findViewById(R.id.reasonofRequest);
            from = itemView.findViewById(R.id.from_date_fetch);
            to = itemView.findViewById(R.id.to_date_fetch);
            approve = (Button) itemView.findViewById(R.id.approve_btn);
            reject = (Button) itemView.findViewById(R.id.reject_btn);
            date = itemView.findViewById(R.id.item_date);
        }

    }
}

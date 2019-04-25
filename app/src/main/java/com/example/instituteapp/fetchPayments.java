package com.example.instituteapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class fetchPayments extends RecyclerView.Adapter<fetchPayments.ViewAdapter>
{  private RecyclerView recyclerView;
   private Context context;
    private ArrayList<String> items;
    private ArrayList<String> name;
    private ArrayList<String> amount;
    private FirebaseFirestore db;


    public fetchPayments(RecyclerView recyclerView, Context context, ArrayList<String> items,
                         ArrayList<String> name, ArrayList<String> amount)
    {
        this.context=context;
        this.items=items;
        this.name = name;
        this.amount = amount;
        this.recyclerView=recyclerView;
    }


    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payments_item,parent,false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAdapter holder, int position) {
        holder.id_pay.setText(items.get(position));
        holder.name_pay.setText(name.get(position));
        holder.amount_pay.setText("Rs. " + amount.get(position));

    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    public void update(String id, String na, String am)
    {
        items.add(id);
        name.add(na);
        amount.add(am);
        notifyDataSetChanged();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        TextView id_pay, name_pay, amount_pay;

        public ViewAdapter(final View itemView) {
            super(itemView);
            id_pay=itemView.findViewById(R.id.payment_id);
            name_pay=itemView.findViewById(R.id.payment_name);
            amount_pay=itemView.findViewById(R.id.payment_amount);

        }
    }
}

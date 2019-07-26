package com.iiysoftware.instituteapp.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iiysoftware.instituteapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReqAcceptedAdapter extends RecyclerView.Adapter<ReqAcceptedAdapter.ViewHolder> {

    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<String> items;
    private ArrayList<String> urls;
    private ArrayList<String> from_date;
    private ArrayList<String> to_date;
    private FirebaseFirestore db;

    public ReqAcceptedAdapter(RecyclerView recyclerView,Context context,
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
    public ReqAcceptedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.readtems,parent,false);
        return new ReqAcceptedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReqAcceptedAdapter.ViewHolder holder, int position) {
        holder.nameofRequest.setText(urls.get(position));
        holder.reasonofRequest.setText(items.get(position));
        holder.from.setText(from_date.get(position));
        holder.to.setText(to_date.get(position));

        holder.approve.setVisibility(View.GONE);
        holder.reject.setText("Delete");
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameofRequest,reasonofRequest, from, to;
        Button approve, reject;

        public ViewHolder(View itemView) {
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

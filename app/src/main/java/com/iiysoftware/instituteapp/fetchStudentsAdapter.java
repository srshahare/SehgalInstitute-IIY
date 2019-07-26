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

public class fetchStudentsAdapter extends RecyclerView.Adapter<fetchStudentsAdapter.ViewAdapter>
{  private RecyclerView recyclerView;
   private Context context;
    private ArrayList<String> items;
    private ArrayList<String> name;
    private ArrayList<String> course;
    private FirebaseFirestore db;


    public fetchStudentsAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items,
                                ArrayList<String> name, ArrayList<String> course)
    {
        this.context=context;
        this.items=items;
        this.name = name;
        this.course = course;
        this.recyclerView=recyclerView;
    }


    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.students_item,parent,false);
        return new ViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewAdapter holder, int position) {
        holder.id_student.setText(items.get(position));
        holder.name_student.setText(name.get(position));
        holder.course_student.setText(course.get(position));

    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    public void update(String id, String na_stu, String cou_stu)
    {
        items.add(id);
        name.add(na_stu);
        course.add(cou_stu);
        notifyDataSetChanged();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        TextView id_student, name_student, course_student;

        public ViewAdapter(final View itemView) {
            super(itemView);
            id_student=itemView.findViewById(R.id.payment_id);
            name_student=itemView.findViewById(R.id.payment_name);
            course_student=itemView.findViewById(R.id.payment_amount);

        }
    }
}

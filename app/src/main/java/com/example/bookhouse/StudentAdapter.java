package com.example.bookhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewholder> {
    Context context;
    ArrayList<StudentModel> list;
    public Onclickliten onclickliten;
    public interface Onclickliten {
        void onclick(StudentModel studentModel);
    }
    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }

    public StudentAdapter(Context context, ArrayList<StudentModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.studentrow, parent, false);
        return new viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        StudentModel studentModel = list.get(position);
        holder.numofbook.setText("Number Of book: " + studentModel.getNumofbook());
        holder.name.setText("Student Name: " + studentModel.getStudentname());
        holder.dept.setText("Department: " + studentModel.getStudentdepartment());
        holder.regi.setText("Registration Num: " + studentModel.getStudentregistration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick(studentModel);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView name, regi, dept, numofbook;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.StudentName);
            dept = itemView.findViewById(R.id.StudentDept);
            regi = itemView.findViewById(R.id.StudentReg);
            numofbook = itemView.findViewById(R.id.NumofBook);
        }
    }

    public void filter(ArrayList<StudentModel> backup) {
        list = backup;
        notifyDataSetChanged();
    }
}

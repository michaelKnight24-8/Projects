package com.example.schoolmanagementsystem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolmanagementsystem.ui.assignments.StudentsFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private ArrayList<StudentItem> studentList;
    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public StudentAdapter(ArrayList<StudentItem> studentList) {
        this.studentList = studentList;
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView nameView;
        ImageView imgView;

        public StudentViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewStudent);
            nameView = itemView.findViewById(R.id.nameView);
            imgView = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });

            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        StudentViewHolder svh = new StudentViewHolder(v, listener);
        return svh;
    }

    public static void addDeleteButton() {

    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentItem sI = studentList.get(position);
        holder.image.setImageResource(sI.getImgResource());
        holder.nameView.setText(sI.getStudentName());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static void showDelete(View v) {

    }

    public static void addStudent(View v) {

    }
}

package com.example.schoolmanagementsystem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static List<TextView> deleteButtonViews;
    private static int DELETE_IS_DISPLAYED = 1;
    static int tags = 1;

    public StudentAdapter(ArrayList<StudentItem> studentList) {
        this.studentList = studentList;
        deleteButtonViews = new LinkedList<>();
    }
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView nameView;
        TextView cancelButton;

        public StudentViewHolder(@NonNull final View itemView, final ArrayList<StudentItem> list) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewStudent);
            nameView = itemView.findViewById(R.id.nameView);
            cancelButton = itemView.findViewById(R.id.cancelBtn);
            //Log.v("Data:", list.get((Integer)cancelButton.getTag() - 1).getStudentName());
            cancelButton.setTag(StudentAdapter.tags++);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Tag is " + cancelButton.getTag().toString(), Toast.LENGTH_LONG).show();

                    StudentsFragment.updateTheAdapter(list, (Integer)cancelButton.getTag());
                }
            });
            deleteButtonViews.add(cancelButton);

        }
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        StudentViewHolder svh = new StudentViewHolder(v, studentList);
        return svh;
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
        String text = (DELETE_IS_DISPLAYED == 1 ? "Done" : "Edit");
        for (TextView tv : deleteButtonViews)
                tv.setVisibility((DELETE_IS_DISPLAYED == 1 ? View.VISIBLE : View.GONE));
        TextView editButton = v.findViewById(R.id.tvEdit);
        editButton.setText(text);
        DELETE_IS_DISPLAYED *= -1;
    }

    public ArrayList getStudentList() { return studentList; }

}

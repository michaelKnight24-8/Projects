package com.example.schoolmanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private ArrayList<AssignmentsItem> assignmentList;
    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AssignmentAdapter(ArrayList<AssignmentsItem> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView dueDate;
        ImageView imgView;


        public AssignmentViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.tvTitle);
            dueDate = itemView.findViewById(R.id.tvDueDate);

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
    public AssignmentAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignments_item, parent, false);
        AssignmentViewHolder svh = new AssignmentViewHolder(v, listener);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        AssignmentsItem aI = assignmentList.get(position);
        holder.title.setText(aI.getTitle());
        holder.dueDate.setText(aI.getDueDate());
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

}

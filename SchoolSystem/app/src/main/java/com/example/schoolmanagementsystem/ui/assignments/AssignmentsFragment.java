package com.example.schoolmanagementsystem.ui.assignments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolmanagementsystem.AssignmentAdapter;
import com.example.schoolmanagementsystem.AssignmentsItem;
import com.example.schoolmanagementsystem.R;
import com.example.schoolmanagementsystem.StudentAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AssignmentsFragment extends Fragment {

    public static AssignmentAdapter aAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<AssignmentsItem> assignmentsList;
    private View v;
    private RecyclerView rView;
    private static SharedPreferences sp;

    private AssignmentsViewModel assignmentsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assignmentsViewModel =
                ViewModelProviders.of(this).get(AssignmentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_assignments, container, false);
        v = root;

        loadData();
        assignmentsViewModel.setView(root, this.getContext());
        createExampleList();
        buildRecyclerView();
        return root;
    }

    private void loadData() {
        sp = this.getContext().getSharedPreferences("assignments", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("assignments-list", null);
        Type type = new TypeToken<ArrayList<AssignmentsItem>>() {}.getType();
        assignmentsList = gson.fromJson(json, type);

        if (assignmentsList == null)
            assignmentsList = new ArrayList<>();
    }

    public void createExampleList() {
//        assignmentsList.add(new AssignmentsItem("HomeWork 15", "Feb12-3", "Math"));
//        assignmentsList.add(new AssignmentsItem( "Read of Mice and Men chapter 34-35", "Tomorrow", "English"));
//        assignmentsList.add(new AssignmentsItem( "Complete history Map", "Tomorrow", "History"));
//        assignmentsList.add(new AssignmentsItem( "Complete problems 1-30", "Thursday", "Science"));
    }

    public static void updateTheAdapter(int position) {
        assignmentsList.remove(position);
        aAdapter.notifyItemRemoved(position);
    }

    private void buildRecyclerView() {
        rView = v.findViewById(R.id.assignmentRecyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        aAdapter = new AssignmentAdapter(assignmentsList);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(aAdapter);
        aAdapter.setOnItemClickListener(new AssignmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onDeleteClick(int position) {
                updateTheAdapter(position);
            }
        });
    }

    public static ArrayList getList() { return assignmentsList; }
}

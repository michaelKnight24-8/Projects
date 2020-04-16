package com.example.schoolmanagementsystem.ui.assignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolmanagementsystem.R;
import com.example.schoolmanagementsystem.StudentAdapter;
import com.example.schoolmanagementsystem.StudentItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentsFragment extends Fragment implements View.OnClickListener{

    private StudentsViewModel studentsViewModel;
    private RecyclerView rView;
    private static RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<StudentItem> studentList;
    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentsViewModel =
                ViewModelProviders.of(this).get(StudentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_students, container, false);
        studentList = new ArrayList<>();

        studentList.add(new StudentItem(R.drawable.ic_students, "John Knight"));
        studentList.add(new StudentItem(R.drawable.ic_students, "Mr Bean"));
        studentList.add(new StudentItem(R.drawable.ic_students, "Maja Knight"));

        v = root;

        TextView tv = root.findViewById(R.id.tvEdit);
        tv.setOnClickListener(this);
        rView = root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        rAdapter = new StudentAdapter(studentList);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(rAdapter);
        return root;
    }

    public static void updateTheAdapter(ArrayList<StudentItem> list, int position) {
        studentList.remove(position - 1);
        rAdapter.notifyItemRemoved(position - 1);
    }

    public final ArrayList getStudentList() { return studentList; }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            StudentAdapter.showDelete(this.v);
        } else {

        }
    }
}

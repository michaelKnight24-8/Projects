package com.example.schoolmanagementsystem.ui.assignments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolmanagementsystem.R;
import com.example.schoolmanagementsystem.StudentAdapter;
import com.example.schoolmanagementsystem.StudentItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StudentsFragment extends Fragment {

    private StudentsViewModel studentsViewModel;
    private RecyclerView rView;
    private static StudentAdapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<StudentItem> studentList;
    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentsViewModel =
                ViewModelProviders.of(this).get(StudentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_students, container, false);
        studentList = new ArrayList<>();

        v = root;
        createExampleList();
        buildRecyclerView();
        return root;
    }

    public static void updateTheAdapter(int position) {
        studentList.remove(position);
        rAdapter.notifyItemRemoved(position);
    }

    public final ArrayList getStudentList() { return studentList; }

    private void showStudentInfo(int position) {
        AlertDialog.Builder eventDialog = new AlertDialog.Builder(this.getContext());
        eventDialog.setTitle("Student Info");
        LinearLayout layout = new LinearLayout(this.getContext());
        final EditText infoBox = new EditText(this.getContext());
        String name = studentList.get(position).getStudentName();
        infoBox.setText("This is the details about " + name + ". \n\n\n Later I will add more info" +
                "each student, such as Parents name, relation to student, address, phone number, and other stuff");
        eventDialog.setView(infoBox);
        eventDialog.show();
    }

    public void createExampleList() {
        studentList.add(new StudentItem(R.drawable.ic_students, "John Knight"));
        studentList.add(new StudentItem(R.drawable.ic_students, "Mr Bean"));
        studentList.add(new StudentItem(R.drawable.ic_students, "Maja Knight"));
    }

    private void buildRecyclerView() {
        rView = v.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        rAdapter = new StudentAdapter(studentList);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(rAdapter);

        rAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showStudentInfo(position);
            }

            @Override
            public void onDeleteClick(int position) {
                updateTheAdapter(position);
            }
        });
    }
}

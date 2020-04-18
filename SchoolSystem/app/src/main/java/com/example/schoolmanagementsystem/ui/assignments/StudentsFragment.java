package com.example.schoolmanagementsystem.ui.assignments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText searchText;
    private TextView clearText;
    private Button addButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentsViewModel =
                ViewModelProviders.of(this).get(StudentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_students, container, false);
        studentList = new ArrayList<>();
        searchText = root.findViewById(R.id.textViewSearch);
        v = root;
        addButton = root.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder eventDialog = new AlertDialog.Builder(v.getContext());
                final EditText nameText = new EditText(v.getContext());
                eventDialog.setTitle("Enter name of Student");
                eventDialog.setView(nameText);
                eventDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nameText.getText().toString().equals("")) {
                            Toast.makeText(v.getContext(), "Please fill out all fields", Toast.LENGTH_LONG);
                        } else {
                            String name = nameText.getText().toString().trim();
                            studentList.add(new StudentItem(R.drawable.ic_students, name));
                            rAdapter.notifyItemInserted(studentList.size() - 1);
                        }
                    }
                });
                eventDialog.show();

            }
        });

        //build the recycler view
        createExampleList();
        buildRecyclerView();
        clearText = root.findViewById(R.id.clearText);

        //clear the edit text field when the clear button is clicked
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
                clearText.setVisibility(View.VISIBLE);
                if (searchText.getText().toString().equals(""))
                    clearText.setVisibility(View.GONE);
            }
        });
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
        studentList.add(new StudentItem(R.drawable.ic_students, "James Knight"));
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

    private void filter(String text) {
        ArrayList<StudentItem> filteredList = new ArrayList<>();

        for (StudentItem item : studentList)
            if (item.getStudentName().toLowerCase().contains(text.toLowerCase()))
                filteredList.add(item);
        rAdapter.filterList(filteredList);
    }
}

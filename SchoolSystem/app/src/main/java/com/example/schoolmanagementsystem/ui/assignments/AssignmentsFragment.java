package com.example.schoolmanagementsystem.ui.assignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.schoolmanagementsystem.AssignmentsItem;
import com.example.schoolmanagementsystem.R;

import java.util.ArrayList;

public class AssignmentsFragment extends Fragment {

    private AssignmentsViewModel assignmentsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        assignmentsViewModel =
                ViewModelProviders.of(this).get(AssignmentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_assignments, container, false);
        ArrayList<AssignmentsItem> itemsList = new ArrayList<>();
        itemsList.add(new AssignmentsItem("Finish problems 12-30", "12-12-2020"));
        return root;
    }
}

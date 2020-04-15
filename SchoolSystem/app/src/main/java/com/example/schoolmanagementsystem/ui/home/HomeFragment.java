package com.example.schoolmanagementsystem.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.schoolmanagementsystem.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button buttonLeft;
    private Button buttonRight;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.setView(view, this.getContext());
        buttonLeft = view.findViewById(R.id.buttonLeft);
        buttonRight = view.findViewById(R.id.buttonRight);
        buttonLeft.setOnClickListener(homeViewModel);
        buttonRight.setOnClickListener(homeViewModel);

        return view;
    }

}

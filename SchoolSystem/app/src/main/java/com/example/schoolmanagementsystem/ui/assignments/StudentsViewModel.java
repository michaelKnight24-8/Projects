package com.example.schoolmanagementsystem.ui.assignments;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentsViewModel extends ViewModel {

    private View view;
    private Context context;
    private MutableLiveData<String> mText;

    public void setView(View v, Context context) {
        view = v;
        this.context = context;
    }

    public StudentsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

}

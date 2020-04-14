package com.example.schoolmanagementsystem;

import android.content.Context;
import android.view.View;

public class CalendarThread extends Thread {
    private View view;
    public CalendarThread(View view) {
        this.view = view;
    }

    @Override
    public void run() {
        //Calendar calendar = new Calendar(4,21,2020, view);
    }
}

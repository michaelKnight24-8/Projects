package com.example.schoolmanagementsystem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class Calendar extends AppCompatActivity {

    private int day, month, year;
    private View view;
    private List<TextView> textViews;
    //long list of the textviews.....
    public TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,
            tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22,
            tv23, tv24, tv25, tv26, tv27, tv28, tv29, tv30, tv31, tv32, tv33, tv34,
            tv35, tv36, tv37, tv38, tv39, tv40, tv41, tv42;

    public Calendar(int month, int day, int year, View view) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.view = view;
        textViews = new LinkedList<>();
        initTextViews();
        displayCalendar(month, year);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
    }


    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private int calcDaysInMonth(int month, int year) {
        // gets the correct number of days for the given month
        //simple array containing the months with 30 days so the if statement isn't super cluttered

        if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        else if (month == 2)
            return (isLeapYear(year) ? 29 : 28);
        else
            return 31;
    }

    private int calcOffset(int month, int year) {
        int daysPassed = 0;
        int startYear = 1753;

        //simple loop to find out the offset needed
        for (int i = startYear; i < year; i++ )
            daysPassed += (isLeapYear(i) ? 366 : 365);

        int days = 0;
        for (int i = 1; i < month; i++)
            days += calcDaysInMonth(i, year);

        return (daysPassed + days) % 7;
    }

    void displayCalendar(int month, int year) {
        TextView startDay;
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        int day = calcDaysInMonth(month, year);
        //print(f'\n{months_tuple[month - 1]}, {year}')
        int offset = calcOffset(month, year);
        int textViewIndex = 0;
        //print('  Su  Mo  Tu  We  Th  Fr  Sa')
        switch (offset) {
            case 6:
                startDay = findViewById(R.id.tv1);
                textViewIndex = 0;
                break;
            case 0:
                startDay = findViewById(R.id.tv2);
                textViewIndex = 1;
                break;
            case 1:
                startDay = findViewById(R.id.tv3);
                textViewIndex = 2;
                break;
            case 2:
                startDay = findViewById(R.id.tv4);
                textViewIndex = 3;
                break;
            case 3:
                startDay = findViewById(R.id.tv5);
                textViewIndex = 4;
                break;
            case 4:
                startDay = findViewById(R.id.tv6);
                textViewIndex = 5;
                break;
            case 5:
                startDay = findViewById(R.id.tv7);
                textViewIndex = 6;
                break;
        }
            //now print out the numbers, with the correct spacing
        for (int i = 1; i < day + 1; i++, textViewIndex++) {
            textViews.get(textViewIndex).setText(i);
        }
    }

    //getters and setters
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private void initTextViews() {
        //initialize all the textview objects.....get ready....
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv6 = view.findViewById(R.id.tv6); tv7 = view.findViewById(R.id.tv7);
        //Log.v("works?", "Is this even getting here?");
        tv8 = view.findViewById(R.id.tv8); tv9 = view.findViewById(R.id.tv9);
        tv10 = view.findViewById(R.id.tv10); tv11 = view.findViewById(R.id.tv11);
        tv12 = view.findViewById(R.id.tv12); tv13 = view.findViewById(R.id.tv13);
        tv14 = view.findViewById(R.id.tv14); tv15 = view.findViewById(R.id.tv15);
        tv16 = view.findViewById(R.id.tv16); tv17 = view.findViewById(R.id.tv17);
        tv18 = view.findViewById(R.id.tv18); tv19 = view.findViewById(R.id.tv19);
        tv20 = view.findViewById(R.id.tv20); tv21 = view.findViewById(R.id.tv21);
        tv22 = view.findViewById(R.id.tv22); tv23 = view.findViewById(R.id.tv23);
        tv24 = view.findViewById(R.id.tv24); tv25 = view.findViewById(R.id.tv25);
        tv26 = view.findViewById(R.id.tv26); tv27 = view.findViewById(R.id.tv27);
        tv28 = view.findViewById(R.id.tv28); tv29 = view.findViewById(R.id.tv29);
        tv30 = view.findViewById(R.id.tv30); tv31 = view.findViewById(R.id.tv31);
        tv32 = view.findViewById(R.id.tv32); tv33 = view.findViewById(R.id.tv33);
        tv34 = view.findViewById(R.id.tv34); tv35 = view.findViewById(R.id.tv35);
        tv36 = view.findViewById(R.id.tv36); tv37 = view.findViewById(R.id.tv37);
        tv38 = view.findViewById(R.id.tv38); tv39 = view.findViewById(R.id.tv39);
        tv40 = view.findViewById(R.id.tv40); tv41 = view.findViewById(R.id.tv41);
        tv42 = view.findViewById(R.id.tv42);

        //now add them to the linkedlist containing all the textviews
        //lot to do here, but it makes it alot easier to use them later!

        textViews.add(tv1); textViews.add(tv2); textViews.add(tv3); textViews.add(tv4);
        textViews.add(tv5); textViews.add(tv6); textViews.add(tv7); textViews.add(tv8);
        textViews.add(tv9); textViews.add(tv10); textViews.add(tv11); textViews.add(tv12);
        textViews.add(tv13); textViews.add(tv14); textViews.add(tv15); textViews.add(tv16);
        textViews.add(tv17); textViews.add(tv18); textViews.add(tv19); textViews.add(tv20);
        textViews.add(tv21); textViews.add(tv22); textViews.add(tv23); textViews.add(tv24);
        textViews.add(tv25); textViews.add(tv26); textViews.add(tv27); textViews.add(tv28);
        textViews.add(tv29); textViews.add(tv30); textViews.add(tv31); textViews.add(tv32);
        textViews.add(tv33); textViews.add(tv34); textViews.add(tv35); textViews.add(tv36);
        textViews.add(tv37); textViews.add(tv38); textViews.add(tv39); textViews.add(tv40);
        textViews.add(tv41); textViews.add(tv42);
    }
}

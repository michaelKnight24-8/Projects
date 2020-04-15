package com.example.schoolmanagementsystem.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolmanagementsystem.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends ViewModel implements View.OnClickListener, View.OnLongClickListener {

    private SharedPreferences sP;
    private String title, description;
    private MutableLiveData<String> mText;
    private Context context;
    private int month, year, day, currentMonth, currentYear;
    //view object to access the textviews from the home fragment
    private View view;
    //list of all the textviews used for the calendar
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,
            tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22,
            tv23, tv24, tv25, tv26, tv27, tv28, tv29, tv30, tv31, tv32, tv33, tv34,
            tv35, tv36, tv37, tv38, tv39, tv40, tv41, tv42;
    private List <TextView> textViews;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        year  = localDate.getYear();
        currentYear = year;
        currentMonth = localDate.getMonthValue();
        month = currentMonth;
        day   = localDate.getDayOfMonth();
    }

    public void setView(View view, Context context) {
        this.context = context;
        this.view = view;
        textViews = new LinkedList<>();
        initTextViews();
        displayCalendar();

        //shared preferences object to store, and recall the events the user
        //has added
        sP = context.getSharedPreferences("listOfEvents", 0);

    }

    public LiveData<String> getText() {
        return mText;
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

    //small function to clear the values in the text views so that each month can
    //be displayed properly
    private void clearTextViewText() {
        for (TextView textView : textViews)
                textView.setText("");
    }

    void displayCalendar() {

        //if December was the previous month, increment the year, and set the
        //month to January
        if (month == 13) {
            month = 1;
            year++;
        } else if (month == 0) {
            month = 12;
            year--;
        }

        clearTextViewText();
        TextView startDay;
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        TextView monthDisplay = view.findViewById(R.id.tvMonthDisplay);
        Log.v("Month Number:", Integer.toString(month));
        String dateDisplayText = months[month - 1] + "   " + year;
        monthDisplay.setText(dateDisplayText);
        int day = calcDaysInMonth(month, year);

        int offset = calcOffset(month, year);
        int textViewIndex = 0;

        //switch statement to get where the first day of the month
        //falls in the week
        switch (offset) {
            case 6:
                textViewIndex = 0;
                break;
            case 0:
                textViewIndex = 1;
                break;
            case 1:
                textViewIndex = 2;
                break;
            case 2:
                textViewIndex = 3;
                break;
            case 3:
                textViewIndex = 4;
                break;
            case 4:
                textViewIndex = 5;
                break;
            case 5:
                textViewIndex = 6;
                break;
        }

        //now print out the numbers, with the correct spacing
        for (int i = 1; i < day + 1; i++, textViewIndex++) {
            String currentDay = Integer.toString(i);
            if (currentDay.length() == 1)
                textViews.get(textViewIndex).setText(" " + currentDay);
            else
                textViews.get(textViewIndex).setText(currentDay);
            textViews.get(textViewIndex).setOnClickListener(this);
            textViews.get(textViewIndex).setOnLongClickListener(this);
            if (i == this.day && month == currentMonth)
                textViews.get(textViewIndex).setTextColor(Color.GREEN);
            else
                textViews.get(textViewIndex).setTextColor(Color.BLACK);
        }
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


    public void setEventNotifier(final int viewTag) {
        boolean hasEventAlready = false;
        final String text = textViews.get(viewTag).getText().toString();
        if (text.length() >= 5) {
            hasEventAlready = true;
        }

        if (!hasEventAlready) {
            AlertDialog.Builder eventDialog = new AlertDialog.Builder(context);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            eventDialog.setTitle("Add an event");

            final EditText titleBox = new EditText(context);
            titleBox.setHint("Title");
            titleBox.setPadding(20,100,0,50);
            layout.addView(titleBox);

            final EditText descriptionBox = new EditText(context);
            descriptionBox.setHint("Description");
            descriptionBox.setPadding(20,0,0,50);
            layout.addView(descriptionBox); // Another add method

            eventDialog.setView(layout); // Again this is a set method, not add

            //first set positive button
            eventDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String eventText = text;
                    eventText += "\n o";
                    textViews.get(viewTag).setText(eventText);
                    title = titleBox.getText().toString();
                    description = descriptionBox.getText().toString();
                    Toast.makeText(context, "Event added successfully", Toast.LENGTH_LONG).show();
                }
            });
            //now negative button
            eventDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "Event cancelled", Toast.LENGTH_LONG).show();
                }
            });
            eventDialog.show();
        } else {
            Toast.makeText(context, "You already have an event on this day", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        //if button was clicked, something new will have to be done
        if (v instanceof Button) {
            switch (v.getId()) {
                case R.id.buttonLeft:
                    this.month--;
                    displayCalendar();
                    break;
                case R.id.buttonRight:
                    this.month++;
                    displayCalendar();
                    break;
            }
        } else //it was a text view
            setEventNotifier(Integer.parseInt(v.getTag().toString()) - 1);
    }

    @Override
    public boolean onLongClick(View v) {

        //let the user either view the current event, add an event, or delete the
        //event that is set for the day
        AlertDialog showOptionsDialog = new AlertDialog.Builder(context).create();
        showOptionsDialog.setTitle("Event Options");
        showOptionsDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Make new event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        showOptionsDialog.setButton(DialogInterface.BUTTON_POSITIVE, "View event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        showOptionsDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Delete event", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        showOptionsDialog.show();
//        AlertDialog.Builder showEventDialog = new AlertDialog.Builder(context);
//        LinearLayout layout = new LinearLayout(context);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        showEventDialog.setTitle("Event Details");
//
//        final EditText titleBox = new EditText(context);
//        titleBox.setText(title);
//        titleBox.setFocusable(false);
//        titleBox.setPadding(20,100,0,50);
//        layout.addView(titleBox);
//
//        final EditText descriptionBox = new EditText(context);
//        descriptionBox.setText(description);
//        descriptionBox.setFocusable(false);
//        descriptionBox.setPadding(20,0,0,50);
//        layout.addView(descriptionBox); // Another add method
//
//        showEventDialog.setView(layout);
//        showEventDialog.show();
        return false;
    }
}
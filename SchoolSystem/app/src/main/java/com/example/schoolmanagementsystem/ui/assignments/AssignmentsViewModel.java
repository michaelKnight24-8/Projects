package com.example.schoolmanagementsystem.ui.assignments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolmanagementsystem.AssignmentsItem;
import com.example.schoolmanagementsystem.R;
import com.example.schoolmanagementsystem.StudentItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.schoolmanagementsystem.ui.assignments.AssignmentsFragment.aAdapter;

public class AssignmentsViewModel extends ViewModel implements View.OnClickListener{

    private MutableLiveData<String> mText;
    private View v;
    private static Context context;
    private TextView history;
    private TextView math;
    private TextView english;
    private TextView science;
    private TextView addAssignment;
    private static String datePicked;
    private static String subject;
    private static ArrayList<AssignmentsItem> assignments;

    public AssignmentsViewModel() {
        mText = new MutableLiveData<>();
    }

    public void setView(View v, Context context) {
        this.v = v;
        this.context = context;

        //set up the txt views, and assign them onclick listeners
        history = v.findViewById(R.id.tvHistory);
        english = v.findViewById(R.id.tvEnglish);
        science = v.findViewById(R.id.tvScience);
        math = v.findViewById(R.id.tvMath);
        addAssignment = v.findViewById(R.id.addAssignment);

        //now the onclickListeners
        history.setOnClickListener(this);
        math.setOnClickListener(this);
        english.setOnClickListener(this);
        science.setOnClickListener(this);
        addAssignment.setOnClickListener(this);

        //initialize the arraylist
        assignments = AssignmentsFragment.getList();
    }

    public LiveData<String> getText() {
        return mText;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addAssignment) {
            //build the event popup
            AlertDialog.Builder eventDialog = new AlertDialog.Builder(v.getContext());

            //initialize the views
            final EditText detailsText = new EditText(v.getContext());
            TextView dueDate = new TextView(v.getContext());
            CalendarView calendarView = new CalendarView(v.getContext());
            final Spinner spinner = new Spinner(v.getContext());
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                    R.array.subjects, android.R.layout.simple_spinner_dropdown_item);
            LinearLayout lnLay = new LinearLayout(v.getContext());

            //set the details of the views
            eventDialog.setTitle("New Assignment");
            detailsText.setPadding(20,0,0,50);
            dueDate.setText("Due Date");
            dueDate.setTextSize(30);
            dueDate.setGravity(Gravity.CENTER);
            lnLay.setOrientation(LinearLayout.VERTICAL);
            detailsText.setHint("Assignment details");
            spinner.setAdapter(adapter);

            //onclick listeners
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                    datePicked = date;
                }
            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    subject = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //add the views now
            lnLay.addView(detailsText);
            lnLay.addView(spinner);
            lnLay.addView(dueDate);
            lnLay.addView(calendarView);
            eventDialog.setView(lnLay);

            //event dialog buttons
            eventDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (detailsText.getText().toString().equals("")) {
                        Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_LONG).show();
                    } else {
                        String details = detailsText.getText().toString().trim();
                        assignments.add(new AssignmentsItem(detailsText.getText().toString(),
                                datePicked, subject));
                        aAdapter.notifyItemInserted(assignments.size() - 1);
                        //now add the subject to the corresponding subject array
                        Toast.makeText(context, "Assignment added successfully!", Toast.LENGTH_LONG).show();
                        saveData();
                        //sp = context.getSharedPreferences()
                    }
                }
            });
            eventDialog.show();
        } else {
            //this means the individual courses from the top were selected
            String name = "";
            switch (v.getId()) {
                case R.id.tvHistory:
                    name = "History";
                    break;
                case R.id.tvEnglish:
                    name = "English";
                    break;
                case R.id.tvMath:
                    name = "Math";
                    break;
                case R.id.tvScience:
                    name = "Science";
                    break;
            }

            //now show all the assignments for the individual courses

            //iterate over the list of assignments. If the subject attribute of the current object
            //is the same as the subject clicked on, add it!
            AlertDialog.Builder eventDialog = new AlertDialog.Builder(v.getContext());
            LinearLayout lnLay = new LinearLayout(v.getContext());
            TextView title = new TextView(v.getContext());
            lnLay.setOrientation(LinearLayout.VERTICAL);
            title.setTextSize(30);
            title.setText("Assignments:");
            title.setTypeface(Typeface.DEFAULT_BOLD);
            TextView tv = new TextView(v.getContext());
            tv.setTextSize(20);
            String subjectDetails = "";
            for (AssignmentsItem item : assignments) {
                if (item.getSection().equals(name)) {
                    subjectDetails += item.getTitle() + "\nDue: " + item.getDueDate() + "\n\n";
                }
            }
            if (subjectDetails.equals(""))
                subjectDetails = "There are no assignments currently for this class";

            tv.setText(subjectDetails);
            lnLay.addView(title);
            lnLay.addView(tv);
            eventDialog.setView(lnLay);
            eventDialog.show();
        }
    }

    public static void saveData() {
        SharedPreferences sp = context.getSharedPreferences("assignments", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(assignments);
        editor.putString("assignments-list", json);
        editor.apply();
    }
}
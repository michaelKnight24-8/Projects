package com.example.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private final String DEFAULT = "N/A";
    private SharedPreferences sP;
    private Switch switchSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        switchSave = findViewById(R.id.switchSave);

        //first check to see if the user wants to stay logged in
        sP = getSharedPreferences("Login-info", 0);
        if (!sP.getBoolean("saveUsername", false)) {
            Intent intent = new Intent(MainActivity.this, com.example.schoolmanagementsystem.MainScreenNavigation.class);
            startActivity(intent);
        }

        //set up the listener for thew switch
        switchSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sP.edit();

                if (switchSave.isChecked()) editor.putBoolean("saveUserName", true);
                else editor.putBoolean("saveUserName", true);

                editor.apply();
            }
        });

    }

    public void login(View v) {
        sP = getSharedPreferences("Login-Info", 0);
        String username = userName.getText().toString();

        //first make sure that all fields are entered in properly
        if (userName.getText().toString().isEmpty() ||
            password.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_LONG).show();
        else {
            //then check to see if there is already a username saved to sharedpreferences, or
            //the username entered is wrong
            if ((sP.getString("username", DEFAULT).equals(DEFAULT)) ||
                    !username.equals(sP.getString("username", DEFAULT))) {
                Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG).show();
            } else {
                //now check to see if the password was entered correctly
                if (password.getText().toString().equals(sP.getString(username, DEFAULT))) {
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, com.example.schoolmanagementsystem.MainScreenNavigation.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    public void signUp(View v) {
        sP = getSharedPreferences("Login-Info", 0);
        //first make sure that all the fields are filled out
        if (userName.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_LONG).show();
        else {
            //then add the username and password to sharedpreference
            SharedPreferences.Editor editor = sP.edit();
            editor.putString("username", userName.getText().toString());
            editor.putString(userName.getText().toString(), password.getText().toString());
            Toast.makeText(getApplicationContext(), "You have been signed up!", Toast.LENGTH_LONG).show();
            editor.apply();
            userName.getText().clear();
            password.getText().clear();
        }
    }
}

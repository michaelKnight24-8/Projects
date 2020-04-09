package com.example.bankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName;
    EditText password;
    private final String DEFAULT = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
    }

    public void login(View v) {
        SharedPreferences sP = getSharedPreferences("Login-Info", 0);
        String username = userName.getText().toString();
        if ((sP.getString("username", DEFAULT).equals(DEFAULT)) ||
                !username.equals(sP.getString("username", DEFAULT))) {
            Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_LONG).show();
        } else {
            if (password.getText().toString().equals(sP.getString(username, DEFAULT))) {
                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void signUp(View v) {
        SharedPreferences sP = getSharedPreferences("Login-Info", 0);
        SharedPreferences.Editor editor = sP.edit();
        editor.putString("username", userName.getText().toString());
        editor.putString(userName.getText().toString(), password.getText().toString());
        Toast.makeText(getApplicationContext(), "You have been signed up!", Toast.LENGTH_LONG).show();
        editor.commit();
    }
}

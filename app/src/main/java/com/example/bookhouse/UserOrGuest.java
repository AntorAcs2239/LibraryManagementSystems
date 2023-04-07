package com.example.bookhouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserOrGuest extends AppCompatActivity {
    SharedPreferences sharepef;
    Button student, guest;
    private static String check;
    private static boolean aBoolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_or_guest);
        student = findViewById(R.id.student);
        guest = findViewById(R.id.guest);
        sharepef = getPreferences(MODE_PRIVATE);
        check = sharepef.getString("check", null);
        if (check == null) {
            student.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveme("student");
                    startActivity(new Intent(UserOrGuest.this, LoginActivity.class));
                    finish();
                }
            });
            guest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveme("Guest");
                    startActivity(new Intent(UserOrGuest.this, MainActivity.class));
                    finish();
                }
            });
        } else if (check.equals("student")) {
            startActivity(new Intent(UserOrGuest.this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(UserOrGuest.this, MainActivity.class));
            finish();
        }
    }

    public void saveme(String name) {
        SharedPreferences.Editor editor = sharepef.edit();
        editor.putString("check", name);
        editor.apply();
    }
}
package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.project1.R;

public class GoalsActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project1.userIdKey";
    private static final String PREFS = "com.example.project1.prefs";
    private int mUserId = -1;
    Button btnBack;

    //good

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        wireUpDisplay();
    }

    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context, GoalsActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }

    public void wireUpDisplay() {
        btnBack = findViewById(R.id.btn_activity_goals_main_back);

        btnBack.setOnClickListener(v -> {
            Intent intent = MainActivity.intentFactory(this, mUserId);
            startActivity(intent);
        });
    }
}
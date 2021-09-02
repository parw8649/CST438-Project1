package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        Intent intent = new Intent(String.valueOf(LoginActivity.class));
//        startActivity(intent);

        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();

    }
}

package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private EditText confirmPassword;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        user = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        confirmPassword = findViewById(R.id.etConfirmPassword);
        register = findViewById(R.id.btnRegister);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Empty Data", Toast.LENGTH_LONG).show();
                } else if (pass.getText().toString().equals(confirmPassword.getText().toString())) {
                    //successful login
                    Toast.makeText(RegisterActivity.this, "Correct Password", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

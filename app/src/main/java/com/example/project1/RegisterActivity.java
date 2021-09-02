package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button register;


    String user = "admin";
    String pass = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.name);
        password = findViewById(R.id.pass);
        confirmPassword = findViewById(R.id.etConfirmPassword);
        register = findViewById(R.id.btnRegister);

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////              Intent i = new Intent(this, Register.class);
////               startActivity(i);
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Empty Data", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().equals(confirmPassword)) {
                    //successful login
                    Toast.makeText(RegisterActivity.this, "Successful login", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

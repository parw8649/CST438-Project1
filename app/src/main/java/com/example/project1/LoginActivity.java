package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    private Button signUp;

    /**
     * this is a comment
     */


    String user = "admin";
    String pass = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        username = findViewById(R.id.etName);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(String.valueOf(RegisterActivity.class));
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Empty Data", Toast.LENGTH_LONG).show();
                }else if(username.getText().toString().equals(user)){
                    //check password
                    if(password.getText().toString().equals(pass)){
                        Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_LONG).show();
//                        Intent i = new Intent(this, LandingPage.class);
//                        startActivity(i);
                        Intent intent = new Intent(String.valueOf(MainActivity.class));
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}

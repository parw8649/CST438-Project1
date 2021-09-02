package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

//    private EditText username;
//    private EditText password;
//    private Button login;
//    private Button signup;
//
//
//    String user = "admin";
//    String pass = "admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();


//        username = findViewById(R.id.etName);
//        password = findViewById(R.id.etPassword);
//        login = findViewById(R.id.btnLogin);
//        signup = findViewById(R.id.btnSignup);
//
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////              Intent i = new Intent(this, Register.class);
////               startActivity(i);
//            }
//        });

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty())
//                {
//                    Toast.makeText(MainActivity.this, "Empty Data", Toast.LENGTH_LONG).show();
//                }else if(username.getText().toString().equals(user)){
//                    //check password
//                    if(password.getText().toString().equals(pass)){
//                        Toast.makeText(MainActivity.this, "Successful login", Toast.LENGTH_LONG).show();
////                        Intent i = new Intent(this, LandingPage.class);
////                        startActivity(i);
//                        Intent intent = new Intent(LoginActivity.class);
//                        startActivity(intent);
//                    }else{
//                        Toast.makeText(MainActivity.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


    }
}

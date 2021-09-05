package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    private String mUsername, mPassword;
    private FitnessLogDao fitnessLogDao;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getDatabase();
        wireUpDisplay();
    }

    private void getDatabase() {
        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
    }

    private void wireUpDisplay() {

        etUsername = findViewById(R.id.et_signUp_username);
        etPassword = findViewById(R.id.et_signUp_password);
        Button saveButton = findViewById(R.id.button_sign_up);
        Button backButton = findViewById(R.id.button_signUp_back);

        saveButton.setOnClickListener(v -> {

            getValuesFromDisplay();
            if(checkForUserInDatabase()) {
                Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show();
            } else {
                mUser = new User(mUsername, mPassword);
                mUser.setUserId(fitnessLogDao.insert(mUser).intValue());
                Intent intent = MainActivity.intentFactory(SignUpActivity.this, mUser.getUserId());
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(v -> finish());
    }

    private boolean checkForUserInDatabase() {

        mUser = fitnessLogDao.getUserByUsername(mUsername);
        return mUser != null;
    }

    private void getValuesFromDisplay() {
        mUsername = etUsername.getText().toString();
        mPassword = etPassword.getText().toString();
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, SignUpActivity.class);
    }
}
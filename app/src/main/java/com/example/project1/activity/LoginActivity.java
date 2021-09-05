package com.example.project1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project1.R;
import com.example.project1.model.User;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;

    private FitnessLogDao fitnessLogDao;

    private String mUsername, mPassword;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireUpDisplay();
        getDatabase();

    }

    private void wireUpDisplay() {

        mUsernameField = findViewById(R.id.et_login_username);
        mPasswordField = findViewById(R.id.et_login_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnSignUp = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(v -> {
            getValuesFromDisplay();
            if(checkForUserInDatabase()) {
                if(!validatePassword()) {
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                    startActivity(intent);
                }
            }
        });

        btnSignUp.setOnClickListener(v -> {
            Intent intent = SignUpActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    private void getValuesFromDisplay() {

        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }

    private boolean checkForUserInDatabase() {

        mUser = fitnessLogDao.getUserByUsername(mUsername);
        if(mUser == null) {
            Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {

        return mUser.getPassword().equals(mPassword);
    }

    private void getDatabase() {
        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
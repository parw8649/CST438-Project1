package com.example.project1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.FitnessLog;
import com.example.project1.model.User;

import java.util.List;

public class DisplayExerciseActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project1.userIdKey";
    private ListView mMainDisplay;
    private List<FitnessLog> fitnessLogs;
    private Button mDeleteButton;
    private TextView displayMsg;

    private ArrayAdapter<FitnessLog> adapter;

    private FitnessLogDao fitnessLogDao;

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exercise);

        getDatabase();
        getUserDetails();
        wireUpDisplay();
        refreshDisplay();
    }

    private void getUserDetails() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
        mUser = fitnessLogDao.getUserByUserId(mUserId);
    }

    private void wireUpDisplay() {

        displayMsg = findViewById(R.id.exerciseMsgDisplay);
        mMainDisplay = findViewById(R.id.lv_exercise_display);

        Button backButton = findViewById(R.id.btn_exercise_main_back);

        backButton.setOnClickListener(v -> {
            Intent intent = MainActivity.intentFactory(this, mUserId);
            startActivity(intent);
        });

        View.OnClickListener listenerDel = v -> {
            /** Getting the checked items from the listview */
            SparseBooleanArray checkedItemPositions = mMainDisplay.getCheckedItemPositions();
            int itemCount = mMainDisplay.getCount();

            for (int i = itemCount - 1; i >= 0; i--) {
                if (checkedItemPositions.get(i)) {
                    FitnessLog fitnessLog = fitnessLogs.get(i);
                    adapter.remove(fitnessLog);
                    fitnessLogDao.delete(fitnessLog);
                }
            }

            checkedItemPositions.clear();
            adapter.notifyDataSetChanged();
            refreshDisplay();
        };

        Button mSubmitButton = findViewById(R.id.addNewExerciseLog);
        mDeleteButton = findViewById(R.id.deleteExerciseButton);

        mDeleteButton.setOnClickListener(listenerDel);

        mSubmitButton.setOnClickListener(v -> {
            Intent intent = ExerciseActivity.intentFactory(this, mUserId);
            startActivity(intent);
        });
    }

    private void getDatabase() {
        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
    }

    private void refreshDisplay() {

        fitnessLogs = fitnessLogDao.getFitnessLogsByUserId(mUserId);

        if(fitnessLogs.isEmpty()) {
            mMainDisplay.setVisibility(View.INVISIBLE);
            displayMsg.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.INVISIBLE);

            displayMsg.setText(R.string.noLogsMessage);

        } else {
            mMainDisplay.setVisibility(View.VISIBLE);
            displayMsg.setVisibility(View.INVISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
        }

        // Adding items to listview
        adapter = new ArrayAdapter<>(this, R.layout.display_list_view, R.id.workout_name, fitnessLogs);
        mMainDisplay.setAdapter(adapter);
    }

    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context, DisplayExerciseActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }
}
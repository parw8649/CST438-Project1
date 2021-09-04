package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.Exercise;
import com.example.project1.model.FitnessLog;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project1.userIdKey";
    private static final String PREFS = "com.example.project1.prefs";

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;
    private Dialog dialog;

    private EditText repetitions, weight;

    private FitnessLogDao fitnessLogDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        getDatabase();
        wireUpDisplay();
        initExerciseData();
        getUserDetails();
    }

    private void getDatabase() {
        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
    }

    private void getUserDetails() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }

    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context, ExerciseActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }

    private void initExerciseData() {

        //TODO: Replace below code with Third party Fitness API Integration
        List<Exercise> exerciseList = fitnessLogDao.getAllExerciseList();
        if(exerciseList.size() == 0) {
            fitnessLogDao.insert(new Exercise("Lunges"), new Exercise("PushUps"),
                    new Exercise("Squats"), new Exercise("Dumbbell rows"),
                    new Exercise("Planks"), new Exercise("Biceps"),
                    new Exercise("Triceps"), new Exercise("PullUps"));
        }
        refreshDisplay(exerciseList);
    }

    private void wireUpDisplay() {

        listView = findViewById(R.id.lv_exercise_list_data);
        Button backButton = findViewById(R.id.btn_exercise_list_back);

        backButton.setOnClickListener(v -> finish());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            displayDialog( -1);
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    displayDialog(position);
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    private void displayDialog(int position) {

        dialog =new Dialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.operations, null);

        dialog.setContentView(view);

        if(position != -1) {
            dialog.show();
        }

        Button addExerciseButton = view.findViewById(R.id.btn_save_data);

        addExerciseButton.setEnabled(position != -1);

        addExerciseButton.setOnClickListener(v -> {
            processExerciseData(position, view);
            dialog.dismiss();
        });
    }

    private void processExerciseData(int position, View view) {

        String value = (String) listView.getItemAtPosition(position);

        Exercise exercise = fitnessLogDao.getByExerciseName(value);

        if(exercise == null) {
            Toast.makeText(this, "Invalid Exercise fetched!", Toast.LENGTH_LONG).show();
        } else {

            repetitions = view.findViewById(R.id.et_repetitions);
            weight = view.findViewById(R.id.et_weight);

            int reps = Integer.parseInt(repetitions.getText().toString());
            int inputWeight = Integer.parseInt(weight.getText().toString());

            FitnessLog fitnessLog = new FitnessLog(exercise.getExerciseName(), inputWeight, reps, mUserId);
            fitnessLogDao.insert(fitnessLog);
        }
    }

    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1) {
            return;
        }

        if(mPreferences == null) {
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1) {
            return;
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private void refreshDisplay(List<Exercise> exerciseList) {

        if(exerciseList.isEmpty()) {
            exerciseList = fitnessLogDao.getAllExerciseList();
        }

        List<String> exercises = new ArrayList<>();
        for(Exercise exercise : exerciseList) {
            exercises.add(exercise.toString());
        }

        // Adding items to listview
        adapter = new ArrayAdapter<>(this, R.layout.operational_list_view, R.id.operational_exercise_name, exercises);
        listView.setAdapter(adapter);
    }
}
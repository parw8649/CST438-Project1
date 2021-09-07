package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.Exercise;
import com.example.project1.model.FitnessLog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayExerciseActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project1.userIdKey";
    private FitnessLogDao fitnessLogDao;
    private int mUserId = -1;
    ListView listViewResult;
    List<Exercise> exercises;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_exercise);
        exercises = new ArrayList<>();

        getDatabase();
        wireUpDisplay();

    }

    private void deleteExercise(Exercise exercise) {
        fitnessLogDao.delete(exercise);
    }

    private void getUserDetails() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }

    public List<Exercise> getAllExercises() {
        return fitnessLogDao.getAllExerciseList();
    }

    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context, DisplayExerciseActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }

    private void getDatabase() {
        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
    }

    private void wireUpDisplay() {
        exercises = getAllExercises();
        final ArrayAdapter<Exercise> arrayAdapter = new ArrayAdapter<Exercise>
                (this, android.R.layout.simple_list_item_1, exercises);

        listViewResult = findViewById(R.id.list_view_result);
        listViewResult.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


//        textViewResult.setOnClickListener(v -> {
//            Intent intent = DisplaySelectedExercise.intentFactory(this, mUserId);
//            startActivity(intent);
//        });

//
//        backButton.setOnClickListener(v -> {
//            Intent intent = MainActivity.intentFactory(this, mUserId);
//            startActivity(intent);
//        });
//
        listViewResult.setOnItemClickListener((parent, view, position, id) -> {
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

        dialog = new Dialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.operations, null);

        dialog.setContentView(view);

        if(position != -1) {
            dialog.show();
        }

        Button displayExerciseButton = view.findViewById(R.id.btn_save_data);

        displayExerciseButton.setEnabled(position != -1);

        displayExerciseButton.setOnClickListener(v -> {
            processExerciseData(position, view);
            dialog.dismiss();
        });
    }

    private void processExerciseData(int position, View view) {

        String value = (String) listViewResult.getItemAtPosition(position);

        Exercise exercise = fitnessLogDao.getByExerciseName(value);

        if(exercise == null) {
            Toast.makeText(this, "Invalid Exercise fetched!", Toast.LENGTH_LONG).show();
        } else {

            TextView exerciseInfo = view.findViewById(R.id.tv_operations);
            exerciseInfo.setText(exercise.getExerciseInfo());

//            EditText repetitions = view.findViewById(R.id.et_repetitions);
//            EditText weight = view.findViewById(R.id.et_weight);

//            int reps = Integer.parseInt(repetitions.getText().toString());
//            int inputWeight = Integer.parseInt(weight.getText().toString());

//            FitnessLog fitnessLog = new FitnessLog(exercise.getExerciseName(), inputWeight, reps, mUserId);
//            fitnessLogDao.insert(fitnessLog);
        }
    }
}
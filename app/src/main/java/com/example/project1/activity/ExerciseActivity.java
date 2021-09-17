package com.example.project1.activity;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.external.ExerciseDataInfo;
import com.example.project1.external.ExternalProcess;
import com.example.project1.external.FitnessAPI;
import com.example.project1.external.FitnessData;
import com.example.project1.external.WorkoutDataInfo;
import com.example.project1.model.Exercise;
import com.example.project1.model.FitnessLog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project1.userIdKey";

    private int mUserId = -1;

    private ListView listView;
    private Dialog dialog;

    private FitnessLogDao fitnessLogDao;

    private List<Exercise> exerciseList;

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

        exerciseList = fitnessLogDao.getAllExerciseList();
        if(exerciseList.size() == 0) {
            fetchExerciseInfo();
        }

        refreshDisplay();
    }

    private void fetchExerciseInfo() {

        FitnessAPI fitnessAPI = ExternalProcess.getFitnessAPIInstance();

        Call<FitnessData<ExerciseDataInfo>> exerciseInfo = fitnessAPI.getAllExerciseInfo();

        exerciseInfo.enqueue(new Callback<FitnessData<ExerciseDataInfo>>() {
            @Override
            public void onResponse(@NonNull Call<FitnessData<ExerciseDataInfo>> call, Response<FitnessData<ExerciseDataInfo>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                FitnessData<ExerciseDataInfo> fitnessData = response.body();

                if(fitnessData != null) {
                    fetchExerciseData(fitnessData);
                }
            }

            @Override
            public void onFailure(Call<FitnessData<ExerciseDataInfo>> call, Throwable t) {

            }
        });
    }

    private void fetchExerciseData(FitnessData<ExerciseDataInfo> fitnessData) {

        exerciseList = new ArrayList<>();

        List<ExerciseDataInfo> exerciseDataInfoList = fitnessData.getResults();

        for(ExerciseDataInfo exerciseDataInfo : exerciseDataInfoList) {

            Exercise exercise = new Exercise(exerciseDataInfo.getId(), exerciseDataInfo.getName(), exerciseDataInfo.getDescription());
            fitnessLogDao.insert(exercise);
            exerciseList.add(exercise);
        }

        refreshDisplay();
    }

    private void wireUpDisplay() {

        listView = findViewById(R.id.lv_exercise_list_data);
        Button backButton = findViewById(R.id.btn_exercise_list_back);

        backButton.setOnClickListener(v -> {
            Intent intent = DisplayExerciseActivity.intentFactory(this, mUserId);
            startActivity(intent);
        });

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

        dialog = new Dialog(this);

        View view = LayoutInflater.from(this).inflate(R.layout.operations, null);

        dialog.setContentView(view);

        TextView exerciseName = view.findViewById(R.id.tv_exercise_name);

        Exercise exercise = fetchSelectedExercise(position);

        if(exercise !=  null) {
            exerciseName.setText(exercise.toString());
        }

        if(position != -1) {
            dialog.show();
        }

        Button addExerciseButton = view.findViewById(R.id.btn_save_exercise);

        addExerciseButton.setEnabled(position != -1);

        addExerciseButton.setOnClickListener(v -> {
            processExerciseData(position, view);
            dialog.dismiss();
        });
    }

    private Exercise fetchSelectedExercise(int position) {

        String value = (String) listView.getItemAtPosition(position);

        return fitnessLogDao.getByExerciseName(value);
    }

    private void processExerciseData(int position, View view) {

        Exercise exercise = fetchSelectedExercise(position);

        if(exercise == null) {
            Toast.makeText(this, "Invalid Exercise fetched!", Toast.LENGTH_LONG).show();
        } else {

            EditText repetitions = view.findViewById(R.id.et_reps);
            EditText weight = view.findViewById(R.id.et_weight);

            EditText etWorkoutName = view.findViewById(R.id.et_workout_name);
            EditText etWorkoutDescription = view.findViewById(R.id.et_workout_description);

            int reps = Integer.parseInt(repetitions.getText().toString());
            int inputWeight = Integer.parseInt(weight.getText().toString());

            String workoutName = etWorkoutName.getText().toString();
            String workoutDescription = etWorkoutDescription.getText().toString();

            sendWorkoutData(exercise.getExerciseName(), inputWeight, reps, workoutName, workoutDescription);
        }
    }

    private void sendWorkoutData(String exerciseName, int inputWeight, int reps, String workoutName, String workoutDescription) {

        FitnessAPI fitnessAPI = ExternalProcess.getFitnessAPIInstance();

        WorkoutDataInfo workoutDataInfo = new WorkoutDataInfo(workoutName, workoutDescription);

        Call<WorkoutDataInfo> workoutInfo = fitnessAPI.sendWorkoutInfo(workoutDataInfo);

        workoutInfo.enqueue(new Callback<WorkoutDataInfo>() {
            @Override
            public void onResponse(Call<WorkoutDataInfo> call, Response<WorkoutDataInfo> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                WorkoutDataInfo dataInfo = response.body();

                if(dataInfo != null) {

                    FitnessLog fitnessLog = new FitnessLog(exerciseName, inputWeight, reps, mUserId,
                            dataInfo.getId(), workoutName, workoutDescription);

                    fitnessLogDao.insert(fitnessLog);
                }
            }

            @Override
            public void onFailure(Call<WorkoutDataInfo> call, @NonNull Throwable t) {

            }
        });
    }

    private void refreshDisplay() {

        if(exerciseList.isEmpty()) {
            exerciseList = fitnessLogDao.getAllExerciseList();
        }

        List<String> exercises = new ArrayList<>();
        for(Exercise exercise : exerciseList) {
            exercises.add(exercise.toString());
        }

        // Adding items to listview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.operational_list_view, R.id.operational_exercise_name, exercises);
        listView.setAdapter(adapter);

    }
}
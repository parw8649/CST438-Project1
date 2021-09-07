package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.Exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeleteExerciseActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project1.userIdKey";
    private FitnessLogDao fitnessLogDao;
    private int mUserId = -1;
    TextView textViewResult;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_exercise);

        exercises = new ArrayList<>();
        getUserDetails();
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
        Intent intent = new Intent(context, DeleteExerciseActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }

    private void getDatabase() {
        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
    }

    public void print() {
        System.out.println("Button clicked");
    }

    private void wireUpDisplay() {
        exercises = getAllExercises();
        String content = "";
        textViewResult = findViewById(R.id.text_view_result);
        for(int i = 0; i < exercises.size(); i++) {
            if(mUserId == exercises.get(i).getId()) {
                content += exercises.get(i).getExerciseInfo() + "\n";
            }
            textViewResult.append(content);
        }

//        listView = findViewById(R.id.lv_exercise_list_data);
//        Button backButton = findViewById(R.id.btn_exercise_list_back);
//
//        backButton.setOnClickListener(v -> {
//            Intent intent = MainActivity.intentFactory(this, mUserId);
//            startActivity(intent);
//        });
//
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            displayDialog( -1);
//            if (dialog != null) {
//                if (!dialog.isShowing()) {
//                    displayDialog(position);
//                } else {
//                    dialog.dismiss();
//                }
//            }
//        });
    }
}
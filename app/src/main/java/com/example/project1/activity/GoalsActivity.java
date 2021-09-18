package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.ActivityGoalLog;

import java.util.List;

public class GoalsActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project1.userIdKey";

    private FitnessLogDao fitnessLogDao;
    private int mUserId = -1;

    private ListView displayActivityGoals;
    private List<ActivityGoalLog> activityGoalLogs;
    private Button mDeleteButton;
    private TextView displayMsg;

    private ArrayAdapter<ActivityGoalLog> adapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        getDatabase();
        getUserDetails();
        wireUpDisplay();
        refreshDisplay();
    }

    private void getDatabase() {
        fitnessLogDao = AppDatabase.getDatabaseInstance(this).getFitnessLogDao();
    }

    private void getUserDetails() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);
    }

    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context, GoalsActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }

    private void wireUpDisplay() {

        displayMsg = findViewById(R.id.goalsMsgDisplay);
        displayActivityGoals = findViewById(R.id.goalsLogDisplay);

        Button backButton = findViewById(R.id.btn_activity_goals_main_back);

        backButton.setOnClickListener(v -> finish());

        View.OnClickListener listenerDel = v -> {
            /** Getting the checked items from the listview */
            SparseBooleanArray checkedItemPositions = displayActivityGoals.getCheckedItemPositions();
            int itemCount = displayActivityGoals.getCount();

            for (int i = itemCount - 1; i >= 0; i--) {
                if (checkedItemPositions.get(i)) {
                    ActivityGoalLog activityGoalLog = activityGoalLogs.get(i);
                    adapter.remove(activityGoalLog);
                    fitnessLogDao.delete(activityGoalLog);
                }
            }

            checkedItemPositions.clear();
            adapter.notifyDataSetChanged();
            refreshDisplay();
        };

        Button addActivityGoal = findViewById(R.id.addNewGoalLog);
        mDeleteButton = findViewById(R.id.deleteGoalsButton);

        addActivityGoal.setOnClickListener(v -> openActivityGoalPopupDialogBox());

        mDeleteButton.setOnClickListener(listenerDel);
    }

    private void openActivityGoalPopupDialogBox() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.add_activity_goals_popup, null);

        processActivityGoalData(popupView);

        dialogBuilder.setView(popupView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void processActivityGoalData(View view) {

        EditText goalDescription = view.findViewById(R.id.et_activity_goals_description);

        Button saveActivityGoal = view.findViewById(R.id.btn_save_activity_goals);

        saveActivityGoal.setOnClickListener(v -> {

            if(goalDescription != null && goalDescription.getText() != null) {

                String goalInfo = goalDescription.getText().toString();

                ActivityGoalLog activityGoalLog = new ActivityGoalLog(goalInfo, mUserId);
                fitnessLogDao.insert(activityGoalLog);

                alertDialog.dismiss();
                refreshDisplay();
            } else {
                Toast.makeText(this, "Invalid Activity goal description", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refreshDisplay() {

        activityGoalLogs = fitnessLogDao.getActivityGoalLogsByUserId(mUserId);

        if(activityGoalLogs.isEmpty()) {
            mDeleteButton.setVisibility(View.INVISIBLE);
            displayActivityGoals.setVisibility(View.INVISIBLE);
            displayMsg.setVisibility(View.VISIBLE);
            displayMsg.setText(R.string.noActivityGoalLogs);

        } else {
            mDeleteButton.setVisibility(View.VISIBLE);
            displayActivityGoals.setVisibility(View.VISIBLE);
            displayMsg.setVisibility(View.INVISIBLE);
        }

        // Adding items to listview
        adapter = new ArrayAdapter<>(this, R.layout.display_list_view, R.id.workout_name, activityGoalLogs);
        displayActivityGoals.setAdapter(adapter);
    }
}
package com.example.project1.activity;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;
import com.example.project1.model.HeartRateLog;
import com.example.project1.model.User;

import java.util.List;

public class HeartRateActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project1.userIdKey";

    private FitnessLogDao fitnessLogDao;
    private int mUserId = -1;

    private ListView displayHeartRate;
    private List<HeartRateLog> heartRateLogs;
    private Button mDeleteButton;
    private TextView displayMsg;

    private ArrayAdapter<HeartRateLog> adapter;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

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

    private void wireUpDisplay() {

        displayMsg = findViewById(R.id.heartRateMsgDisplay);
        displayHeartRate = findViewById(R.id.heartRateLogDisplay);

        Button backButton = findViewById(R.id.btn_heart_rate_main_back);

        backButton.setOnClickListener(v -> finish());

        View.OnClickListener listenerDel = v -> {
            /** Getting the checked items from the listview */
            SparseBooleanArray checkedItemPositions = displayHeartRate.getCheckedItemPositions();
            int itemCount = displayHeartRate.getCount();

            for (int i = itemCount - 1; i >= 0; i--) {
                if (checkedItemPositions.get(i)) {
                    HeartRateLog heartRateLog = heartRateLogs.get(i);
                    adapter.remove(heartRateLog);
                    fitnessLogDao.delete(heartRateLog);
                }
            }

            checkedItemPositions.clear();
            adapter.notifyDataSetChanged();
            refreshDisplay();
        };

        Button addHeartRateInfo = findViewById(R.id.addNewHeartRateLog);
        mDeleteButton = findViewById(R.id.deleteHeartRateButton);

        addHeartRateInfo.setOnClickListener(v -> openHeartRatePopupDialogBox());

        mDeleteButton.setOnClickListener(listenerDel);
    }

    private void openHeartRatePopupDialogBox() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.add_heart_rate_popup, null);

        processHeartRateData(popupView);

        dialogBuilder.setView(popupView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void processHeartRateData(View view) {

        EditText wristMonitoring = view.findViewById(R.id.et_wrist_monitoring);
        EditText chestStrapMonitoring = view.findViewById(R.id.et_chest_strap_monitoring);

        Button saveHeartRate = view.findViewById(R.id.btn_save_heart_rate);

        saveHeartRate.setOnClickListener(v -> {

            if(wristMonitoring != null && wristMonitoring.getText() != null
                    && chestStrapMonitoring != null && chestStrapMonitoring.getText() != null) {

                int wristInfo = Integer.parseInt(wristMonitoring.getText().toString());
                int chestStrapInfo = Integer.parseInt(chestStrapMonitoring.getText().toString());

                HeartRateLog heartRateLog = new HeartRateLog(wristInfo, chestStrapInfo, mUserId);
                fitnessLogDao.insert(heartRateLog);

                alertDialog.dismiss();
                refreshDisplay();
            } else {
                Toast.makeText(this, "Invalid heart rate details", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refreshDisplay() {

        heartRateLogs = fitnessLogDao.getHeartRateLogsByUserId(mUserId);

        if(heartRateLogs.isEmpty()) {
            mDeleteButton.setVisibility(View.INVISIBLE);
            displayHeartRate.setVisibility(View.INVISIBLE);
            displayMsg.setVisibility(View.VISIBLE);
            displayMsg.setText(R.string.noHeartRateLogs);

        } else {
            mDeleteButton.setVisibility(View.VISIBLE);
            displayHeartRate.setVisibility(View.VISIBLE);
            displayMsg.setVisibility(View.INVISIBLE);
        }

        // Adding items to listview
        adapter = new ArrayAdapter<>(this, R.layout.display_list_view, R.id.workout_name, heartRateLogs);
        displayHeartRate.setAdapter(adapter);
    }

    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context, HeartRateActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }
}
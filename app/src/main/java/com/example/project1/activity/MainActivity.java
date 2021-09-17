package com.example.project1.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.model.FitnessLog;
import com.example.project1.R;
import com.example.project1.model.User;
import com.example.project1.db.AppDatabase;
import com.example.project1.db.FitnessLogDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.project1.userIdKey";
    private static final String PREFS = "com.example.project1.prefs";
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
        setContentView(R.layout.activity_main);

        getDatabase();
        wireUpDisplay();
        checkForUser();
        addUserToPreferences(mUserId);
        loginUser(mUserId);

        refreshDisplay();
    }

    private void loginUser(int mUserId) {
        mUser = fitnessLogDao.getUserByUserId(mUserId);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        logoutUser();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mUser != null) {
            MenuItem item = menu.findItem(R.id.userMenuLogout);
            item.setTitle(mUser.getUsername());
        }

        return super.onPrepareOptionsMenu(menu);
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

        List<User> users = fitnessLogDao.getAllUsers();
        if(users.size() <= 0) {
            User defaultUser = new User("user1", "user123");
            User altUser = new User("user2", "user456");
            fitnessLogDao.insert(defaultUser, altUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
    

    private void wireUpDisplay() {

        displayMsg = findViewById(R.id.fitnessMsgDisplay);
        mMainDisplay = findViewById(R.id.mainGymLogDisplay);

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

        Button mSubmitButton = findViewById(R.id.mainSubmitButton);
        mDeleteButton = findViewById(R.id.deleteButton);

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
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }

    private void addUserToPreferences(int mUserId) {
        if(mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, mUserId);
        editor.apply();
    }

    private void logoutUser() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            MainActivity.this.clearUserFromIntent();
            MainActivity.this.clearUserFromPrefs();
            mUserId = -1;
            MainActivity.this.checkForUser();
        });

        alertBuilder.setNegativeButton(R.string.no, (dialogInterface, i) -> {

        });

        alertBuilder.setOnCancelListener(dialog -> {

        });

        AlertDialog goodAlert = alertBuilder.create();
        goodAlert.show();
    }

    private void clearUserFromPrefs() {
        addUserToPreferences(-1);
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }
}
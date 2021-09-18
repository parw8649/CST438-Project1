package com.example.project1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project1.R;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity {

    //variables
    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        //assign variables
        editText = findViewById(R.id.edit_text);
        btAdd =  findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);


        //initialize database
        database = RoomDB.getInstance(this);
        //store database value in data list
        dataList = database.mainDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layour manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        adapter = new MainAdapter(GoalsActivity.this,dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get string from edittext
                String sText = editText.getText().toString().trim();

                //check condition
                if (!sText.equals("")){
                    //when text is not empty
                    //Initialize main data
                    MainData data = new MainData();
                    //set text on main data
                    data.setText(sText);
                    //insert text in database
                    database.mainDao().insert(data);
                    // cleaar edit text
                    editText.setText("");
                    //notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete all data from database
                database.mainDao().reset(dataList);
                //Notify whe data is deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_name:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
            MenuItem item = menu.findItem(R.id.user_name);
            item.setTitle("Home");

        return super.onPrepareOptionsMenu(menu);
    }



    public static Intent intentFactory(Context context, int mUserId) {
        Intent intent = new Intent(context, GoalsActivity.class);
        //intent.putExtra(USER_ID_KEY, mUserId);
        return intent;
    }
}
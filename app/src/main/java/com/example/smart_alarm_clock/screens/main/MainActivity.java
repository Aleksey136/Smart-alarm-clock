package com.example.smart_alarm_clock.screens.main;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.screens.subordinate.EditAlarmClockActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    Button buttonAddAlarmClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.ListOfAlarmClock);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        buttonAddAlarmClock = findViewById(R.id.ButtonAddAlarmClock);

        buttonAddAlarmClock.setOnClickListener(view -> {
            EditAlarmClockActivity.start(MainActivity.this,null);
        });
    }

}
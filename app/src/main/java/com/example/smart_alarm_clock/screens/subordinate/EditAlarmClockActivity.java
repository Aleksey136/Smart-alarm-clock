package com.example.smart_alarm_clock.screens.subordinate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class EditAlarmClockActivity extends AppCompatActivity {
    private static final String extraAlarmClock = "ChangeAlarmClockActivity.extraAlarmClock";

    private ListOfAlarmClock listOfAlarmClock;

    private Button buttonSaveEditAlarmClock;
    private Button buttonSaveEditAlarmClock2;
    private Button buttonEditAlarmClock;

    private EditText editText;

    public static void start(Activity caller, ListOfAlarmClock listOfAlarmClock) {
        Intent intent = new Intent(caller, EditAlarmClockActivity.class);
        if (listOfAlarmClock != null) {
            intent.putExtra(extraAlarmClock,listOfAlarmClock);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_alarm_clock_activity);

        editText=findViewById(R.id.descriptionAlarmClock);
        buttonSaveEditAlarmClock = findViewById(R.id.ButtonSaveEditAlarmClock);
        buttonEditAlarmClock = findViewById(R.id.ButtonEditAlarmClock);

        Calendar calendar = Calendar.getInstance();

        buttonSaveEditAlarmClock.setOnClickListener(view -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Выберите время")
                    .build();
            materialTimePicker.addOnPositiveButtonClickListener(view1 -> {
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getAlarmInfoPendingIntern());
            });
            materialTimePicker.show(getSupportFragmentManager(), "tag");
        });

        if (getIntent().hasExtra(extraAlarmClock)){
            listOfAlarmClock = getIntent().getParcelableExtra(extraAlarmClock);
            editText.setText(listOfAlarmClock.textAlarmClock);
            buttonEditAlarmClock.setOnClickListener(view -> {
                listOfAlarmClock.textAlarmClock=editText.getText().toString();
//                listOfAlarmClock.timeAlarmClockHour=12;
            });
        } else {
            listOfAlarmClock = new ListOfAlarmClock();
        }

    }
    private PendingIntent getAlarmInfoPendingIntern() {
        Intent alarmInfoIntern = new Intent(this, EditAlarmClockActivity.class);
        alarmInfoIntern.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmInfoIntern, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

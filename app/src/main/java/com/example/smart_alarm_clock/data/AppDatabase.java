package com.example.smart_alarm_clock.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.smart_alarm_clock.model.ListOfAlarmClock;
import com.example.smart_alarm_clock.model.ListOfLateness;

@Database(entities = {ListOfAlarmClock.class, ListOfLateness.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ListOfAlarmClockDao listOfAlarmClockDao();

    public abstract ListOfLatenessDao listOfLatenessDao();
}

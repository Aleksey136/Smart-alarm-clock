package com.example.smart_alarm_clock.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smart_alarm_clock.model.ListOfAlarmClock;

import java.util.List;

@Dao
public interface ListOfAlarmClockDao {
    @Query("SELECT * FROM ListOfAlarmClock")
    List<ListOfAlarmClock> getAll();

    @Query("SELECT * FROM ListOfAlarmClock WHERE alarmClock_ID = :alarmClock_ID")
    ListOfAlarmClock getById(int alarmClock_ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ListOfAlarmClock listOfAlarmClock);

    @Update
    void update(ListOfAlarmClock listOfAlarmClock);

    @Delete
    void delete(ListOfAlarmClock listOfAlarmClock);
}

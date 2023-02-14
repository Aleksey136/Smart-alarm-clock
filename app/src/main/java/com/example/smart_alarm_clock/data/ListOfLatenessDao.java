package com.example.smart_alarm_clock.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smart_alarm_clock.model.ListOfLateness;

import java.util.List;

@Dao
public interface ListOfLatenessDao {
    @Query("SELECT * FROM ListOfLateness")
    List<ListOfLateness> getAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(ListOfLateness listOfLateness);

    @Update
    void update(ListOfLateness listOfLateness);

    @Delete
    void delete(ListOfLateness listOfLateness);
}
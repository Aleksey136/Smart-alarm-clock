package com.example.smart_alarm_clock;

import android.app.Application;

import androidx.room.Room;

import com.example.smart_alarm_clock.data.AppDatabase;
import com.example.smart_alarm_clock.data.ListOfAlarmClockDao;
import com.example.smart_alarm_clock.data.ListOfLatenessDao;

public class App extends Application {

private AppDatabase database;
    private ListOfAlarmClockDao listOfAlarmClockDao;
    private ListOfLatenessDao listOfLatenessDao;

    private static App instance;                                        //singleton - стоит ли использовать?

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-db")
                .allowMainThreadQueries()                           //Главный поток -> Бэк поток?
                .build();

        listOfAlarmClockDao = database.listOfAlarmClockDao();
        listOfLatenessDao = database.listOfLatenessDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public ListOfAlarmClockDao getListOfAlarmClockDao() {
        return listOfAlarmClockDao;
    }

    public void setListOfAlarmClockDao(ListOfAlarmClockDao listOfAlarmClockDao) {
        this.listOfAlarmClockDao = listOfAlarmClockDao;
    }

    public ListOfLatenessDao getListOfLatenessDao() {
        return listOfLatenessDao;
    }

    public void setListOfLatenessDao(ListOfLatenessDao listOfLatenessDao) {
        this.listOfLatenessDao = listOfLatenessDao;
    }
}

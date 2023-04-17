package com.example.smart_alarm_clock.screens.main;

import androidx.lifecycle.ViewModel;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;

import java.util.List;

public class MainViewModel extends ViewModel {
    private List<ListOfAlarmClock> listOfAlarmClocks = App.getInstance().getListOfAlarmClockDao().getAll();

    public List<ListOfAlarmClock> getListOfAlarmClocks() {
        return listOfAlarmClocks;
    }
}

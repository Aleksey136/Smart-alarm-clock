package com.example.smart_alarm_clock.screens.activeAlarmClockFragment;

import androidx.lifecycle.ViewModel;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;
import com.example.smart_alarm_clock.model.ListOfLateness;

import java.util.List;

public class ActiveAlarmClockFragmentViewModel extends ViewModel {
    public List<ListOfAlarmClock> getListOfAlarmClock() {
        return App.getInstance().getListOfAlarmClockDao().getAll();
    }

    public ListOfAlarmClock getAlarmClockById(int alarmClockId){
        return App.getInstance().getListOfAlarmClockDao().getById(alarmClockId);
    }

    public void saveEditAlarmClock(ListOfAlarmClock listOfAlarmClock) {
        App.getInstance().getListOfAlarmClockDao().update(listOfAlarmClock);
    }

    public void insertNewLateness(ListOfLateness lateness) {
        App.getInstance().getListOfLatenessDao().insert(lateness);
    }
}

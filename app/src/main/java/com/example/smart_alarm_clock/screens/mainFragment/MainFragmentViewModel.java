package com.example.smart_alarm_clock.screens.mainFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;
import com.example.smart_alarm_clock.model.ListOfLateness;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private LiveData<List<ListOfAlarmClock>> listLiveData = App.getInstance().getListOfAlarmClockDao().getAllLiveData();

    public LiveData<List<ListOfAlarmClock>> getListLiveData() {
        return listLiveData;
    }

    public ListOfAlarmClock getAlarmClockById(int alarmClockId){
        return App.getInstance().getListOfAlarmClockDao().getById(alarmClockId);
    }

    public void updateAlarmClock(ListOfAlarmClock listOfAlarmClock) {
        App.getInstance().getListOfAlarmClockDao().update(listOfAlarmClock);
    }

    public List<ListOfLateness> getListOfLateness() {
        return App.getInstance().getListOfLatenessDao().getAll();
    }
}

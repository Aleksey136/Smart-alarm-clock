package com.example.smart_alarm_clock.screens.editListOfAlarmClockFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;

import java.util.List;

public class EditListOfAlarmClockFragmentViewModel extends ViewModel {
    private LiveData<List<ListOfAlarmClock>> listLiveData = App.getInstance().getListOfAlarmClockDao().getAllLiveData();

    public ListOfAlarmClock getAlarmClockById(int alarmClockId){
        return App.getInstance().getListOfAlarmClockDao().getById(alarmClockId);
    }

    public void saveNewAlarmClock(ListOfAlarmClock listOfAlarmClock) {
        App.getInstance().getListOfAlarmClockDao().insert(listOfAlarmClock);
    }

    public void saveEditAlarmClock(ListOfAlarmClock listOfAlarmClock) {
        App.getInstance().getListOfAlarmClockDao().update(listOfAlarmClock);
    }

    public void deleteAlarmClock(ListOfAlarmClock listOfAlarmClock) {
        App.getInstance().getListOfAlarmClockDao().delete(listOfAlarmClock);
    }
}

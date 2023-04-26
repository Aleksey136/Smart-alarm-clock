package com.example.smart_alarm_clock.screens.listOfLatenessFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.model.ListOfLateness;

import java.util.List;

public class ListOfLatenessFragmentViewModel extends ViewModel {
    private LiveData<List<ListOfLateness>> listLiveData = App.getInstance().getListOfLatenessDao().getAllLiveData();

    public LiveData<List<ListOfLateness>> getListLiveData() {
        return listLiveData;
    }

    public List<ListOfLateness> getListLateness() {
        return App.getInstance().getListOfLatenessDao().getAll();
    }

    public void updateLateness(ListOfLateness lateness) {
        App.getInstance().getListOfLatenessDao().update(lateness);
    }
}

package com.example.smart_alarm_clock.screens.editListOfLatenessFragment;

import androidx.lifecycle.ViewModel;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.model.ListOfLateness;

public class EditListOfLatenessFragmentViewModel extends ViewModel {
    public ListOfLateness getLatenessById(int latenessId){
        return App.getInstance().getListOfLatenessDao().getById(latenessId);
    }

    public void saveEditLateness(ListOfLateness listOfLateness) {
        App.getInstance().getListOfLatenessDao().update(listOfLateness);
    }

    public void deleteLateness(ListOfLateness listOfLateness) {
        App.getInstance().getListOfLatenessDao().delete(listOfLateness);
    }
}

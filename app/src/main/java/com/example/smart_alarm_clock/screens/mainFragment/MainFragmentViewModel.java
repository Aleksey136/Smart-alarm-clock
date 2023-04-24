package com.example.smart_alarm_clock.screens.mainFragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;

import java.util.List;

public class MainFragmentViewModel extends ViewModel {

    private MutableLiveData<List<ListOfAlarmClock>> listMutableLiveData = (MutableLiveData<List<ListOfAlarmClock>>) App.getInstance().getListOfAlarmClockDao().getAllLiveData();

    public MutableLiveData<List<ListOfAlarmClock>> getListMutableLiveData() {
        return listMutableLiveData;
    }

//    public void setListOfAlarmClock(ListOfAlarmClock listOfAlarmClock) {
//        App.getInstance().getListOfAlarmClockDao().update(listOfAlarmClock);
//    }

//    private ListOfAlarmClock alarmClock;
//    private  MutableLiveData<List<ListOfAlarmClock>> listOfAlarmClock;
//
//    public MainFragmentViewModel() {
//        alarmClock = new ListOfAlarmClock();
//        listOfAlarmClock = new MutableLiveData<>();
//    }
//
//    public LiveData<List<ListOfAlarmClock>> getAlarmClockList() {
//        if (alarmClock. == null) {
//            loadAlarmClockList();
//        }
//        return alarmClock;
//    }
//
//    public void addUser(ListOfAlarmClock alarmClock) {
//        alarmClock..addUser(user);
//        loadAlarmClockList();
//    }
//
//    public void updateUser(ListOfAlarmClock alarmClock) {
//        mUserRepo.updateUser(user);
//        loadAlarmClockList();
//    }
//
//    public void deleteUser(ListOfAlarmClock alarmClock) {
//        mUserRepo.deleteUser(user);
//        loadAlarmClockList();
//    }
//
//    private void loadAlarmClockList() {
//        List<ListOfAlarmClock> listOfAlarmClock1 = ;
//        mUserList.setValue(userList);
//    }
}

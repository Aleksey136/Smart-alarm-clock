package com.example.smart_alarm_clock;

import androidx.lifecycle.LiveData;

import com.example.smart_alarm_clock.data.ListOfAlarmClockDao;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class UnitTest2 {
    @Test
    public void testDao2() {
        // Creating mock ListOfLateness instances for testing
        ListOfAlarmClock alarmClock1 = new ListOfAlarmClock();
        alarmClock1.alarmClock_ID=1;
        alarmClock1.textAlarmClock="It's test";
        alarmClock1.timeAlarmClockHour=1;
        alarmClock1.timeAlarmClockMinute=23;
        alarmClock1.activeAlarmClock=false;
        alarmClock1.activeRepeatAlarmClock=false;
        ListOfAlarmClock alarmClock2 = new ListOfAlarmClock();
        alarmClock2.alarmClock_ID=2;
        alarmClock2.textAlarmClock="It's for test";
        alarmClock2.timeAlarmClockHour=4;
        alarmClock2.timeAlarmClockMinute=42;
        alarmClock2.activeAlarmClock=true;
        alarmClock2.activeRepeatAlarmClock=true;

        // Creating mock LiveData<List<ListOfAlarmClock>> for testing
        LiveData<List<ListOfAlarmClock>> mockLiveData = Mockito.mock(LiveData.class);

        // Creating mock ListOfAlarmClockDao instance for testing
        ListOfAlarmClockDao mockDao = Mockito.mock(ListOfAlarmClockDao.class);

        // Setting up the mock objects
        Mockito.when(mockLiveData.getValue()).thenReturn(Arrays.asList(alarmClock1, alarmClock2));
        Mockito.when(mockDao.getAllLiveData()).thenReturn(mockLiveData);
        Mockito.when(mockDao.getAll()).thenReturn(Arrays.asList(alarmClock1, alarmClock2));

        // Testing the update() method
        ListOfAlarmClock updatedAlarmClock = new ListOfAlarmClock();
        updatedAlarmClock.alarmClock_ID=1;
        updatedAlarmClock.textAlarmClock="It's test2";
        updatedAlarmClock.timeAlarmClockHour=1;
        updatedAlarmClock.timeAlarmClockMinute=23;
        updatedAlarmClock.activeAlarmClock=false;
        updatedAlarmClock.activeRepeatAlarmClock=false;
        mockDao.update(updatedAlarmClock);
        Mockito.verify(mockDao).update(updatedAlarmClock);
        Mockito.when(mockDao.getAll()).thenReturn(Arrays.asList(updatedAlarmClock, alarmClock2));
    }
}

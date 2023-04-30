package com.example.smart_alarm_clock;

import androidx.lifecycle.LiveData;

import com.example.smart_alarm_clock.data.ListOfLatenessDao;
import com.example.smart_alarm_clock.model.ListOfLateness;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;


public class UnitTest {
    @Test
    public void testDao1() {
        // Creating mock ListOfLateness instances for testing
        ListOfLateness lateness1 = new ListOfLateness();
        lateness1.lateness_ID=1;
        lateness1.textLateness="It's test";
        lateness1.timeLatenessHour=1;
        lateness1.timeLatenessMinute=23;
        lateness1.technologyLateness=false;
        ListOfLateness lateness2 = new ListOfLateness();
        lateness2.lateness_ID=2;
        lateness2.textLateness="It's for test";
        lateness2.timeLatenessHour=4;
        lateness2.timeLatenessMinute=42;
        lateness2.technologyLateness=true;

        // Creating mock LiveData<List<ListOfLateness>> for testing
        LiveData<List<ListOfLateness>> mockLiveData = Mockito.mock(LiveData.class);

        // Creating mock ListOfLatenessDao instance for testing
        ListOfLatenessDao mockDao = Mockito.mock(ListOfLatenessDao.class);

        // Setting up the mock objects
        Mockito.when(mockLiveData.getValue()).thenReturn(Arrays.asList(lateness1, lateness2));
        Mockito.when(mockDao.getAllLiveData()).thenReturn(mockLiveData);
        Mockito.when(mockDao.getAll()).thenReturn(Arrays.asList(lateness1, lateness2));

        // Testing the update() method
        ListOfLateness updatedLateness = new ListOfLateness();
        updatedLateness.lateness_ID=1;
        updatedLateness.textLateness="It's test2";
        updatedLateness.timeLatenessHour=1;
        updatedLateness.timeLatenessMinute=23;
        updatedLateness.technologyLateness=false;
        mockDao.update(updatedLateness);
        Mockito.verify(mockDao).update(updatedLateness);
        Mockito.when(mockDao.getAll()).thenReturn(Arrays.asList(updatedLateness, lateness2));
    }
}

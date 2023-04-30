package com.example.smart_alarm_clock.screens.mainFragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.databinding.FragmentMainBinding;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;
import com.example.smart_alarm_clock.model.ListOfLateness;
import com.example.smart_alarm_clock.screens.mainActivity.MainActivity;

import java.util.Calendar;
import java.util.List;

public class MainFragment extends Fragment implements AdapterForListOfAlarmClock.OnListener {

    private FragmentMainBinding binding;

    //При нажатии на элемент списка запускается другой фрагмент
    @Override
    public void onItemClick(int position, int alarmClockID) {
        MainFragmentDirections.ActionMainFragmentToEditListOfAlarmClockFragment directions;
        directions = MainFragmentDirections.actionMainFragmentToEditListOfAlarmClockFragment(alarmClockID);

        NavHostFragment.findNavController(this).navigate(directions);
    }

    //Установка будильника на нужное время с учетом времени опоздания
    @Override
    public void onActiveAlarmClockManager(ListOfAlarmClock listOfAlarmClock, int delayTechnology) {
        int delayHour = delayTechnology/60;
        int delayMinute = delayTechnology%60;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MINUTE, listOfAlarmClock.timeAlarmClockMinute - delayMinute);
        calendar.set(Calendar.HOUR_OF_DAY, listOfAlarmClock.timeAlarmClockHour - delayHour);
        if (calendar.get(Calendar.HOUR_OF_DAY)<Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE)+1);
        else if (calendar.get(Calendar.HOUR_OF_DAY)==Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.MINUTE)<Calendar.getInstance().get(Calendar.MINUTE))
            calendar.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE)+1);
        setAlarmClockManager(calendar, listOfAlarmClock.alarmClock_ID);
    }

    //Выключение будильника
    @Override
    public void onDisableAlarmClockManager(ListOfAlarmClock listOfAlarmClock) {
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getAlarmActionPendingIntent(listOfAlarmClock.alarmClock_ID));
    }

    @Override
    public void onAlarmClockUpdate(ListOfAlarmClock listOfAlarmClock) {
        MainFragmentViewModel mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        mainFragmentViewModel.updateAlarmClock(listOfAlarmClock);
    }

    //Ищет среднее время опоздания
    @Override
    public int onSearchDelayAlarmClock() {
        MainFragmentViewModel mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        List<ListOfLateness> listOfLatenessList = mainFragmentViewModel.getListOfLateness();
        if (!listOfLatenessList.isEmpty()) {
            if (listOfLatenessList.get(0).technologyLateness) {
                int sumCount = 0;
                int quantityCount = 0;
                for (ListOfLateness lateness : listOfLatenessList) {
                    sumCount += lateness.timeLatenessHour * 60 + lateness.timeLatenessMinute;
                    quantityCount++;
                }
                return (sumCount / quantityCount);
            } else {
                return 0;
            }
        }
        else {
            return 0;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false);
        binding.ListOfAlarmClock.setLayoutManager(linearLayoutManager);

        AdapterForListOfAlarmClock adapter = new AdapterForListOfAlarmClock();
        binding.ListOfAlarmClock.setAdapter(adapter);
        adapter.OnListener(this);

        MainFragmentViewModel mainFragmentViewModel =
                new ViewModelProvider(this).get(MainFragmentViewModel.class);
        mainFragmentViewModel.getListLiveData().observe(requireActivity(), new Observer<List<ListOfAlarmClock>>() {
            @Override
            public void onChanged(List<ListOfAlarmClock> listOfAlarmClocks) {
                adapter.setItems(listOfAlarmClocks);
            }
        });

        binding.ButtonAddAlarmClock.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(MainFragmentDirections.actionMainFragmentToEditListOfAlarmClockFragment(-1));
        });

        binding.ButtonToListOfLateness.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(MainFragmentDirections.actionMainFragmentToListOfLatenessFragment());
        });
    }

    private PendingIntent getAlarmInfoPendingIntern() {
        return new NavDeepLinkBuilder(requireContext())
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.mainFragment)
                .createPendingIntent();
    }

    private PendingIntent getAlarmActionPendingIntent(int alarmClockId) {
        Bundle bundle = new Bundle();
        bundle.putInt("alarmClockId", alarmClockId);
        bundle.putBoolean("repeatAlarmClock", false);
        NavDeepLinkBuilder builder = new NavDeepLinkBuilder(requireActivity())
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.activeAlarmClockFragment)
                .setArguments(bundle);
        Intent alarmInfoIntern = builder.createTaskStackBuilder().getIntents()[0];
        alarmInfoIntern.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(requireActivity(), alarmClockId , alarmInfoIntern, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setAlarmClockManager(Calendar calendar, int alarmClock_ID){
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        AlarmManager.AlarmClockInfo alarmClockInfo =
                new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getAlarmInfoPendingIntern());
        alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent(alarmClock_ID));
    }
}
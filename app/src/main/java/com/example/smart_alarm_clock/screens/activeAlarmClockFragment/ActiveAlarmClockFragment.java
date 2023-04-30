package com.example.smart_alarm_clock.screens.activeAlarmClockFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.databinding.FragmentActiveAlarmClockBinding;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;
import com.example.smart_alarm_clock.model.ListOfLateness;
import com.example.smart_alarm_clock.screens.mainActivity.MainActivity;

import java.util.Calendar;

public class ActiveAlarmClockFragment extends Fragment {
    private FragmentActiveAlarmClockBinding binding;
    private Ringtone ringtone;
    private MediaPlayer player;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentActiveAlarmClockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActiveAlarmClockFragmentViewModel activeAlarmClockFragmentViewModel = new ViewModelProvider(this).get(ActiveAlarmClockFragmentViewModel.class);

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getContext(), notificationUri);
        player = MediaPlayer.create(requireContext(), notificationUri);
        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(getContext(), notificationUri);
            player = MediaPlayer.create(requireContext(), notificationUri);
        }
        if (ringtone != null) {
            player.setLooping(true);
            player.start();
        }

        assert getArguments() != null;
        int alarmClockId = getArguments().getInt("alarmClockId");
        boolean repeatAlarmClock = getArguments().getBoolean("repeatAlarmClock");

        binding.DisableAlarmClock.setOnClickListener(v -> {
            if (ringtone != null) {
                player.stop();

                //Добавление опоздания при отложенном будильнике
                if (repeatAlarmClock) {
                    Calendar calendar = Calendar.getInstance();
                    ListOfAlarmClock alarmClock = activeAlarmClockFragmentViewModel.getAlarmClockById(alarmClockId);
                    ListOfLateness lateness = new ListOfLateness();
                    int timeHour = calendar.get(Calendar.HOUR_OF_DAY) - alarmClock.timeAlarmClockHour;
                    int timeMinute = calendar.get(Calendar.MINUTE) - alarmClock.timeAlarmClockMinute;
                    lateness.timeLatenessHour = timeHour;
                    lateness.timeLatenessMinute = timeMinute;
                    activeAlarmClockFragmentViewModel.insertNewLateness(lateness);
                }

                //Отображение пользователю выключенного будильника
                ListOfAlarmClock listOfAlarmClock = activeAlarmClockFragmentViewModel.getAlarmClockById(alarmClockId);
                listOfAlarmClock.activeAlarmClock = false;
                activeAlarmClockFragmentViewModel.saveEditAlarmClock(listOfAlarmClock);

                NavHostFragment.findNavController(this)
                        .navigate(R.id.mainFragment);
            }
        });
        binding.ChangeTimeAlarmClock.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
            setAlarmClockManager(calendar, alarmClockId);

            //Отображение пользователю включенного будильника, но являющегося отложенным
            ListOfAlarmClock listOfAlarmClock = activeAlarmClockFragmentViewModel.getAlarmClockById(alarmClockId);
            listOfAlarmClock.activeAlarmClock = true;
            listOfAlarmClock.activeRepeatAlarmClock = true;
            activeAlarmClockFragmentViewModel.saveEditAlarmClock(listOfAlarmClock);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.mainFragment);
        });
    }

    @Override
    public void onDestroyView() {
        if (ringtone != null && player.isPlaying()) {
            player.stop();
        }

        super.onDestroyView();
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
        bundle.putBoolean("repeatAlarmClock", true);
        NavDeepLinkBuilder builder = new NavDeepLinkBuilder(requireActivity())
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.activeAlarmClockFragment)
                .setArguments(bundle);
        Intent alarmInfoIntern = builder.createTaskStackBuilder().getIntents()[0];
        alarmInfoIntern.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(requireActivity(), alarmClockId, alarmInfoIntern, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setAlarmClockManager(Calendar calendar, int alarmClockId) {
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        AlarmManager.AlarmClockInfo alarmClockInfo =
                new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getAlarmInfoPendingIntern());
        alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent(alarmClockId));
    }
}

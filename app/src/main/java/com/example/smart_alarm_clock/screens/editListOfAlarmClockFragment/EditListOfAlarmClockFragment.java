package com.example.smart_alarm_clock.screens.editListOfAlarmClockFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.databinding.FragmentEditListAlarmClockBinding;
import com.example.smart_alarm_clock.screens.mainActivity.MainActivity;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class EditListOfAlarmClockFragment extends Fragment {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private FragmentEditListAlarmClockBinding binding;
    private static final String EXTRA_NOTE = "EditListOfAlarmClockFragment.EXTRA_NOTE";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditListAlarmClockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();

        binding.ButtonEditListOfAlarmClock.setOnClickListener(v -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Выберите время")
                    .build();
            materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());
            });
            materialTimePicker.addOnDismissListener(v2 -> {
                binding.ButtonEditListOfAlarmClock.setText(simpleDateFormat.format(calendar.getTime()));
            });
            materialTimePicker.show(getChildFragmentManager(),"TAG");
        });
        binding.ButtonSaveEditAlarmClock.setOnClickListener(v ->{
            AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
            AlarmManager.AlarmClockInfo alarmClockInfo =
                    new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getAlarmInfoPendingIntern());

            alarmManager.setAlarmClock(alarmClockInfo, new NavDeepLinkBuilder(requireContext())
                    .setComponentName(MainActivity.class)
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.activeAlarmClockFragment)
                    .createPendingIntent());
            Toast.makeText(getContext(), "Будильник на " + simpleDateFormat
                    .format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.mainFragment);
        });


    }

    private PendingIntent getAlarmInfoPendingIntern() {
        Intent alarmInfoIntern = new Intent(requireContext(), MainActivity.class);
        alarmInfoIntern.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(requireContext(), 0, alarmInfoIntern, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

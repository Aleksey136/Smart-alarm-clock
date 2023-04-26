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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.databinding.FragmentEditListAlarmClockBinding;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;
import com.example.smart_alarm_clock.screens.mainActivity.MainActivity;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class EditListOfAlarmClockFragment extends Fragment {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private FragmentEditListAlarmClockBinding binding;

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

        ListOfAlarmClock listOfAlarmClock;

        EditListOfAlarmClockFragmentViewModel editListOfAlarmClockFragmentViewModel = new ViewModelProvider(this).get(EditListOfAlarmClockFragmentViewModel.class);

        int alarmClockId = EditListOfAlarmClockFragmentArgs.fromBundle(requireArguments()).getAlarmClockId();

        Calendar calendar = Calendar.getInstance();

        if (alarmClockId==-1) {
            listOfAlarmClock = new ListOfAlarmClock();
        }
        else {
            listOfAlarmClock = editListOfAlarmClockFragmentViewModel.getAlarmClockById(alarmClockId);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.MINUTE, listOfAlarmClock.timeAlarmClockMinute);
            calendar.set(Calendar.HOUR_OF_DAY, listOfAlarmClock.timeAlarmClockHour);
            binding.DescriptionAlarmClock.setText(listOfAlarmClock.textAlarmClock);
        }

        binding.ButtonEditListOfAlarmClock.setText(simpleDateFormat.format(calendar.getTime()));

        binding.ButtonEditListOfAlarmClock.setOnClickListener(v -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .setTitleText("Выберите время")
                    .build();
            materialTimePicker.addOnPositiveButtonClickListener(v1 -> {
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

                if (calendar.get(Calendar.HOUR_OF_DAY)<Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                    calendar.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE)+1);
                else if (calendar.get(Calendar.HOUR_OF_DAY)==Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && calendar.get(Calendar.MINUTE)<Calendar.getInstance().get(Calendar.MINUTE))
                    calendar.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE)+1);
            });
            materialTimePicker.addOnDismissListener(v2 -> {
                binding.ButtonEditListOfAlarmClock.setText(simpleDateFormat.format(calendar.getTime()));
            });
            materialTimePicker.show(getChildFragmentManager(),"TAG");
        });
        binding.ButtonSaveEditAlarmClock.setOnClickListener(v ->{
            listOfAlarmClock.textAlarmClock= "" + binding.DescriptionAlarmClock.getText().toString();
            listOfAlarmClock.timeAlarmClockHour = calendar.get(Calendar.HOUR_OF_DAY);
            listOfAlarmClock.timeAlarmClockMinute = calendar.get(Calendar.MINUTE);
            listOfAlarmClock.activeAlarmClock = true;
            listOfAlarmClock.activeRepeatAlarmClock = false;

            if (alarmClockId==-1) {
                editListOfAlarmClockFragmentViewModel.saveNewAlarmClock(listOfAlarmClock);
            }
            else {
                editListOfAlarmClockFragmentViewModel.saveEditAlarmClock(listOfAlarmClock);
            }
            Toast.makeText(getContext(), "Будильник на " + simpleDateFormat
                    .format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.mainFragment);
        });
        binding.ButtonDeleteAlarmClock.setOnClickListener(v -> {
            if (alarmClockId!=-1){
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
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(requireActivity(), alarmClockId ,
                                alarmInfoIntern, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                editListOfAlarmClockFragmentViewModel.deleteAlarmClock(listOfAlarmClock);
            }
            NavHostFragment.findNavController(this)
                    .navigate(R.id.mainFragment);
        });
    }
}

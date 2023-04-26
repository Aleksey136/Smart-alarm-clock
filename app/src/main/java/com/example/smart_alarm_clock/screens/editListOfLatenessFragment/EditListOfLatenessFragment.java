package com.example.smart_alarm_clock.screens.editListOfLatenessFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.databinding.FragmentEditListOfLatenessBinding;
import com.example.smart_alarm_clock.model.ListOfLateness;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditListOfLatenessFragment extends Fragment {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private FragmentEditListOfLatenessBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditListOfLatenessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListOfLateness listOfLateness;

        EditListOfLatenessFragmentViewModel editListOfLatenessFragmentViewModel =
                new ViewModelProvider(this).get(EditListOfLatenessFragmentViewModel.class);

        int latenessId = EditListOfLatenessFragmentArgs.fromBundle(requireArguments()).getLatenessId();

        listOfLateness = editListOfLatenessFragmentViewModel.getLatenessById(latenessId);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MINUTE, listOfLateness.timeLatenessMinute);
        calendar.set(Calendar.HOUR_OF_DAY, listOfLateness.timeLatenessHour);
        binding.DescriptionLateness.setText(listOfLateness.textLateness);
        binding.TextTimeLateness.setText(simpleDateFormat.format(calendar.getTime()));

        binding.ButtonEditListOfLateness.setOnClickListener(v -> {
            listOfLateness.textLateness = "" + binding.DescriptionLateness.getText().toString();
            editListOfLatenessFragmentViewModel.saveEditLateness(listOfLateness);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.listOfLatenessFragment);
        });

        binding.ButtonDeleteListOfLateness.setOnClickListener(v -> {
            editListOfLatenessFragmentViewModel.deleteLateness(listOfLateness);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.listOfLatenessFragment);
        });
    }
}

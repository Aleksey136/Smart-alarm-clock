package com.example.smart_alarm_clock.screens.mainFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.databinding.FragmentMainBinding;

public class MainFragment extends Fragment implements AdapterForListOfAlarmClock.OnItemClickListener{

    private FragmentMainBinding binding;
    private MainFragmentViewModel mainFragmentViewModel;

    @Override
    public void onItemClick(int position) {

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
        binding.ListOfAlarmClock.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));

        AdapterForListOfAlarmClock adapter = new AdapterForListOfAlarmClock();
        binding.ListOfAlarmClock.setAdapter(adapter);

        binding.ButtonAddAlarmClock.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_mainFragment_to_editListOfAlarmClockFragment);
        });

        binding.ButtonToListOfLateness.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_mainFragment_to_listOfLatenessFragment);
        });

        mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);

    }
}
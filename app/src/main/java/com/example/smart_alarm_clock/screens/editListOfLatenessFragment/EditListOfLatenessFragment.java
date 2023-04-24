package com.example.smart_alarm_clock.screens.editListOfLatenessFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.smart_alarm_clock.databinding.FragmentEditListOfLatenessBinding;

public class EditListOfLatenessFragment extends Fragment {
    private FragmentEditListOfLatenessBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditListOfLatenessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}

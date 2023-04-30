package com.example.smart_alarm_clock.screens.listOfLatenessFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_alarm_clock.databinding.FragmentListOfLatenessBinding;
import com.example.smart_alarm_clock.model.ListOfLateness;

import java.util.List;

public class ListOfLatenessFragment extends Fragment implements AdapterForListOfLateness.OnListener {

    private FragmentListOfLatenessBinding binding;

    //При нажатии на элемент списка запускается другой фрагмент
    @Override
    public void onItemClick(int position, int lateness_Id) {
        ListOfLatenessFragmentDirections.ActionListOfLatenessFragmentToEditListOfLatenessFragment direction;
        direction = ListOfLatenessFragmentDirections.actionListOfLatenessFragmentToEditListOfLatenessFragment(lateness_Id);

        NavHostFragment.findNavController(this).navigate(direction);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListOfLatenessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false);
        binding.ListOfLateness.setLayoutManager(linearLayoutManager);

        AdapterForListOfLateness adapter = new AdapterForListOfLateness();
        binding.ListOfLateness.setAdapter(adapter);
        adapter.OnListener(this);

        ListOfLatenessFragmentViewModel listOfLatenessFragmentViewModel =
                new ViewModelProvider(this).get(ListOfLatenessFragmentViewModel.class);
        listOfLatenessFragmentViewModel.getListLiveData().observe(requireActivity(), new Observer<List<ListOfLateness>>() {
            @Override
            public void onChanged(List<ListOfLateness> listOfLatenessList) {
                adapter.setItems(listOfLatenessList);
            }
        });

        SwitchCompat switchActiveTechnology = binding.SwitchActiveTechnology;
        ListOfLateness lateness;

        //Если существует хоть 1 опоздание, можно включить технологию опозданий
        if (!listOfLatenessFragmentViewModel.getListLateness().isEmpty()) {
            lateness = listOfLatenessFragmentViewModel.getListLateness().get(0);
            switchActiveTechnology.setChecked(lateness.technologyLateness);
        } else {
            lateness = new ListOfLateness();
            lateness.technologyLateness = false;
        }

        binding.SwitchActiveTechnology.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!listOfLatenessFragmentViewModel.getListLateness().isEmpty()) {
                lateness.technologyLateness = isChecked;
                listOfLatenessFragmentViewModel.updateLateness(lateness);
            }
            else {
                lateness.technologyLateness = isChecked;
            }
        });
    }
}

package com.example.smart_alarm_clock.screens.activeAlarmClockFragment;

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
import androidx.navigation.fragment.NavHostFragment;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.databinding.FragmentActiveAlarmClockBinding;

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

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getContext(),notificationUri);
        player = MediaPlayer.create(requireContext(), notificationUri);
        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(getContext(),notificationUri);
            player = MediaPlayer.create(requireContext(), notificationUri);
        }
        if (ringtone != null) {
            player.setLooping(true);
            player.start();
        }
        binding.disableAlarmClock.setOnClickListener(v -> {
            if (ringtone != null){
                player.stop();
                NavHostFragment.findNavController(this)
                        .navigate(R.id.mainFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        if (ringtone != null && player.isPlaying()){
            player.stop();
        }
        super.onDestroyView();
    }
}

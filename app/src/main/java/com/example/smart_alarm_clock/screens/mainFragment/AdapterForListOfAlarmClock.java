package com.example.smart_alarm_clock.screens.mainFragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;

import java.util.List;

public class AdapterForListOfAlarmClock extends RecyclerView.Adapter<AdapterForListOfAlarmClock.AlarmClockViewHolder> {

    //Работа с прослушивателем для выполнения действий, доступных только фрагменту
    private OnListener listener;

    public interface OnListener {
        void onItemClick(int position, int alarmClockID);

        void onActiveAlarmClockManager(ListOfAlarmClock listOfAlarmClock, int delayTechnology);

        void onDisableAlarmClockManager(ListOfAlarmClock listOfAlarmClock);

        void onAlarmClockUpdate(ListOfAlarmClock listOfAlarmClock);

        int onSearchDelayAlarmClock();
    }

    public void OnListener(OnListener listener) {
        this.listener = listener;
    }

    //Создан для сортировки будильников по времени
    private SortedList<ListOfAlarmClock> sortedList;

    public AdapterForListOfAlarmClock() {
        this.sortedList = new SortedList<>(ListOfAlarmClock.class, new SortedList.Callback<ListOfAlarmClock>() {
            @Override
            public int compare(ListOfAlarmClock o1, ListOfAlarmClock o2) {
                if (o1.timeAlarmClockHour > o2.timeAlarmClockHour) {
                    return 1;
                } else if (o1.timeAlarmClockHour == o2.timeAlarmClockHour && o1.timeAlarmClockMinute >= o2.timeAlarmClockMinute)
                    return 1;
                else return -1;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(ListOfAlarmClock oldItem, ListOfAlarmClock newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(ListOfAlarmClock item1, ListOfAlarmClock item2) {
                return item1.alarmClock_ID == item2.alarmClock_ID;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    public void setItems(List<ListOfAlarmClock> listOfAlarmClocks) {
        sortedList.replaceAll(listOfAlarmClocks);
    }

    @NonNull
    @Override
    public AlarmClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_of_alarm_clock, parent, false);

        return new AlarmClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmClockViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(position, holder.alarmClock.alarmClock_ID);
            }
        });
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    class AlarmClockViewHolder extends RecyclerView.ViewHolder {

        TextView alarmClock_time;
        SwitchCompat switchActiveAlarmClock;

        int delayTechnology = listener.onSearchDelayAlarmClock();

        ListOfAlarmClock alarmClock;
        boolean silentUpdate;

        public AlarmClockViewHolder(@NonNull View itemView) {
            super(itemView);

            alarmClock_time = itemView.findViewById(R.id.AlarmClock_time);
            switchActiveAlarmClock = itemView.findViewById(R.id.SwitchActiveAlarmClock);

            //Сервис включается или выключается при использовании переключателя
            switchActiveAlarmClock.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!silentUpdate) {
                    alarmClock.activeAlarmClock = isChecked;
                    if (alarmClock.activeAlarmClock){
                        listener.onActiveAlarmClockManager(alarmClock, delayTechnology);
                    }
                    else{
                        listener.onDisableAlarmClockManager(alarmClock);
                    }
                    listener.onAlarmClockUpdate(alarmClock);
                }
            });
        }

        public void bind(ListOfAlarmClock alarmClock) {
            this.alarmClock = alarmClock;
            String timeHour;
            String timeMinute;
            if (alarmClock.timeAlarmClockHour < 10)
                timeHour = "0" + alarmClock.timeAlarmClockHour;
            else
                timeHour = "" + alarmClock.timeAlarmClockHour;
            if (alarmClock.timeAlarmClockMinute < 10)
                timeMinute = "0" + alarmClock.timeAlarmClockMinute;
            else
                timeMinute = "" + alarmClock.timeAlarmClockMinute;
            String time = timeHour + ":" + timeMinute;
            alarmClock_time.setText(time);

            if (!alarmClock.activeAlarmClock) {
                listener.onDisableAlarmClockManager(alarmClock);
            } else if (!alarmClock.activeRepeatAlarmClock){
                listener.onActiveAlarmClockManager(alarmClock, delayTechnology);
            }

            silentUpdate = true;
            switchActiveAlarmClock.setChecked(alarmClock.activeAlarmClock);
            silentUpdate = false;
        }
    }
}

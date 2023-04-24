package com.example.smart_alarm_clock.screens.mainFragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.smart_alarm_clock.App;
import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.model.ListOfAlarmClock;

import java.util.List;

public class AdapterForListOfAlarmClock extends RecyclerView.Adapter<AdapterForListOfAlarmClock.AlarmClockViewHolder> {
    public ListOfAlarmClock alarmClock;

    private OnItemClickListener listener;                            //Работа с прослушивателем, чтобы переходить на другой фрагмент

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    private SortedList<ListOfAlarmClock> sortedList;

    public AdapterForListOfAlarmClock() {
        this.sortedList = new SortedList<>(ListOfAlarmClock.class, new SortedList.Callback<ListOfAlarmClock>() {
            @Override
            public int compare(ListOfAlarmClock o1, ListOfAlarmClock o2) {
                if (o1.timeAlarmClockHour > o2.timeAlarmClockHour){
                    return 1;
                }
                else if (o1.timeAlarmClockHour == o2.timeAlarmClockHour && o1.timeAlarmClockMinute >= o2.timeAlarmClockMinute)
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

    public void setItems(List<ListOfAlarmClock> listOfAlarmClocks){
        sortedList.replaceAll(listOfAlarmClocks);
    }

    @NonNull
    @Override
    public AlarmClockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_of_alarm_clock,parent,false);

        return new AlarmClockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmClockViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    static class AlarmClockViewHolder extends RecyclerView.ViewHolder {

        TextView alarmClock_time;
        SwitchCompat switchActiveAlarmClock;

        ListOfAlarmClock alarmClock;
        boolean silentUpdate;

        public AlarmClockViewHolder(@NonNull View itemView) {
            super(itemView);

            alarmClock_time = itemView.findViewById(R.id.alarmClock_time);
            switchActiveAlarmClock = itemView.findViewById(R.id.switchActiveAlarmClock);

            switchActiveAlarmClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!silentUpdate){
                        alarmClock.activeAlarmClock = isChecked;
                        App.getInstance().getListOfAlarmClockDao().update(alarmClock);
                    }
                }
            });
        }

        public void bind(ListOfAlarmClock alarmClock){
            this.alarmClock = alarmClock;
            String time = alarmClock.timeAlarmClockHour + ":" + alarmClock.timeAlarmClockMinute;
            alarmClock_time.setText(time);

            silentUpdate = true;
            switchActiveAlarmClock.setChecked(alarmClock.activeAlarmClock);
            silentUpdate = false;
        }
    }
}

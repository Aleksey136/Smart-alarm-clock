package com.example.smart_alarm_clock.screens.listOfLatenessFragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.smart_alarm_clock.R;
import com.example.smart_alarm_clock.model.ListOfLateness;

import java.util.List;

public class AdapterForListOfLateness extends RecyclerView.Adapter<AdapterForListOfLateness.LatenessViewHolder> {
    public ListOfLateness lateness;

    //Работа с прослушивателем для выполнения действий, доступных только фрагменту
    private AdapterForListOfLateness.OnListener listener;

    public interface OnListener {
        void onItemClick(int position, int alarmClock_id);
    }

    public void OnListener(AdapterForListOfLateness.OnListener listener) {
        this.listener = listener;
    }

    //Создан для сортировки опозданий от большего времени к меньшему
    private SortedList<ListOfLateness> sortedList;

    public AdapterForListOfLateness() {
        this.sortedList = new SortedList<>(ListOfLateness.class, new SortedList.Callback<ListOfLateness>() {
            @Override
            public int compare(ListOfLateness o1, ListOfLateness o2) {
                if (o1.timeLatenessHour > o2.timeLatenessHour) {
                    return 1;
                } else if (o1.timeLatenessHour == o2.timeLatenessHour && o1.timeLatenessMinute >= o2.timeLatenessMinute)
                    return 1;
                else return -1;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(ListOfLateness oldItem, ListOfLateness newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(ListOfLateness item1, ListOfLateness item2) {
                return item1.lateness_ID == item2.lateness_ID;
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

    public void setItems(List<ListOfLateness> lateness) {
        sortedList.replaceAll(lateness);
    }

    @NonNull
    @Override
    public AdapterForListOfLateness.LatenessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_of_lateness, parent, false);

        return new LatenessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForListOfLateness.LatenessViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(position, holder.lateness.lateness_ID);
            }
        });
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    static class LatenessViewHolder extends RecyclerView.ViewHolder {

        TextView lateness_time;

        ListOfLateness lateness;

        public LatenessViewHolder(@NonNull View itemView) {
            super(itemView);
            lateness_time = itemView.findViewById(R.id.Lateness_time);
        }


        public void bind(ListOfLateness lateness) {
            this.lateness = lateness;

            String time = (lateness.timeLatenessHour * 60 + lateness.timeLatenessMinute) + " минут(а)";
            lateness_time.setText(time);
        }
    }
}

























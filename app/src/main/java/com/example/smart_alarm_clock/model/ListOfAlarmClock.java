package com.example.smart_alarm_clock.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class ListOfAlarmClock implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int alarmClock_ID;

    @ColumnInfo(name = "textAlarmClock")
    public String textAlarmClock;

    @ColumnInfo(name = "timeAlarmClockHour")
    public int timeAlarmClockHour;

    @ColumnInfo(name = "timeAlarmClockMinute")
    public int timeAlarmClockMinute;

    public ListOfAlarmClock() {
    }

    protected ListOfAlarmClock(Parcel in) {
        alarmClock_ID = in.readInt();
        textAlarmClock = in.readString();
        timeAlarmClockHour = in.readInt();
        timeAlarmClockMinute = in.readInt();
    }

    public static final Creator<ListOfAlarmClock> CREATOR = new Creator<ListOfAlarmClock>() {
        @Override
        public ListOfAlarmClock createFromParcel(Parcel in) {
            return new ListOfAlarmClock(in);
        }

        @Override
        public ListOfAlarmClock[] newArray(int size) {
            return new ListOfAlarmClock[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListOfAlarmClock)) return false;
        ListOfAlarmClock that = (ListOfAlarmClock) o;
        return alarmClock_ID == that.alarmClock_ID && timeAlarmClockHour == that.timeAlarmClockHour && timeAlarmClockMinute == that.timeAlarmClockMinute && Objects.equals(textAlarmClock, that.textAlarmClock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alarmClock_ID, textAlarmClock, timeAlarmClockHour, timeAlarmClockMinute);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(alarmClock_ID);
        dest.writeString(textAlarmClock);
        dest.writeInt(timeAlarmClockHour);
        dest.writeInt(timeAlarmClockMinute);
    }
}

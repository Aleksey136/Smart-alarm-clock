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

    @ColumnInfo(name = "activeAlarmClock")
    public boolean activeAlarmClock;

    @ColumnInfo(name = "activeRepeatAlarmClock")
    public boolean activeRepeatAlarmClock;

    public ListOfAlarmClock() {
    }

    protected ListOfAlarmClock(Parcel in) {
        alarmClock_ID = in.readInt();
        textAlarmClock = in.readString();
        timeAlarmClockHour = in.readInt();
        timeAlarmClockMinute = in.readInt();
        activeAlarmClock = in.readByte() != 0;
        activeRepeatAlarmClock = in.readByte() != 0;
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

        if (alarmClock_ID != that.alarmClock_ID) return false;
        if (timeAlarmClockHour != that.timeAlarmClockHour) return false;
        if (timeAlarmClockMinute != that.timeAlarmClockMinute) return false;
        if (activeAlarmClock != that.activeAlarmClock) return false;
        if (activeRepeatAlarmClock != that.activeRepeatAlarmClock) return false;
        return Objects.equals(textAlarmClock, that.textAlarmClock);
    }

    @Override
    public int hashCode() {
        int result = alarmClock_ID;
        result = 31 * result + (textAlarmClock != null ? textAlarmClock.hashCode() : 0);
        result = 31 * result + timeAlarmClockHour;
        result = 31 * result + timeAlarmClockMinute;
        result = 31 * result + (activeAlarmClock ? 1 : 0);
        result = 31 * result + (activeRepeatAlarmClock ? 1 : 0);
        return result;
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
        dest.writeByte((byte) (activeAlarmClock ? 1 : 0));
        dest.writeByte((byte) (activeRepeatAlarmClock ? 1 : 0));
    }
}

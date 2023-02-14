package com.example.smart_alarm_clock.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class ListOfLateness implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int lateness_ID;

    @ColumnInfo(name = "textLateness")
    public String textLateness;

    @ColumnInfo(name = "timeLateness")
    public int timeLateness;

    public ListOfLateness() {
    }

    protected ListOfLateness(Parcel in) {
        lateness_ID = in.readInt();
        textLateness = in.readString();
        timeLateness = in.readInt();
    }

    public static final Creator<ListOfLateness> CREATOR = new Creator<ListOfLateness>() {
        @Override
        public ListOfLateness createFromParcel(Parcel in) {
            return new ListOfLateness(in);
        }

        @Override
        public ListOfLateness[] newArray(int size) {
            return new ListOfLateness[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListOfLateness)) return false;
        ListOfLateness that = (ListOfLateness) o;
        return lateness_ID == that.lateness_ID && timeLateness == that.timeLateness && Objects.equals(textLateness, that.textLateness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lateness_ID, textLateness, timeLateness);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(lateness_ID);
        parcel.writeString(textLateness);
        parcel.writeInt(timeLateness);
    }
}

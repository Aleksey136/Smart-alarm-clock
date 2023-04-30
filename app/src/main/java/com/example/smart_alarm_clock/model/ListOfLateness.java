package com.example.smart_alarm_clock.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
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

    @ColumnInfo(name = "timeLatenessHour")
    public int timeLatenessHour;

    @ColumnInfo(name = "timeLatenessMinute")
    public int timeLatenessMinute;

    @ColumnInfo(name = "technologyLateness")
    public boolean technologyLateness;

    public ListOfLateness() {
    }

    protected ListOfLateness(Parcel in) {
        lateness_ID = in.readInt();
        textLateness = in.readString();
        timeLatenessHour = in.readInt();
        timeLatenessMinute = in.readInt();
        technologyLateness = in.readByte() != 0;
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

        if (lateness_ID != that.lateness_ID) return false;
        if (timeLatenessHour != that.timeLatenessHour) return false;
        if (timeLatenessMinute != that.timeLatenessMinute) return false;
        if (technologyLateness != that.technologyLateness) return false;
        return Objects.equals(textLateness, that.textLateness);
    }

    @Override
    public int hashCode() {
        int result = lateness_ID;
        result = 31 * result + (textLateness != null ? textLateness.hashCode() : 0);
        result = 31 * result + timeLatenessHour;
        result = 31 * result + timeLatenessMinute;
        result = 31 * result + (technologyLateness ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(lateness_ID);
        dest.writeString(textLateness);
        dest.writeInt(timeLatenessHour);
        dest.writeInt(timeLatenessMinute);
        dest.writeByte((byte) (technologyLateness ? 1 : 0));
    }
}

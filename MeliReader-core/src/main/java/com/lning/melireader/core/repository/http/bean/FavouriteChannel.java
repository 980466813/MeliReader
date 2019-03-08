package com.lning.melireader.core.repository.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ning on 2019/2/6.
 */


public class FavouriteChannel implements Parcelable {
    private String channel_id;
    private String channel_name;

    public FavouriteChannel() {

    }

    protected FavouriteChannel(Parcel in) {
        channel_id = in.readString();
        channel_name = in.readString();
    }

    public static final Creator<FavouriteChannel> CREATOR = new Creator<FavouriteChannel>() {
        @Override
        public FavouriteChannel createFromParcel(Parcel in) {
            return new FavouriteChannel(in);
        }

        @Override
        public FavouriteChannel[] newArray(int size) {
            return new FavouriteChannel[size];
        }
    };

    public FavouriteChannel(String id, String name) {
        this.channel_id = id;
        this.channel_name = name;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(channel_id);
        parcel.writeString(channel_name);
    }
}

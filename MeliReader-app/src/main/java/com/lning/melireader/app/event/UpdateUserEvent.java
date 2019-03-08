package com.lning.melireader.app.event;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.lning.melireader.R;

public class UpdateUserEvent implements Parcelable {

    public static final int IMAGE_TYPE = R.id.user_info_profile_iv;
    public static final int NICKNAME_TYPE = R.id.user_info_nickname_tv;
    public static final int ADDRESS_TYPE = R.id.user_info_address_tv;
    public static final int GENDER_TYPE = R.id.user_info_gender_tv;
    public static final int BIRTHDAY_TYPE = R.id.user_info_birthday_tv;
    public static final int SIGNATURE_TYPE = R.id.user_info_signature_cl;
    public int viewId;
    public String value;
    public String title;

    public UpdateUserEvent() {
    }

    public UpdateUserEvent(int viewId, String value) {
        this.viewId = viewId;
        this.value = value;
    }

    protected UpdateUserEvent(Parcel in) {
        viewId = in.readInt();
        value = in.readString();
        title = in.readString();
    }

    public static final Creator<UpdateUserEvent> CREATOR = new Creator<UpdateUserEvent>() {
        @Override
        public UpdateUserEvent createFromParcel(Parcel in) {
            return new UpdateUserEvent(in);
        }

        @Override
        public UpdateUserEvent[] newArray(int size) {
            return new UpdateUserEvent[size];
        }
    };

    public static UpdateUserEvent createProfileEvent(String value) {
        UpdateUserEvent event = new UpdateUserEvent();
        event.viewId = IMAGE_TYPE;
        event.value = value;
        event.title = "头像";
        return event;
    }

    public static UpdateUserEvent createNicknameEvent(String value) {
        UpdateUserEvent event = new UpdateUserEvent();
        event.viewId = NICKNAME_TYPE;
        event.value = !TextUtils.isEmpty(value) ? value : "";
        event.title = "昵称";
        return event;
    }

    public static UpdateUserEvent createBirthdayEvent(String value) {
        UpdateUserEvent event = new UpdateUserEvent();
        event.viewId = BIRTHDAY_TYPE;
        event.value = value;
        event.title = "生日";
        return event;
    }

    public static UpdateUserEvent createGenderEvent(String value) {
        UpdateUserEvent event = new UpdateUserEvent();
        event.viewId = GENDER_TYPE;
        event.value = value;
        event.title = "选择性别";
        return event;
    }

    public static UpdateUserEvent createAddressEvent(String value) {
        UpdateUserEvent event = new UpdateUserEvent();
        event.viewId = ADDRESS_TYPE;
        event.value = !TextUtils.isEmpty(value) ? value : "";
        event.title = "地址";
        return event;
    }

    public static UpdateUserEvent createSignatureEvent(String value) {
        UpdateUserEvent event = new UpdateUserEvent();
        event.viewId = SIGNATURE_TYPE;
        event.value = !TextUtils.isEmpty(value) ? value : "";
        event.title = "个人简介";
        return event;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(viewId);
        parcel.writeString(value);
        parcel.writeString(title);
    }

    @Override
    public String toString() {
        return "UpdateUserEvent{" +
                "viewId=" + viewId +
                ", value='" + value + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

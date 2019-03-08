package com.lning.melireader.ui.provider.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.lning.melireader.core.repository.http.bean.CollectionVo;

/**
 * Created by Ning on 2019/2/16.
 */

public class CollectionPro implements Parcelable {

    private CollectionVo collectionVo;

    private int visible;
    private boolean checked;

    protected CollectionPro(Parcel in) {
        visible = in.readInt();
        checked = in.readByte() != 0;
    }

    public static final Creator<CollectionPro> CREATOR = new Creator<CollectionPro>() {
        @Override
        public CollectionPro createFromParcel(Parcel in) {
            return new CollectionPro(in);
        }

        @Override
        public CollectionPro[] newArray(int size) {
            return new CollectionPro[size];
        }
    };

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public CollectionPro(CollectionVo collectionVo) {
        this.collectionVo = collectionVo;
        this.visible = View.GONE;
        this.checked = false;
    }

    public CollectionVo getCollectionVo() {
        return collectionVo;
    }

    public void setCollectionVo(CollectionVo collectionVo) {
        this.collectionVo = collectionVo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(visible);
        parcel.writeByte((byte) (checked ? 1 : 0));
    }
}

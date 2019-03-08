package com.lning.melireader.core.repository.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lning.melireader.core.repository.db.converter.DateConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by Ning on 2019/2/22.
 */

public class VideoNewsListVo implements Parcelable {

    private String id;

    private String title;
    private String summary;
    private String image;

    private String channelId;
    private String dislikeIds;
    private String dislikeNames;
    private String source;
    private String dsource;

    private String publisherName;
    private String publisherProfile;

    private Date created;
    private String ctype;
    private int commentCount;
    private boolean read;
    private boolean subscripted;
    private String videoUrls;

    public VideoNewsListVo() {
    }

    protected VideoNewsListVo(Parcel in) {
        id = in.readString();
        title = in.readString();
        summary = in.readString();
        image = in.readString();
        channelId = in.readString();
        dislikeIds = in.readString();
        dislikeNames = in.readString();
        source = in.readString();
        dsource = in.readString();
        publisherName = in.readString();
        publisherProfile = in.readString();
        ctype = in.readString();
        commentCount = in.readInt();
        read = in.readByte() != 0;
        subscripted = in.readByte() != 0;
        videoUrls = in.readString();
    }

    public static final Creator<VideoNewsListVo> CREATOR = new Creator<VideoNewsListVo>() {
        @Override
        public VideoNewsListVo createFromParcel(Parcel in) {
            return new VideoNewsListVo(in);
        }

        @Override
        public VideoNewsListVo[] newArray(int size) {
            return new VideoNewsListVo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDislikeIds() {
        return dislikeIds;
    }

    public void setDislikeIds(String dislikeIds) {
        this.dislikeIds = dislikeIds;
    }

    public String getDislikeNames() {
        return dislikeNames;
    }

    public void setDislikeNames(String dislikeNames) {
        this.dislikeNames = dislikeNames;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDsource() {
        return dsource;
    }

    public void setDsource(String dsource) {
        this.dsource = dsource;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherProfile() {
        return publisherProfile;
    }

    public void setPublisherProfile(String publisherProfile) {
        this.publisherProfile = publisherProfile;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isSubscripted() {
        return subscripted;
    }

    public void setSubscripted(boolean subscripted) {
        this.subscripted = subscripted;
    }

    public String getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(String videoUrls) {
        this.videoUrls = videoUrls;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(summary);
        parcel.writeString(image);
        parcel.writeString(channelId);
        parcel.writeString(dislikeIds);
        parcel.writeString(dislikeNames);
        parcel.writeString(source);
        parcel.writeString(dsource);
        parcel.writeString(publisherName);
        parcel.writeString(publisherProfile);
        parcel.writeString(ctype);
        parcel.writeInt(commentCount);
        parcel.writeByte((byte) (read ? 1 : 0));
        parcel.writeByte((byte) (subscripted ? 1 : 0));
        parcel.writeString(videoUrls);
    }
}

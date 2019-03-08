package com.lning.melireader.core.repository.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lning.melireader.core.repository.db.converter.DateConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NewsListVo implements Parcelable {

    @Id
    private String id;

    @NotNull
    private String title;
    private String summary;
    private String image;

    @NotNull
    private String channelId;
    private String dislikeIds;
    private String dislikeNames;
    private String source;
    private String dsource;

    private String publisherName;
    private String publisherProfile;

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date created;
    private String ctype;
    private int commentCount;
    private boolean read;
    private boolean subscripted;

    @Generated(hash = 2013460596)
    public NewsListVo(String id, @NotNull String title, String summary,
                      String image, @NotNull String channelId, String dislikeIds,
                      String dislikeNames, String source, String dsource,
                      String publisherName, String publisherProfile, Date created,
                      String ctype, int commentCount, boolean read, boolean subscripted) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.image = image;
        this.channelId = channelId;
        this.dislikeIds = dislikeIds;
        this.dislikeNames = dislikeNames;
        this.source = source;
        this.dsource = dsource;
        this.publisherName = publisherName;
        this.publisherProfile = publisherProfile;
        this.created = created;
        this.ctype = ctype;
        this.commentCount = commentCount;
        this.read = read;
        this.subscripted = subscripted;
    }

    @Generated(hash = 1941970437)
    public NewsListVo() {
    }

    protected NewsListVo(Parcel in) {
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
    }

    public static final Creator<NewsListVo> CREATOR = new Creator<NewsListVo>() {
        @Override
        public NewsListVo createFromParcel(Parcel in) {
            return new NewsListVo(in);
        }

        @Override
        public NewsListVo[] newArray(int size) {
            return new NewsListVo[size];
        }
    };

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

    public String getPublisherProfile() {
        return publisherProfile;
    }

    public void setPublisherProfile(String publisherProfile) {
        this.publisherProfile = publisherProfile;
    }

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

    public String getDislikeNames() {
        return dislikeNames;
    }

    public void setDislikeNames(String dislikeNames) {
        this.dislikeNames = dislikeNames;
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

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
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

    public boolean getRead() {
        return this.read;
    }

    public boolean getSubscripted() {
        return this.subscripted;
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
    }
}

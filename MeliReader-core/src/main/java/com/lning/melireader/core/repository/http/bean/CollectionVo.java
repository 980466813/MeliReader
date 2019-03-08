package com.lning.melireader.core.repository.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by Ning on 2019/2/14.
 */

public class CollectionVo implements Parcelable {

    private String newsId;
    private String title;
    private String summary;
    private String image;
    private String channelId;

    private String dislikeIds;
    private String dislikeNames;
    private String source;
    private String dsource;
    private Date publishDate;

    private String publisherName;
    private String publisherProfile;
    private String ctype;
    private int commentCount;

    private Long id;
    private String userId;
    private Date created;
    private Date updated;
    private String tag;
    private boolean read;
    private boolean subscripted;

    public CollectionVo() {
    }

    public CollectionVo(Long id, String userId, String tag, NewsListVo vo) {
        this.id = id;
        this.userId = userId;

        this.newsId = vo.getId();
        this.title = vo.getTitle();
        this.summary = vo.getSummary();
        this.image = vo.getImage();
        this.channelId = vo.getChannelId();

        this.dislikeIds = vo.getDislikeIds();
        this.dislikeNames = vo.getDislikeNames();
        this.source = vo.getSource();
        this.dsource = vo.getDsource();
        this.publishDate = vo.getCreated();

        this.publisherName = vo.getPublisherName();
        this.publisherProfile = vo.getPublisherProfile();
        this.read = vo.getRead();
        this.subscripted = vo.getSubscripted();
        this.ctype = vo.getCtype();
        this.commentCount = vo.getCommentCount();
        this.created = new Date();
        this.updated = new Date();
        this.tag = tag;

    }


    protected CollectionVo(Parcel in) {
        newsId = in.readString();
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
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        userId = in.readString();
        tag = in.readString();
        read = in.readByte() != 0;
        subscripted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newsId);
        dest.writeString(title);
        dest.writeString(summary);
        dest.writeString(image);
        dest.writeString(channelId);
        dest.writeString(dislikeIds);
        dest.writeString(dislikeNames);
        dest.writeString(source);
        dest.writeString(dsource);
        dest.writeString(publisherName);
        dest.writeString(publisherProfile);
        dest.writeString(ctype);
        dest.writeInt(commentCount);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(userId);
        dest.writeString(tag);
        dest.writeByte((byte) (read ? 1 : 0));
        dest.writeByte((byte) (subscripted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CollectionVo> CREATOR = new Creator<CollectionVo>() {
        @Override
        public CollectionVo createFromParcel(Parcel in) {
            return new CollectionVo(in);
        }

        @Override
        public CollectionVo[] newArray(int size) {
            return new CollectionVo[size];
        }
    };

    @JSONField(serialize = false)
    public NewsListVo getNewsListVo() {
        NewsListVo newsListVo = new NewsListVo(newsId, title, summary, image, channelId, dislikeIds, dislikeNames
                , source, dsource, publisherName, publisherProfile, publishDate, ctype, commentCount, read, subscripted);
        return newsListVo;
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    @Override
    public String toString() {
        return "CollectionVo{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", image='" + image + '\'' +
                ", channelId='" + channelId + '\'' +
                ", dislikeIds='" + dislikeIds + '\'' +
                ", dislikeNames='" + dislikeNames + '\'' +
                ", source='" + source + '\'' +
                ", dsource='" + dsource + '\'' +
                ", publishDate=" + publishDate +
                ", publisherName='" + publisherName + '\'' +
                ", publisherProfile='" + publisherProfile + '\'' +
                ", ctype='" + ctype + '\'' +
                ", commentCount=" + commentCount +
                ", id=" + id +
                ", userId='" + userId + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", tag='" + tag + '\'' +
                ", read=" + read +
                ", subscripted=" + subscripted +
                '}';
    }
}

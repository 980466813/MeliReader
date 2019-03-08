package com.lning.melireader.core.repository.http.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class HistoryVo {
    private Long id;
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
    private Date updated;
    private String ctype;
    private int commentCount;

    private String newsId;
    private String userId;
    private Date publishDate;
    private boolean read;
    private boolean subscripted;

    public HistoryVo() {
    }

    public HistoryVo(Long id, String userId, NewsListVo vo) {
        this.id = id;
        this.newsId = vo.getId();
        this.userId = userId;
        this.title = vo.getTitle();
        this.summary = vo.getSummary();
        this.image = vo.getImage();
        this.channelId = vo.getChannelId();
        this.dislikeIds = vo.getDislikeIds();
        this.dislikeNames = vo.getDislikeNames();
        this.source = vo.getSource();
        this.dsource = vo.getDsource();
        this.publisherName = vo.getPublisherName();
        this.publisherProfile = vo.getPublisherProfile();
        this.publishDate = vo.getCreated();
        this.read = vo.getRead();
        this.subscripted = vo.getSubscripted();

        this.created = new Date();
        this.updated = new Date();
        this.ctype = vo.getCtype();
        this.commentCount = vo.getCommentCount();
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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

    @JSONField(serialize = false)
    public NewsListVo getNewsListVo() {
        NewsListVo newsListVo = new NewsListVo(newsId, title, summary, image, channelId, dislikeIds, dislikeNames
                , source, dsource, publisherName, publisherProfile, publishDate, ctype, commentCount, read, subscripted);
        return newsListVo;
    }

    @Override
    public String toString() {
        return "HistoryVo{" +
                "id=" + id +
                ", newsId='" + newsId + '\'' +
                ", userId='" + userId + '\'' +
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
                ", created=" + created +
                ", ctype='" + ctype + '\'' +
                ", commentCount=" + commentCount +
                '}';
    }
}

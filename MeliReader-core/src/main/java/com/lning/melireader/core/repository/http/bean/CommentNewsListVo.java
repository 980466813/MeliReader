package com.lning.melireader.core.repository.http.bean;

import android.text.TextUtils;

import java.util.Date;

public class CommentNewsListVo {

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

    private boolean read;
    private boolean subscripted;

    private Date created;
    private String ctype;
    private int commentCount;
    private String commentId;
    private String content;
    private int likeCount;
    private boolean liked;


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
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


    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
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

    public String[] getImageUrls() {
        if (TextUtils.isEmpty(image)) {
            return null;
        }
        return image.split(",");
    }

}

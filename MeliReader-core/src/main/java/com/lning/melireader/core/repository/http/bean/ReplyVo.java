package com.lning.melireader.core.repository.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ReplyVo implements Parcelable {

    private String id;
    private String userId;
    private String objectId;
    private String content;
    private Date created;

    private String userNickname;
    private String userProfile;

    private boolean liked;
    private int likeCount;
    private String replyId;
    private String replyUserId;
    private String replyNickname;
    private String replyProfile;

    public ReplyVo() {
    }

    protected ReplyVo(Parcel in) {
        id = in.readString();
        userId = in.readString();
        objectId = in.readString();
        content = in.readString();
        userNickname = in.readString();
        userProfile = in.readString();
        liked = in.readByte() != 0;
        likeCount = in.readInt();
        replyId = in.readString();
        replyUserId = in.readString();
        replyNickname = in.readString();
        replyProfile = in.readString();
    }

    public static final Creator<ReplyVo> CREATOR = new Creator<ReplyVo>() {
        @Override
        public ReplyVo createFromParcel(Parcel in) {
            return new ReplyVo(in);
        }

        @Override
        public ReplyVo[] newArray(int size) {
            return new ReplyVo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getReplyNickname() {
        return replyNickname;
    }

    public void setReplyNickname(String replyNickname) {
        this.replyNickname = replyNickname;
    }

    public String getReplyProfile() {
        return replyProfile;
    }

    public void setReplyProfile(String replyProfile) {
        this.replyProfile = replyProfile;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(objectId);
        parcel.writeString(content);
        parcel.writeString(userNickname);
        parcel.writeString(userProfile);
        parcel.writeByte((byte) (liked ? 1 : 0));
        parcel.writeInt(likeCount);
        parcel.writeString(replyId);
        parcel.writeString(replyUserId);
        parcel.writeString(replyNickname);
        parcel.writeString(replyProfile);
    }


}

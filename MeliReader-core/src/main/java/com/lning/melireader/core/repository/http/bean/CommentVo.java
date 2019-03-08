package com.lning.melireader.core.repository.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class CommentVo implements Parcelable {

    private String id;
    private String userId;
    private String objectId;
    private String content;
    private Date created;

    private String userProfile;
    private String userNickname;

    private boolean liked;
    private int likeCount;
    private List<ReplyVo> replyList;

    public CommentVo() {
    }

    protected CommentVo(Parcel in) {
        id = in.readString();
        userId = in.readString();
        objectId = in.readString();
        content = in.readString();
        userProfile = in.readString();
        userNickname = in.readString();
        liked = in.readByte() != 0;
        likeCount = in.readInt();
        replyList = in.createTypedArrayList(ReplyVo.CREATOR);
    }

    public static final Creator<CommentVo> CREATOR = new Creator<CommentVo>() {
        @Override
        public CommentVo createFromParcel(Parcel in) {
            return new CommentVo(in);
        }

        @Override
        public CommentVo[] newArray(int size) {
            return new CommentVo[size];
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

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    public List<ReplyVo> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyVo> replyList) {
        this.replyList = replyList;
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
        parcel.writeString(userProfile);
        parcel.writeString(userNickname);
        parcel.writeByte((byte) (liked ? 1 : 0));
        parcel.writeInt(likeCount);
        parcel.writeTypedList(replyList);
    }
}

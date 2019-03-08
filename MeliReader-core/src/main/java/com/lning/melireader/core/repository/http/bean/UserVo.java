package com.lning.melireader.core.repository.http.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.lning.melireader.core.repository.db.converter.DateConverter;
import com.lning.melireader.core.repository.db.converter.UserItemListConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserVo {

    @Id(autoincrement = true)
    @JSONField(serialize = false)
    private Long localDbId;

    @NotNull
    private String id;

    @NotNull
    private Long rid;

    private String username;
    private String nickname;
    private String profile;
    private int experience;

    private int attentionCount;
    private int fansCount;

    private int level;
    private int upperLimit;
    private String levelName;
    private boolean subscripted;

    @Convert(converter = UserItemListConverter.class, columnType = String.class)
    private ItemListVo itemListVo;

    @NotNull
    @JSONField(serialize = false)
    private String type;

    @NotNull
    @JSONField(serialize = false)
    private String token;

    @NotNull
    private String favouriteChannels;

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date birthday;

    private String address;
    private String signature;
    private byte gender;

    public UserVo() {
        super();
    }

    @Generated(hash = 545491095)
    public UserVo(Long localDbId, @NotNull String id, @NotNull Long rid, String username,
            String nickname, String profile, int experience, int attentionCount,
            int fansCount, int level, int upperLimit, String levelName, boolean subscripted,
            ItemListVo itemListVo, @NotNull String type, @NotNull String token,
            @NotNull String favouriteChannels, Date birthday, String address,
            String signature, byte gender) {
        this.localDbId = localDbId;
        this.id = id;
        this.rid = rid;
        this.username = username;
        this.nickname = nickname;
        this.profile = profile;
        this.experience = experience;
        this.attentionCount = attentionCount;
        this.fansCount = fansCount;
        this.level = level;
        this.upperLimit = upperLimit;
        this.levelName = levelName;
        this.subscripted = subscripted;
        this.itemListVo = itemListVo;
        this.type = type;
        this.token = token;
        this.favouriteChannels = favouriteChannels;
        this.birthday = birthday;
        this.address = address;
        this.signature = signature;
        this.gender = gender;
    }

    public UserVo(String id, String favouriteChannels) {
        this.id = id;
        this.rid = 2L;
        this.type = "basic";
        this.favouriteChannels = favouriteChannels;
    }

    public boolean isSubscripted() {
        return subscripted;
    }

    public void setSubscripted(boolean subscripted) {
        this.subscripted = subscripted;
    }

    public Long getLocalDbId() {
        return localDbId;
    }

    public void setLocalDbId(Long localDbId) {
        this.localDbId = localDbId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }


    public ItemListVo getItemListVo() {
        return itemListVo;
    }

    public void setItemListVo(ItemListVo itemListVo) {
        this.itemListVo = itemListVo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getFavouriteChannels() {
        return favouriteChannels;
    }

    public void setFavouriteChannels(String favouriteChannels) {
        this.favouriteChannels = favouriteChannels;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", rid=" + rid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profile='" + profile + '\'' +
                ", experience=" + experience +
                ", attentionCount=" + attentionCount +
                ", fansCount=" + fansCount +
                ", level=" + level +
                ", upperLimit=" + upperLimit +
                ", levelName='" + levelName + '\'' +
                ", itemListVo=" + itemListVo +
                ", type='" + type + '\'' +
                ", token='" + token + '\'' +
                ", favouriteChannels='" + favouriteChannels + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", signature='" + signature + '\'' +
                ", gender=" + gender +
                '}';
    }

    public boolean getSubscripted() {
        return this.subscripted;
    }
}

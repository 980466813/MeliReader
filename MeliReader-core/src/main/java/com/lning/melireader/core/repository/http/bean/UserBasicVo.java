package com.lning.melireader.core.repository.http.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.lning.melireader.core.repository.db.converter.UserItemListConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class UserBasicVo {
    @Id
    private String id;

    @NotNull
    private Long rid;

    @NotNull
    private String username;
    @NotNull
    private String nickname;
    @NotNull
    private String profile;
    private int experience;

    private int attentionCount;
    private int fansCount;

    private int level;
    private int upperLimit;
    private String levelName;

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
    private boolean subscripted;

    public UserBasicVo() {
        setType("basic");
    }

    public UserBasicVo(String id, String favouriteChannels) {
        this.id = id;
        this.favouriteChannels = favouriteChannels;
    }

    @Generated(hash = 1622139133)
    public UserBasicVo(String id, @NotNull Long rid, @NotNull String username,
            @NotNull String nickname, @NotNull String profile, int experience,
            int attentionCount, int fansCount, int level, int upperLimit, String levelName,
            ItemListVo itemListVo, @NotNull String type, @NotNull String token,
            @NotNull String favouriteChannels, boolean subscripted) {
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
        this.itemListVo = itemListVo;
        this.type = type;
        this.token = token;
        this.favouriteChannels = favouriteChannels;
        this.subscripted = subscripted;
    }

    public boolean isSubscripted() {
        return subscripted;
    }

    public void setSubscripted(boolean subscripted) {
        this.subscripted = subscripted;
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
        return "UserBasicVo{" +
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
                ", favouriteChannels='" + favouriteChannels + '\'' +
                '}';
    }

    public boolean getSubscripted() {
        return this.subscripted;
    }
}

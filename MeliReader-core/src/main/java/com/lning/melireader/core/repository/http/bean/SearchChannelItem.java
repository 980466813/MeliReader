package com.lning.melireader.core.repository.http.bean;

/**
 * Created by Ning on 2019/2/24.
 */

public class SearchChannelItem {

    private String channelId;
    private String channelName;
    private String channelImage;

    public SearchChannelItem() {
    }

    public SearchChannelItem(String channelId, String channelName, String channelImage) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelImage = channelImage;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(String channelImage) {
        this.channelImage = channelImage;
    }
}

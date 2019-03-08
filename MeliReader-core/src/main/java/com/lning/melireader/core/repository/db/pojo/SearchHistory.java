package com.lning.melireader.core.repository.db.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Ning on 2019/2/24.
 */

@Entity
public class SearchHistory {

    @Id(autoincrement = true)
    private Long id;

    private String keywords;

    private int click;

    @Generated(hash = 1745531915)
    public SearchHistory(Long id, String keywords, int click) {
        this.id = id;
        this.keywords = keywords;
        this.click = click;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}

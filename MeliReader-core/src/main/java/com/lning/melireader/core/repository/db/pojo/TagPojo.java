package com.lning.melireader.core.repository.db.pojo;

import com.lning.melireader.core.repository.db.converter.DateConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Ning on 2019/2/14.
 */

@Entity
public class TagPojo {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String content; // 代表单个标签

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date created;

    @Generated(hash = 1928837427)
    public TagPojo(Long id, @NotNull String userId, @NotNull String content,
            Date created) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.created = created;
    }

    @Generated(hash = 1124538503)
    public TagPojo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

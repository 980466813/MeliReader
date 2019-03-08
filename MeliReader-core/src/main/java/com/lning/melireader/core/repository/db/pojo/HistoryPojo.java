package com.lning.melireader.core.repository.db.pojo;

import com.lning.melireader.core.repository.db.converter.DateConverter;
import com.lning.melireader.core.repository.http.bean.NewsListVo;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.lning.melireader.core.repository.db.dao.DaoSession;

import java.util.Date;
import com.lning.melireader.core.repository.db.dao.NewsListVoDao;
import com.lning.melireader.core.repository.db.dao.HistoryPojoDao;

/**
 * Created by Ning on 2019/2/13.
 */

@Entity
public class HistoryPojo {

    @Id(autoincrement = true)
    private Long historyId;

    @NotNull
    private Long id;

    @NotNull
    private String newsId;

    @NotNull
    private String userId;

    @NotNull
    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date created;

    @NotNull
    private String ctype;

    @ToOne(joinProperty = "newsId")
    private NewsListVo newsListVo;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 919382117)
    private transient HistoryPojoDao myDao;

    @Generated(hash = 1363469976)
    public HistoryPojo(Long historyId, @NotNull Long id, @NotNull String newsId, @NotNull String userId,
            @NotNull Date created, @NotNull String ctype) {
        this.historyId = historyId;
        this.id = id;
        this.newsId = newsId;
        this.userId = userId;
        this.created = created;
        this.ctype = ctype;
    }

    @Generated(hash = 608983045)
    public HistoryPojo() {
    }

    public Long getHistoryId() {
        return this.historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreated() {
        return this.created;
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

    @Generated(hash = 1178917296)
    private transient String newsListVo__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 374945028)
    public NewsListVo getNewsListVo() {
        String __key = this.newsId;
        if (newsListVo__resolvedKey == null || newsListVo__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NewsListVoDao targetDao = daoSession.getNewsListVoDao();
            NewsListVo newsListVoNew = targetDao.load(__key);
            synchronized (this) {
                newsListVo = newsListVoNew;
                newsListVo__resolvedKey = __key;
            }
        }
        return newsListVo;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1156123892)
    public void setNewsListVo(@NotNull NewsListVo newsListVo) {
        if (newsListVo == null) {
            throw new DaoException(
                    "To-one property 'newsId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.newsListVo = newsListVo;
            newsId = newsListVo.getId();
            newsListVo__resolvedKey = newsId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1783563469)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHistoryPojoDao() : null;
    }



}

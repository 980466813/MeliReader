package com.lning.melireader.core.repository.db.pojo;

import com.lning.melireader.core.repository.db.converter.DateConverter;
import com.lning.melireader.core.repository.http.bean.NewsListVo;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.lning.melireader.core.repository.db.dao.DaoSession;
import com.lning.melireader.core.repository.db.dao.NewsListVoDao;
import com.lning.melireader.core.repository.db.dao.CollectionPojoDao;

/**
 * Created by Ning on 2019/2/14.
 */

@Entity
public class CollectionPojo {

    @Id(autoincrement = true)
    private Long collectionId;

    private Long id;

    @NotNull
    private String userId;

    private String newsId;

    private String tag;

    private String ctype;

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date created;

    @Convert(converter = DateConverter.class, columnType = Long.class)
    private Date updated;

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
    @Generated(hash = 910532317)
    private transient CollectionPojoDao myDao;


    @Generated(hash = 883910369)
    public CollectionPojo(Long collectionId, Long id, @NotNull String userId, String newsId,
            String tag, String ctype, Date created, Date updated) {
        this.collectionId = collectionId;
        this.id = id;
        this.userId = userId;
        this.newsId = newsId;
        this.tag = tag;
        this.ctype = ctype;
        this.created = created;
        this.updated = updated;
    }

    @Generated(hash = 2133241797)
    public CollectionPojo() {
    }

    @Generated(hash = 1178917296)
    private transient String newsListVo__resolvedKey;

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreated() {
        return created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

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
    @Generated(hash = 1787586255)
    public void setNewsListVo(NewsListVo newsListVo) {
        synchronized (this) {
            this.newsListVo = newsListVo;
            newsId = newsListVo == null ? null : newsListVo.getId();
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
    @Generated(hash = 524522867)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCollectionPojoDao() : null;
    }
}

package com.lning.melireader.core.repository.db.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lning.melireader.core.repository.db.converter.DateConverter;
import com.lning.melireader.core.repository.http.bean.NewsListVo;
import java.util.Date;

import com.lning.melireader.core.repository.db.pojo.HistoryPojo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "HISTORY_POJO".
*/
public class HistoryPojoDao extends AbstractDao<HistoryPojo, Long> {

    public static final String TABLENAME = "HISTORY_POJO";

    /**
     * Properties of entity HistoryPojo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property HistoryId = new Property(0, Long.class, "historyId", true, "_id");
        public final static Property Id = new Property(1, Long.class, "id", false, "ID");
        public final static Property NewsId = new Property(2, String.class, "newsId", false, "NEWS_ID");
        public final static Property UserId = new Property(3, String.class, "userId", false, "USER_ID");
        public final static Property Created = new Property(4, long.class, "created", false, "CREATED");
        public final static Property Ctype = new Property(5, String.class, "ctype", false, "CTYPE");
    }

    private DaoSession daoSession;

    private final DateConverter createdConverter = new DateConverter();

    public HistoryPojoDao(DaoConfig config) {
        super(config);
    }
    
    public HistoryPojoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"HISTORY_POJO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: historyId
                "\"ID\" INTEGER NOT NULL ," + // 1: id
                "\"NEWS_ID\" TEXT NOT NULL ," + // 2: newsId
                "\"USER_ID\" TEXT NOT NULL ," + // 3: userId
                "\"CREATED\" INTEGER NOT NULL ," + // 4: created
                "\"CTYPE\" TEXT NOT NULL );"); // 5: ctype
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"HISTORY_POJO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, HistoryPojo entity) {
        stmt.clearBindings();
 
        Long historyId = entity.getHistoryId();
        if (historyId != null) {
            stmt.bindLong(1, historyId);
        }
        stmt.bindLong(2, entity.getId());
        stmt.bindString(3, entity.getNewsId());
        stmt.bindString(4, entity.getUserId());
        stmt.bindLong(5, createdConverter.convertToDatabaseValue(entity.getCreated()));
        stmt.bindString(6, entity.getCtype());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, HistoryPojo entity) {
        stmt.clearBindings();
 
        Long historyId = entity.getHistoryId();
        if (historyId != null) {
            stmt.bindLong(1, historyId);
        }
        stmt.bindLong(2, entity.getId());
        stmt.bindString(3, entity.getNewsId());
        stmt.bindString(4, entity.getUserId());
        stmt.bindLong(5, createdConverter.convertToDatabaseValue(entity.getCreated()));
        stmt.bindString(6, entity.getCtype());
    }

    @Override
    protected final void attachEntity(HistoryPojo entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public HistoryPojo readEntity(Cursor cursor, int offset) {
        HistoryPojo entity = new HistoryPojo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // historyId
            cursor.getLong(offset + 1), // id
            cursor.getString(offset + 2), // newsId
            cursor.getString(offset + 3), // userId
            createdConverter.convertToEntityProperty(cursor.getLong(offset + 4)), // created
            cursor.getString(offset + 5) // ctype
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, HistoryPojo entity, int offset) {
        entity.setHistoryId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.getLong(offset + 1));
        entity.setNewsId(cursor.getString(offset + 2));
        entity.setUserId(cursor.getString(offset + 3));
        entity.setCreated(createdConverter.convertToEntityProperty(cursor.getLong(offset + 4)));
        entity.setCtype(cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(HistoryPojo entity, long rowId) {
        entity.setHistoryId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(HistoryPojo entity) {
        if(entity != null) {
            return entity.getHistoryId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(HistoryPojo entity) {
        return entity.getHistoryId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getNewsListVoDao().getAllColumns());
            builder.append(" FROM HISTORY_POJO T");
            builder.append(" LEFT JOIN NEWS_LIST_VO T0 ON T.\"NEWS_ID\"=T0.\"ID\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected HistoryPojo loadCurrentDeep(Cursor cursor, boolean lock) {
        HistoryPojo entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        NewsListVo newsListVo = loadCurrentOther(daoSession.getNewsListVoDao(), cursor, offset);
         if(newsListVo != null) {
            entity.setNewsListVo(newsListVo);
        }

        return entity;    
    }

    public HistoryPojo loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<HistoryPojo> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<HistoryPojo> list = new ArrayList<HistoryPojo>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<HistoryPojo> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<HistoryPojo> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
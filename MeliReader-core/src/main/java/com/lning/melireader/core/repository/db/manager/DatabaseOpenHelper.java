package com.lning.melireader.core.repository.db.manager;

import android.content.Context;
import android.util.Log;

import com.lning.melireader.core.repository.db.dao.CollectionPojoDao;
import com.lning.melireader.core.repository.db.dao.DaoMaster;
import com.lning.melireader.core.repository.db.dao.HistoryPojoDao;
import com.lning.melireader.core.repository.db.dao.NewsListVoDao;
import com.lning.melireader.core.repository.db.dao.TagPojoDao;
import com.lning.melireader.core.repository.db.dao.UserVoDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Ning on 2018/11/20.
 */

public class DatabaseOpenHelper extends DaoMaster.DevOpenHelper {
    public DatabaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.d("TAG", "onUpgrade(Database db, int oldVersion, int newVersion) ");
        MigrationHelper.migrate(db, NewsListVoDao.class);
        MigrationHelper.migrate(db, UserVoDao.class);
        MigrationHelper.migrate(db, HistoryPojoDao.class);
        MigrationHelper.migrate(db, CollectionPojoDao.class);
        MigrationHelper.migrate(db, TagPojoDao.class);
    }
}

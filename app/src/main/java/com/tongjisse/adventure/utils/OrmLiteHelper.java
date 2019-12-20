package com.tongjisse.adventure.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tongjisse.adventure.data.bean.StoryList;
import com.tongjisse.adventure.data.bean.UserInfo;
import com.tongjisse.adventure.data.bean.WishList;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

/**
 * Set up OrmLite
 *
 * @author Feifan Wang
 */
public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAB_NAME = "orm_lite_table_name";
    private static OrmLiteHelper sORMLITEHELPER;
    private Map<String, Dao> daos = new HashMap<>();

    private OrmLiteHelper(Context context) {
        super(context, TAB_NAME, null, 1);
    }

    /**
     * 新建 OrmLiteHelper 实例，使用单例模式
     */
    public static void createInstance(Context context) {
        Log.d(TAG, "createInstance: " + "helper created");
        if (sORMLITEHELPER == null) {
            synchronized (OrmLiteHelper.class) {
                sORMLITEHELPER = new OrmLiteHelper(context);
            }
        }
    }

    /**
     * 获取 OrmLiteHelper 实例
     */
    public static OrmLiteHelper getInstance() {
        return sORMLITEHELPER;
    }

    public Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        daos.clear();
        super.close();
    }

    /**
     * 新建表
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTable(connectionSource, WishList.class);
            TableUtils.createTable(connectionSource, StoryList.class);
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 删除表并重建数据库
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, UserInfo.class, true);
            TableUtils.dropTable(connectionSource, WishList.class, true);
            TableUtils.dropTable(connectionSource, StoryList.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
        onCreate(database, connectionSource);
    }
}

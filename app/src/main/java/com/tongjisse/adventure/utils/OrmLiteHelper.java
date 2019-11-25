package com.tongjisse.adventure.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tongjisse.adventure.model.bean.Story;
import com.tongjisse.adventure.model.bean.UserInfo;
import com.tongjisse.adventure.model.bean.WishList;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAB_NAME = "orm_lite_table_name";
    private static OrmLiteHelper sORMLITEHELPER;
    private Map<String, Dao> daos = new HashMap<>();

    private OrmLiteHelper(Context context) {
        super(context, TAB_NAME, null, 1);
    }

    public static void createInstance(Context context) {
        Log.d(TAG, "createInstance: "+"helper created");
        if (sORMLITEHELPER == null) {
            synchronized (OrmLiteHelper.class) {
                if (sORMLITEHELPER == null) {
                    sORMLITEHELPER = new OrmLiteHelper(context);
                }
            }
        }
    }

    public static OrmLiteHelper getInstance() {
//        if (sORMLITEHELPER == null) {
//            synchronized (OrmLiteHelper.class) {
//                if (sORMLITEHELPER == null) {
//                    sORMLITEHELPER = new OrmLiteHelper(context);
//                }
//            }
//        }
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
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTable(connectionSource, Story.class);
            TableUtils.createTable(connectionSource, WishList.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, UserInfo.class, true);
            TableUtils.dropTable(connectionSource, Story.class, true);
            TableUtils.dropTable(connectionSource, WishList.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }
}

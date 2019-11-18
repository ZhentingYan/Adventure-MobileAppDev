package com.tongjisse.adventure.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.tongjisse.adventure.model.bean.UserInfo;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import java.sql.SQLException;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class UserInfoDao {
    private Dao<UserInfo, Long> userDao;

    public UserInfoDao() {
        OrmLiteHelper helper = OrmLiteHelper.getInstance();
        Log.d(TAG, "UserInfoDao: "+"inited" + UserInfo.class + helper);
        try {
            userDao = helper.getDao(UserInfo.class);
            if (userDao == null) {
                Log.d(TAG, "UserInfoDao: " + "NULL!");
            }
            userDao.createIfNotExists(new UserInfo("FNAME", "LNAME",
                    "admin123", "test@admin.com", "18918911111"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增
     *
     * @param info
     */
    public void addInfo(UserInfo info) {
        if (userDao == null)
            return;
        try {
            userDao.createIfNotExists(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删
     *
     * @param info
     */
    public void delInfo(UserInfo info) {
        try {
            userDao.delete(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有表项
     */
    public void delAllInfo() {
        try {
            List<UserInfo> allInfo = this.query();
            userDao.delete(allInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改
     *
     * @param info
     */
    public void updateInfo(UserInfo info) {
        try {
            userDao.update(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Email查
     *
     * @param email
     */
    public UserInfo queryInfoByEmail(String email) {
        try {
            List<UserInfo> tempUserList = userDao.queryBuilder()
                    .where().eq("emailAddress", email).query();
            if (tempUserList.size() < 1) {
                return null;
            } else {
                Log.d(TAG, "queryInfoByEmail: "+tempUserList.toString()+tempUserList.size());
                return tempUserList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过id获取
     *
     * @param id
     */
    public UserInfo queryInfoById(long id) {
        try {
            return userDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取user_info表的全部内容
     */
    public List<UserInfo> query() {
        try {
            return userDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
package com.tongjisse.adventure.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.tongjisse.adventure.model.bean.Story;
import com.tongjisse.adventure.model.bean.UserInfo;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import java.sql.SQLException;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class StoryDao {
    private Dao<Story, Long> storyDao;

    public StoryDao() {
        OrmLiteHelper helper = OrmLiteHelper.getInstance();
        Log.d(TAG, "UserInfoDao: "+"inited" + UserInfo.class + helper);
        try {
            storyDao = helper.getDao(UserInfo.class);
            if (storyDao == null) {
                Log.d(TAG, "UserInfoDao: " + "NULL!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增
     *
     * @param story
     */
    public void addStory(Story story) {
        if (storyDao == null)
            return;
        try {
            storyDao.createIfNotExists(story);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删
     *
     * @param story
     */
    public void delInfo(Story story) {
        try {
            storyDao.delete(story);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有表项
     */
    public void delAllStory() {
        try {
            List<Story> allStory = this.query();
            storyDao.delete(allStory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改
     *
     * @param story
     */
    public void updateStory(Story story) {
        try {
            storyDao.update(story);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 通过Email查
//     *
//     * @param email
//     */
//    public UserInfo queryInfoByEmail(String email) {
//        try {
//            List<UserInfo> tempUserList = userDao.queryBuilder()
//                    .where().eq("emailAddress", email).query();
//            if (tempUserList.size() < 1) {
//                return null;
//            } else {
//                Log.d(TAG, "queryInfoByEmail: "+tempUserList.toString()+tempUserList.size());
//                return tempUserList.get(0);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 通过id获取
     *
     * @param id
     */
    public Story queryInfoById(long id) {
        try {
            return storyDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取story表的全部内容
     */
    public List<Story> query() {
        try {
            return storyDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
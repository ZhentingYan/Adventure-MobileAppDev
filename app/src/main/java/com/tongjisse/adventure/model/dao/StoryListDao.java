package com.tongjisse.adventure.model.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.tongjisse.adventure.data.bean.StoryList;
import com.tongjisse.adventure.data.bean.UserInfo;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import java.sql.SQLException;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class StoryListDao {
    private Dao<StoryList, Long> storyDao;

    public StoryListDao() {
        OrmLiteHelper helper = OrmLiteHelper.getInstance();
        try {
            storyDao = helper.getDao(StoryList.class);
            Log.d(TAG, "StoryListDao: "+storyDao);
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
     * @param storyList
     */
    public void addStory(StoryList storyList) {
        if (storyDao == null)
            return;
        try {
            storyList.setId(storyList.getTime()+ storyList.getUser().toString());
            storyDao.createIfNotExists(storyList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删
     *
     * @param storyList
     */
    public void delInfo(StoryList storyList) {
        try {
            storyDao.delete(storyList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除所有表项
     */
    public void delAllStory() {
        try {
            List<StoryList> allStoryList = this.query();
            storyDao.delete(allStoryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改
     *
     * @param storyList
     */
    public void updateStory(StoryList storyList) {
        try {
            storyDao.update(storyList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Email查
     *
     * @param email
     * @return List<StoryList>
     */
    public List<StoryList> queryStoryByEmail(String email) {
        UserInfoDao userDao = new UserInfoDao();
        UserInfo user = userDao.queryInfoByEmail(email);
        List<StoryList> tempStoryLists = queryStoryByUser(user);
        if (tempStoryLists.size() < 1) {
            return null;
        } else {
            Log.d(TAG, "queryInfoByUser: "+ tempStoryLists.toString()+ tempStoryLists.size());
            return tempStoryLists;
        }
    }

    /**
     * 通过User查
     *
     * @param user
     * @return List<StoryList>
     */
    public List<StoryList> queryStoryByUser(UserInfo user) {
        try {
            List<StoryList> tempStoryLists = storyDao.queryBuilder()
                    .where().eq("user", user).query();
            if (tempStoryLists.size() < 1) {
                return null;
            } else {
                Log.d(TAG, "queryInfoByUser: "+ tempStoryLists.toString()+ tempStoryLists.size());
                return tempStoryLists;
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
    public StoryList queryStoryById(String id) {
        try {
            return storyDao.queryForEq("id",id).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取story表的全部内容
     */
    public List<StoryList> query() {
        try {
            return storyDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
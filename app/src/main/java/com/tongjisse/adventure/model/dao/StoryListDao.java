package com.tongjisse.adventure.model.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.tongjisse.adventure.data.bean.StoryList;
import com.tongjisse.adventure.data.bean.UserInfo;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * StoryList database operation
 *
 * @author Feifan Wang
 */
public class StoryListDao {
    private static String DISTRICT = "district";
    private static String USER = "user";
    private static String SCENE = "scene";
    private static String TITLE = "title";
    private Dao<StoryList, Long> storyDao;

    public StoryListDao() {
        OrmLiteHelper helper = OrmLiteHelper.getInstance();
        try {
            storyDao = helper.getDao(StoryList.class);
            Log.d(TAG, "StoryListDao: " + storyDao);
            if (storyDao == null) {
                Log.d(TAG, "StoryListDao: " + "NULL!");
            }
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 增
     *
     * @param storyList: StoryList
     */
    public void addStory(StoryList storyList) {
        if (storyDao == null)
            return;
        try {
            storyList.setId(storyList.getTime() + storyList.getUser().toString());
            storyDao.createIfNotExists(storyList);
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 删
     *
     * @param storyList: StoryList
     */
    public void delStory(StoryList storyList) {
        try {
            storyDao.delete(storyList);
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
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
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 改
     *
     * @param storyList: StoryList
     */
    public void updateStory(StoryList storyList) {
        try {
            storyDao.update(storyList);
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 通过Email查
     *
     * @param email: String
     * @return List<StoryList>
     */
    public List<StoryList> queryStoryByEmail(String email) {
        UserInfoDao userDao = new UserInfoDao();
        UserInfo user = userDao.queryInfoByEmail(email);
        List<StoryList> tempStoryLists = queryStoryByUser(user);
        if (tempStoryLists == null || tempStoryLists.isEmpty()) {
            return Collections.emptyList();
        } else {
            return tempStoryLists;
        }
    }

    /**
     * 通过User查
     *
     * @param user: UserInfo
     * @return List<StoryList>
     */
    public List<StoryList> queryStoryByUser(UserInfo user) {
        try {
            List<StoryList> tempStoryLists = storyDao.queryBuilder()
                    .where().eq(USER, user).query();
            if (tempStoryLists.isEmpty()) {
                return Collections.emptyList();
            } else {
                Log.d(TAG, "queryStoryByUser: " + tempStoryLists.toString() + tempStoryLists.size());
                return tempStoryLists;
            }
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
        return Collections.emptyList();
    }

    /**
     * 通过district查
     *
     * @param district: String
     * @return List<StoryList>
     */
    public List<StoryList> queryStoryByDistrict(String district) {
        try {
            List<StoryList> tempStoryLists = storyDao.queryBuilder()
                    .where()
                    .eq(DISTRICT, district)
                    .query();
            if (tempStoryLists.isEmpty()) {
                return Collections.emptyList();
            } else {
                Log.d(TAG, "queryStoryByDistrict: " + tempStoryLists.toString() + tempStoryLists.size());
                return tempStoryLists;
            }
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
        return Collections.emptyList();
    }

    /**
     * 通过title在有限制的district内查
     *
     * @param title:    String
     * @param district: String
     * @return List<StoryList>
     */
    public List<StoryList> queryStoryByTitleWithDistrict(String title, String district) {
        try {
            List<StoryList> tempStoryLists = storyDao.queryBuilder()
                    .where()
                    .like(TITLE, "%" + title + "%")
                    .and()
                    .eq(DISTRICT, district)
                    .query();
            if (tempStoryLists.isEmpty()) {
                return Collections.emptyList();
            } else {
                Log.d(TAG, "queryStoryByTitleWithDistrict: " + tempStoryLists.toString() + tempStoryLists.size());
                return tempStoryLists;
            }
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
        return Collections.emptyList();
    }

    /**
     * 通过scene在有限制的district内查
     *
     * @param scene:    String
     * @param district: String
     * @return List<StoryList>
     */
    public List<StoryList> queryStoryBySceneWithDistrict(String scene, String district) {
        try {
            List<StoryList> tempStoryLists = storyDao.queryBuilder()
                    .where()
                    .like(SCENE, "%" + scene + "%")
                    .and()
                    .eq(DISTRICT, district)
                    .query();
            if (tempStoryLists.isEmpty()) {
                return Collections.emptyList();
            } else {
                Log.d(TAG, "queryStoryBySceneWithDistrict: " + tempStoryLists.toString() + tempStoryLists.size());
                return tempStoryLists;
            }
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
        return Collections.emptyList();
    }

    /**
     * 通过id获取
     *
     * @param id
     * @return StoryList
     */
    public StoryList queryStoryById(String id) {
        try {
            return storyDao.queryForEq("id", id).get(0);
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * 获取story表的全部内容
     *
     * @return List<StoryList>
     */
    public List<StoryList> query() {
        try {
            return storyDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
        }
        return Collections.emptyList();
    }
}
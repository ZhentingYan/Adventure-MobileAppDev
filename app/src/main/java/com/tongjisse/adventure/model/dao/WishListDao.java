package com.tongjisse.adventure.model.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.tongjisse.adventure.data.bean.WishList;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * WishList database operation
 *
 * @author Feifan Wang
 */
public class WishListDao {
    private Dao<WishList, Long> wishDao;

    public WishListDao() {
        OrmLiteHelper helper = OrmLiteHelper.getInstance();
        try {
            wishDao = helper.getDao(WishList.class);
            if (wishDao == null) {
                Log.d(TAG, "UserInfoDao: " + "NULL!");
            }
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * 增
     *
     * @param wish
     */
    public void addWish(WishList wish) {
        if (wishDao == null)
            return;
        try {
            wishDao.createIfNotExists(wish);
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * 删
     *
     * @param wish
     */
    public void delInfo(WishList wish) {
        try {
            wishDao.delete(wish);
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * 删除所有表项
     */
    public void delAllWish() {
        try {
            List<WishList> allWish = this.query();
            wishDao.delete(allWish);
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * 心愿状态修改
     *
     * @param wish
     * @param state
     */
    public void updateState(WishList wish, int state) {
        try {
            wish.setState(state);
            wishDao.update(wish);
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
    }

    /**
     * 通过id获取
     *
     * @param id
     * @return WishList
     */
    public WishList queryWishById(long id) {
        try {
            return wishDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
        return null;
    }

    /**
     * 获取wish_list表的全部内容
     *
     * @return List<WishList>
     */
    public List<WishList> query() {
        try {
            return wishDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
        return Collections.emptyList();
    }

    /**
     * 通过user在限制的district中查
     *
     * @param user: String
     * @param district: String
     * @return List<WishList>
     */
    public List<WishList> queryByUserAndDistrict(String user, String district) {
        try {
            return wishDao.queryBuilder().where().eq("user", user).and().eq("district", district).query();
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
        return Collections.emptyList();
    }

    /**
     * 通过user查
     *
     * @param user: String
     * @return List<WishList>
     */
    public List<WishList> queryByUser(String user) {
        try {
            return wishDao.queryForEq("user", user);
        } catch (SQLException e) {
            Log.e(TAG,e.toString());
        }
        return Collections.emptyList();
    }
}
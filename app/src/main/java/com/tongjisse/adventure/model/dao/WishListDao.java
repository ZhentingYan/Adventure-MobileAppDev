package com.tongjisse.adventure.model.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.tongjisse.adventure.data.bean.WishList;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import java.sql.SQLException;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
    public WishList queryWishById(long id) {
        try {
            return wishDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取wish_list表的全部内容
     */
    public List<WishList> query() {
        try {
            return wishDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WishList> queryByUserAndDistrict(String user, String district) {
        try {
            return wishDao.queryBuilder().where().eq("user", user).and().eq("district", district).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<WishList> queryByUser(String user) {
        try {
            return wishDao.queryForEq("user", user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
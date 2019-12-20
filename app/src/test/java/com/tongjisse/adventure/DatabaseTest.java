package com.tongjisse.adventure;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lzy.imagepicker.bean.ImageItem;
import com.tongjisse.adventure.data.bean.UserInfo;
import com.tongjisse.adventure.model.dao.UserInfoDao;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DatabaseTest extends Activity {
    private UserInfoDao mOrmDao;

    @Before
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        OrmLiteHelper.createInstance(this);
        mOrmDao = new UserInfoDao();
        Log.d("CC", "onCreate: "+"Ss");
        initOrmInfo();
    }

    @After
    private void del(){
        mOrmDao.delAllInfo();
        queryEmail("1e");
    }

    @Test
    private void queryOrmOri() {
        List<UserInfo> list = mOrmDao.query();
        for (UserInfo info : list) {
            Log.d("mutex", info.toString()+" "+list.size());
        }
    }
    @Test
    private void queryOrmOri(long id) {
        UserInfo info = mOrmDao.queryInfoById(id);
        Log.d("mutexi", info.toString());
    }
    @Test
    private void queryEmail(String email){
        UserInfo info = mOrmDao.queryInfoByEmail(email);
        Log.d("QUERYEMAIL", "queryEmail: "+info.toString());
    }
    private void initOrmInfo() {
        for (int i = 0; i < 10; i++) {
            UserInfo info = new UserInfo("name" + i,"la"+i, "psw"+i + 20,i+"e","2",2,new ImageItem());
            mOrmDao.addInfo(info);
        }
        Log.d("INITDAO", "initOrmInfo: "+mOrmDao);
    }
}

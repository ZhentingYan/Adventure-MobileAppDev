package com.tongjisse.adventure.view.views.Welcome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tongjisse.adventure.R;
import com.tongjisse.adventure.dao.UserInfoDao;
import com.tongjisse.adventure.model.bean.UserInfo;
import com.tongjisse.adventure.utils.OrmLiteHelper;

import java.util.List;

public class DatabaseTest extends Activity {
    private UserInfoDao mOrmDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mOrmDao = new UserInfoDao(getApplicationContext());
        Log.d("CC", "onCreate: "+"Ss");
        initOrmInfo();
        queryOrmOri();
        queryOrmOri(1);
    }
    private void queryOrmOri() {
        List<UserInfo> list = mOrmDao.query();
        for (UserInfo info : list) {
            Log.i("mutex", info.toString());
        }
    }
    private void queryOrmOri(long id) {
        UserInfo info = mOrmDao.queryInfoById(id);
        Log.w("mutex", info.toString());
    }
    private void initOrmInfo() {
        for (int i = 0; i < 10; i++) {
            UserInfo info = new UserInfo(i, "name" + i,"la"+i, "psw"+i + 20,"1","2");
            mOrmDao.addInfo(info);
        }
        Log.d("INITDAO", "initOrmInfo: "+mOrmDao);
    }
}

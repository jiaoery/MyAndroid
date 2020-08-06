package jixiang.com.myandroid;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by jixiang52002 on 2016/11/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initSetting();
    }

    /**
     * 配置设置参数
     */
    private void initSetting() {
    }
}

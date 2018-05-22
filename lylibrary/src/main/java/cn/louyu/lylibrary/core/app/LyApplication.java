package cn.louyu.lylibrary.core.app;

import android.app.Application;

/**
 * Created by sdj003 on 2018/5/19.
 */

public abstract class LyApplication extends Application {

    @Override
    public void onCreate() {
        init();
        super.onCreate();
    }

    protected abstract void init();
}

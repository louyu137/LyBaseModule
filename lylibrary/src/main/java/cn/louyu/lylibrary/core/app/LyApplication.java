package cn.louyu.lylibrary.core.app;


/**
 * Created by sdj003 on 2018/5/19.
 */

public abstract class LyApplication extends android.support.multidex.MultiDexApplication {

    @Override
    public void onCreate() {
        init();
        super.onCreate();
    }

    protected abstract void init();
}

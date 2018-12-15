package cn.louyu.lymvpframework.utils.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by sdj003 on 2018/9/26.
 */

public class ActivityPageManager {
    private static CopyOnWriteArrayList<Activity> mActivityStack;
    private static ActivityPageManager instance;


    /**
     * 获取Activity管理者单一实例
     */
    public static ActivityPageManager getInstance() {
        if (instance == null) {
            synchronized (ActivityPageManager.class){
                if(instance==null){
                    synchronized (ActivityPageManager.class) {
                        instance = new ActivityPageManager();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new CopyOnWriteArrayList<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        try {
            if (activity != null) {
                mActivityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从栈中移除此Activity
     * */
    public void removeActivity(Activity activity){
        mActivityStack.remove(activity);
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                killActivity(activity);
            }
        }
    }
    /**
     * 获取栈顶的Activity
     */
    public Activity currentActivity() {
        Activity activity = mActivityStack.get(mActivityStack.size()-1);
        return activity;
    }

    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

//    /**
//     * 结束所有Activity除了Login
//     */
//    public void killAllActivityNoLogin() {
//        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
//            if (null != mActivityStack.get(i)) {
//                if (mActivityStack.get(i).getClass()!=LoginActivity.class) {
//                    mActivityStack.get(i).finish();
//                }
//            }
//        }
//        mActivityStack.clear();
//    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package cn.louyu.lylibrary.core.utils.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Stack;

/**
 * Activity管理类
 */
public class ActivityPageManager {
    private static Stack<Activity> activityStack;
    private static ActivityPageManager instance;

    /**
     * 私有化构造函数
     */
    private ActivityPageManager() {}

    /**
     * 获取Activity管理者
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
     * 添加Activity到栈中
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 从栈中删除Activity
     */
    public boolean removeActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
       return activityStack.remove(activity);
    }

    /**
     * 获取栈顶的Activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束掉栈顶的Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束掉一个Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 根据类结束掉一个Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 关闭所有的Activity
     */
    public void finishAllActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            for (int i = 0, size = activityStack.size();
                 i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 释放View所有占用的资源
     * @param view
     */
    public static void unbindReferences(View view) {
        try {
            if (view != null) {
                view.destroyDrawingCache();
                unbindViewReferences(view);
                if (view instanceof ViewGroup) {
                    unbindViewGroupReferences((ViewGroup) view);
                }
            }
        } catch (Throwable e) {
            // whatever exception is thrown just ignore it because a crash is
            // always worse than this method not doing what it's supposed to do
        }
    }
    /**
     * 释放ViewGroup所有占用的资源
     * @param viewGroup
     */
    private static void unbindViewGroupReferences(ViewGroup viewGroup) {
        int nrOfChildren = viewGroup.getChildCount();
        for (int i = 0; i < nrOfChildren; i++) {
            View view = viewGroup.getChildAt(i);
            unbindViewReferences(view);
            if (view instanceof ViewGroup)
                unbindViewGroupReferences((ViewGroup) view);
        }
        try {
            viewGroup.removeAllViews();
        } catch (Throwable mayHappen) {
            // AdapterViews, ListViews and potentially other ViewGroups don't
            // support the removeAllViews operation
        }
    }

    /**
     * 设置所有监听为null
     * */
    @SuppressWarnings("deprecation")
    private static void unbindViewReferences(View view) {
        // set all listeners to null (not every view and not every API level
        // supports the methods)
        try {
            view.setOnClickListener(null);
            view.setOnCreateContextMenuListener(null);
            view.setOnFocusChangeListener(null);
            view.setOnKeyListener(null);
            view.setOnLongClickListener(null);
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
        }
        // set background to null
        Drawable d = view.getBackground();
        if (d != null) {
            d.setCallback(null);
        }
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            d = imageView.getDrawable();
            if (d != null) {
                d.setCallback(null);
            }
            imageView.setImageDrawable(null);
            imageView.setBackgroundDrawable(null);
        }
        // destroy WebView
        if (view instanceof WebView) {
            WebView webview = (WebView) view;
            webview.stopLoading();
            webview.clearFormData();
            webview.clearDisappearingChildren();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.destroyDrawingCache();
            webview.destroy();
            webview = null;
        }
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            try {
                listView.removeAllViewsInLayout();
            } catch (Throwable mayHappen) {

            }
            ((ListView) view).destroyDrawingCache();
        }
    }

    /**
     * exit System   退出系统
     * @param context
     */
    public boolean exit(Context context) {
//        exit(context, true);
        finishAllActivity();
        if(context.getApplicationContext()!=null) {
            return false;
        }
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
        return true;
    }


    /**
     * activity页面跳转
     * @param activityContext 当前activity 上下文
     * @param cls 要跳转到的地方
     * */
    public static void jumpToOtherPage(Activity activityContext, Class<?> cls){
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(activityContext,android.R.anim.fade_in, android.R.anim.fade_out);
        ActivityCompat.startActivity(activityContext, new Intent(activityContext, cls), compat.toBundle());
    }

}
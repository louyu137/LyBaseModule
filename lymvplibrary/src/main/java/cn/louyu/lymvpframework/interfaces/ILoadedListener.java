package cn.louyu.lymvpframework.interfaces;

/**
 * Created by sdj003 on 2018/10/9.
 */

public interface ILoadedListener {
    /**
     * BaseActivity 加载布局文件之前回调
     * */
    void onBeforeViewLoad();
    /**
     * 当BaseActivity 加载布局文件之后回调(getLayoutId 后回调)
     * */
    void onViewLoaded();


}

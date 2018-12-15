package cn.louyu.lymvpframework.interfaces;

import android.support.annotation.LayoutRes;

public interface UIInterface{

    /**
     * 得到布局文件
     *
     * @return 布局文件Id
     */
    @LayoutRes int getLayoutId();

    /**
     * 做一些初始化操作
     * */
    void initOperation();

}

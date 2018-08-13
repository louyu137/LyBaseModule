package cn.louyu.lylibrary.core.interfaces;

import android.support.annotation.LayoutRes;

public interface UIInterface {

    /**
     * 得到布局文件
     *
     * @return 布局文件Id
     */
    @LayoutRes int getLayoutId();

    /**
     * 初始化View
     */
    void initView();

    /**
     * 初始化界面数据
     */
    void initData();

    /**
     * 绑定监听器与适配器
     */
    void initListener();
}

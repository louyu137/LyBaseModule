package cn.louyu.lylibrary.core.interfaces;

import android.view.View;

/**
 * Created by sdj003 on 2018/5/19.
 */

public interface IFragmentUI {
    /**
     * 得到布局文件
     *
     * @return 布局文件Id
     */
    int getLayoutId();

    /**
     * 初始化View
     */
    void initView(View view);

    /**
     * 初始化界面数据
     */
    void initData();

    /**
     * 绑定监听器与适配器
     */
    void initListener();
}

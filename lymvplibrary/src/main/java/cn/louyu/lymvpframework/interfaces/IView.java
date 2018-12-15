package cn.louyu.lymvpframework.interfaces;
import android.content.Context;

/**
 * V层接口
 * */
public interface IView {
    /**
     * 显示加载框（一般在执行耗时操作的时候，让用户等待操作完成）
     * */
    void showLoading();

    /**
     *  隐藏加载框（一般在耗时操作完成后关闭加载框，与显示加载框对应）
     * */
    void hideLoading();

    /**
     * 弹出吐司（一般用来展示提示信息）
     * */
    void showToast(String msg);
    /**
     * 获取上下文对象
     * */
    Context getContext();
}
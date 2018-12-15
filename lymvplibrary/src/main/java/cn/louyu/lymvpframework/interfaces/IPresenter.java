package cn.louyu.lymvpframework.interfaces;

/**
 * Created by sdj003 on 2018/9/27.
 */

public interface IPresenter<V extends IView> {
    /**
     * 绑定view，一般在初始化中调用该方法
     */
    void attachView(V mvpView);

    /**
     * 断开view，一般在onDestroy中调用
     */
    void detachView();

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    boolean isViewAttached();

    /**
     * 获取V
     * */
    V getView();
}

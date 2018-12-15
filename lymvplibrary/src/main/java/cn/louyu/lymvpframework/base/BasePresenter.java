package cn.louyu.lymvpframework.base;


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.louyu.lymvpframework.interfaces.IPresenter;
import cn.louyu.lymvpframework.interfaces.IView;
import cn.louyu.lymvpframework.utils.annotation.Injector;
import cn.louyu.lymvpframework.utils.proxy.ProxyUtils;

/**
 * BasePresenter MVP 模式的基类 Presenter
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {
    private WeakReference<V> weakView;

    protected V proxyView;

    public BasePresenter(){
        Injector.inject(this);
    }

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    @Override
    public void attachView(V view) {
        //创建需要被代理的类
        this.weakView = new WeakReference<>(view);
        // 创建被代理类的委托类,之后想要调用被代理类的方法时，都会委托给这个类的invoke(Object proxy, Method method, Object[] args)方法
        MvpViewInvocationHandler invocationHandler = new MvpViewInvocationHandler(this.weakView.get());
        //生成代理类（不需要再判断isViewAttached）
        proxyView=ProxyUtils.newProxyInstance(this.weakView.get().getClass(),invocationHandler);
    }
    /**
     * 断开view，一般在onDestroy中调用
     */
    @Override
    public void detachView() {
        if (this.weakView != null) {
            this.weakView.clear();
            this.weakView = null;
        }
    }
    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    @Override
    public boolean isViewAttached() {
        return this.weakView != null && this.weakView.get() != null;
    }
    /**
     * 获取连接的view
     */
    @Override
    public V getView() {
        return proxyView;
    }


    private class MvpViewInvocationHandler implements InvocationHandler {

        private Object mvpView;

        public MvpViewInvocationHandler(Object mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object arg0, Method method, Object[] arg2) throws Throwable {
            if (isViewAttached()) {
                return method.invoke(mvpView, arg2);
            }
            return null;
        }
    }


}

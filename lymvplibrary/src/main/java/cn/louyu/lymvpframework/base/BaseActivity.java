package cn.louyu.lymvpframework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.louyu.lymvpframework.interfaces.IPresenter;
import cn.louyu.lymvpframework.interfaces.IView;
import cn.louyu.lymvpframework.interfaces.ILoadedListener;
import cn.louyu.lymvpframework.interfaces.UIInterface;
import cn.louyu.lymvpframework.utils.manager.ActivityPageManager;
import cn.louyu.lymvpframework.utils.tools.ToastHelper;
import cn.louyu.lymvpframework.utils.annotation.Injector;
import cn.louyu.lymvpframework.weight.LoadingDialog;

/**
 * 基类Activity
 */

public abstract class BaseActivity extends AppCompatActivity implements IView,UIInterface,ILoadedListener {

    //所有的业务提供商
    private List<IPresenter> presenters=new CopyOnWriteArrayList<>();
    /**
     * 当前页面上下文
     * */
    protected Activity context;
    /**
     * 加载对话框
     * */
    private LoadingDialog loadingDialog;

    {
        //设置Context
        this.context=this;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置没有标题栏
        onBeforeViewLoad();
        //得到布局文件
        setContentView(getLayoutId());
        //组件依赖注入(需要放在setContentView()后面,添加Presenter前面)
        Injector.inject(this);
        //执行onViewLoaded
        onViewLoaded();
        //把当前activity加入管理器
        ActivityPageManager.getInstance().addActivity(this);

        //初始化操作
        initOperation();
    }

    @Override
    public void initOperation() {
        /**
         * 初始化操作
         * */
    }

    @Override
    public void onViewLoaded() {
        /**
         * 布局加载完成后加载此方法
         * */
    }

    @Override
    public void onBeforeViewLoad() {
        /**
         * 布局加载之前加载此方法（不可在此方法拿到组件）
         * */
    }

    @Override
    public void showLoading() {
        if(loadingDialog==null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(false);
        }
        if(!loadingDialog.isShowing())
            loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if(this.isFinishing()) return; //少了这句可能窗口泄漏java.lang.IllegalArgumentException: View not attached to window manager
        if(loadingDialog!=null) {
            loadingDialog.cancel();
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void showToast(String msg) {
        ToastHelper.showToast(getApplicationContext(),msg);
    }

    /**
     * 添加一个IPresenter,并且绑定IView
     * */
    protected void addPersenter(IPresenter p){
        presenters.add(p);
        p.attachView(this);
    }
    /**
     * 添加多个IPresenter,并且绑定IView
     * */
    protected void addPersenter(List<IPresenter> ps){
        presenters.addAll(ps);
        for (IPresenter p :ps){
            p.attachView(this);
        }
    }
    /**
     * 移除一个IPresenter,并且解除绑定IView
     * */
    protected void removePersenter(IPresenter p){
        presenters.remove(p);
        p.detachView();
    }
    /**
     * 解除所有绑定
     * */
    protected void removeAllPersenter(){
        for (IPresenter p :presenters){
            p.detachView();
        }
        presenters.clear();
    }


    @Override
    protected void onDestroy() {
        if(loadingDialog!=null) loadingDialog.dismiss(); //销毁Dialog使其生命周期与Activity保持同步
        super.onDestroy();
        ActivityPageManager.getInstance().removeActivity(this);
        removeAllPersenter();
    }
}

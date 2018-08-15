package cn.louyu.lylibrary.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import cn.louyu.lylibrary.core.interfaces.UIInterface;
import cn.louyu.lylibrary.core.utils.manager.ActivityPageManager;
import cn.louyu.lylibrary.core.utils.ui.LoadingDialogHelper;
import cn.louyu.lylibrary.core.utils.ui.MessageDialogHelper;
import cn.louyu.lylibrary.core.utils.ui.ToastHelper;

/**
 * @author xiarui 2016/8/11
 * @description Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity implements UIInterface{

    protected Activity context;
    private LoadingDialogHelper loadingDialog;
    private MessageDialogHelper msgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置没有标题栏
        //得到布局文件
        setContentView(getLayoutId());
        //设置Activity上下文
        this.context=this;
        //把当前activity加入管理器
        ActivityPageManager.getInstance().addActivity(this);

        loadingDialog=new LoadingDialogHelper(context);
        msgDialog=new MessageDialogHelper(context);
        //初始化View
        initView();

        //初始化界面数据
        initData();

        //绑定监听器与适配器
        initListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityPageManager.getInstance().removeActivity(this);
    }


    /**
     * 显示一个Toast
     *
     * @param msg 吐司内容
     */
    protected void showToast(String msg) {
        ToastHelper.showToast(context,msg,Toast.LENGTH_SHORT);
    }

    protected void showLoadingDialog(){
        loadingDialog.showLoadingDialog("加载中...");
    }

    public void heidLoadingDialog(){
        loadingDialog.heidLoadingDialog();
    }

    public void showDialog(String msg){
        msgDialog.showDialog("提示",msg,null);
    }

    public void heidDialog(){
        msgDialog.heidDialog();
    }

    /**
     * 触摸隐藏键盘
     * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0); //强制隐藏键盘
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 返回桌面
     */
    public void toHome() {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(mHomeIntent);
        ActivityPageManager.getInstance().finishAllActivity();
    }

}

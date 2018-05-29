package cn.louyu.lylibrary.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.louyu.lylibrary.core.interfaces.IFragmentUI;
import cn.louyu.lylibrary.core.utils.ui.LoadingDialogHelper;
import cn.louyu.lylibrary.core.utils.ui.MessageDialogHelper;
import cn.louyu.lylibrary.core.utils.ui.ToastHelper;

/**
 * Created by sdj003 on 2018/5/19.
 */

public abstract class BaseFragment extends Fragment implements IFragmentUI{

    protected Activity activity;
    protected Context context;
    protected LayoutInflater inflater;
    private LoadingDialogHelper loadingDialog;
    private MessageDialogHelper msgDialog;

    /**
     * Fragment创建
     * */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.activity=getActivity();
        this.context=getContext();
        this.inflater=inflater;
        View view = inflater.inflate(getLayoutId(), null);
        loadingDialog=new LoadingDialogHelper(context);
        msgDialog=new MessageDialogHelper(context);
        initView(view);
        initData();
        initListener();
        return view;
    }


    /**
     * 弹出吐司
     * */
    protected void showToast(String msg) {
        ToastHelper.showToast(activity,msg,Toast.LENGTH_SHORT);
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
}

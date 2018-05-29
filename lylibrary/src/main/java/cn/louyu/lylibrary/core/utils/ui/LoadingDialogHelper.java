package cn.louyu.lylibrary.core.utils.ui;

import android.content.Context;

import cn.louyu.lylibrary.core.component.LoadingDialog;

/**
 * Created by sdj003 on 2018/5/27.
 */

public class LoadingDialogHelper {
    private LoadingDialog loadingDialog;
    private Context context;
    public LoadingDialogHelper(Context context){
        this.context=context;
        loadingDialog=new LoadingDialog(context);
    }

    public void showLoadingDialog(){
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    public void showLoadingDialog(String tip){
        loadingDialog.setCancelable(false);
        loadingDialog.setTip(tip);
        loadingDialog.show();
    }

    public void heidLoadingDialog(){
        if(loadingDialog!=null)
            loadingDialog.cancel();
    }
}

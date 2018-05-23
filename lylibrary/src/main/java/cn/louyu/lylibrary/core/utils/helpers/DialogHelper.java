package cn.louyu.lylibrary.core.utils.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import cn.louyu.lylibrary.core.component.LoadingDialog;

/**
 * Created by sdj003 on 2018/5/24.
 */

public class DialogHelper {

    private static AlertDialog dialog;
    private static LoadingDialog loadingDialog;
    private static AlertDialog.Builder builder;
    private static DialogHelper sInstance=null;
    private static Context _context=null;

    private DialogHelper(Context context){
        builder=new AlertDialog.Builder(context);
        loadingDialog=new LoadingDialog(context);
    }

    public static DialogHelper getInstance(Context context){
        if (sInstance == null||_context!=context) {
            synchronized (DialogHelper.class) {
                if (sInstance == null||_context!=context) {
                    synchronized (DialogHelper.class) {
                       sInstance = new DialogHelper(context);
                    }
                }
            }
        }
        return sInstance;
    }

    public AlertDialog showDialog(Context context,String title,String msg){
        heidDialog();
        dialog = builder.setTitle(title).setMessage(msg).show();
        return dialog;
    }

    public AlertDialog showDialog(String title, String msg, DialogInterface.OnClickListener positiveListener){
        heidDialog();
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK",positiveListener);
        dialog=builder.show();
        return dialog;
    }

    public AlertDialog showDialog(String title,String msg,DialogInterface.OnClickListener positiveListener,
                                     DialogInterface.OnClickListener negativeListener){
        heidDialog();
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK",positiveListener)
                .setNegativeButton("Cancel",negativeListener);
        dialog=builder.show();
        return dialog;
    }

    public void heidDialog(){
        if(dialog!=null)
            dialog.cancel();
    }

    public void showLoadingDialog(){
        heidLoadingDialog();
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    public void heidLoadingDialog(){
        if(loadingDialog!=null)
            loadingDialog.cancel();
    }
}

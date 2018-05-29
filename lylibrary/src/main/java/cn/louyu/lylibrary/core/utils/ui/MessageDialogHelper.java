package cn.louyu.lylibrary.core.utils.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by sdj003 on 2018/5/27.
 */

public class MessageDialogHelper {
    private AlertDialog dialog;
    private Context context;
    public MessageDialogHelper(Context context){
        this.context=context;

    }

    public AlertDialog showDialog(String msg){
        heidDialog();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(msg);
        dialog=builder.show();
        return dialog;
    }

    public AlertDialog showDialog(String title,String msg){
        heidDialog();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title)
        .setMessage(msg);
        dialog=builder.show();
        return dialog;
    }
    public AlertDialog showDialog(String title,String msg,String pButtonStr){
        heidDialog();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(pButtonStr,null);
        dialog=builder.show();
        return dialog;
    }

    public AlertDialog showDialog(String title, String msg, String pButtonStr, DialogInterface.OnClickListener pListener){
        heidDialog();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(pButtonStr,pListener);
        dialog=builder.show();
        return dialog;
    }

    public AlertDialog showDialog(String title, String msg, String pButtonStr, DialogInterface.OnClickListener pListener,String nButtonStr, DialogInterface.OnClickListener nListener){
        heidDialog();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(pButtonStr,pListener)
                .setNegativeButton(nButtonStr,nListener);
        dialog=builder.show();
        return dialog;
    }

    public void heidDialog(){
        if(dialog!=null){
            dialog.cancel();
        }
    }
}

package cn.louyu.lylibrary.core.utils.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sdj003 on 2018/5/22.
 */

public class ToastHelper {
    private static Toast toast=null;
    /**
     * 弹出一个吐司
     * */
    public static void showToast(Context context,String msg,int duration){
        if(toast==null){
            toast=Toast.makeText(context,msg,duration);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}

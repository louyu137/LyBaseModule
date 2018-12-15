package cn.louyu.lymvpframework.utils.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sdj003 on 2018/5/22.
 */

public class ToastHelper {
    private static Toast toast=null;

    private ToastHelper() {
        throw new AssertionError();
    }
    /**
     * 弹出一个吐司
     * */
    public final static void showToast(Context context,String msg,int duration){
        if(toast==null){
            toast=Toast.makeText(context,msg,duration);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }

    public final static void showToast(Context context,String msg){
        showToast(context,msg,Toast.LENGTH_SHORT);
    }
}

package cn.louyu.lylibrary.core.utils.okhttp.base;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import cn.louyu.lylibrary.core.utils.okhttp.interfaces.IRequestOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.ResultMsg;
import okhttp3.Call;

/**
 * Created by sdj003 on 2018/7/11.
 */

public abstract class BaseCallbackOnUI<T> extends Handler implements IRequestOnUI<T> {
    /**
     * BEFORE={@BEFORE} 请求前的标记常量
     * */
    protected final int BEFORE=0x45287895;
    /**
     * SUCCESS={@SUCCESS} 请求成功的标记常量
     * */
    protected final int SUCCESS=0x45287896;
    /**
     * FAILURE={@FAILURE} 请求失败的标记常量
     * */
    protected final int FAILURE=0x45287897;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case BEFORE: //请求前的操作
                onBeforeOnUI();
                break;
            case SUCCESS: //请求成功的操作
                onSuccessOnUI((ResultMsg<T>) msg.obj);
                break;
            case FAILURE: //请求失败的操作
                onFailureOnUI((ResultMsg<T>) msg.obj);
                break;
            default:
                throw new IllegalArgumentException("非法的请求");
        }
    }


    @Override
    public void onBeforeSend() {
        this.sendEmptyMessage(BEFORE);
    }


    @Override
    public void onError(Call call, IOException e) {
        ResultMsg<T> msg=new ResultMsg<T>();
        msg.Msg=e.getMessage();
        msg.Success=false;
        this.sendMessage(this.obtainMessage(FAILURE,msg));
    }
}

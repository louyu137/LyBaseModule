package cn.louyu.lylibrary.core.utils.okhttp.base;

import android.os.Message;

import java.io.IOException;

import cn.louyu.lylibrary.core.utils.okhttp.interfaces.IRequestOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.ResultMsg;
import okhttp3.Call;

/**
 * Created by sdj003 on 2018/7/11.
 */

public abstract class BaseSimpleCallbackOnUI<T> extends BaseCallbackOnUI{
    private IRequestOnUI<T> iRequestOnUI;

    protected void setIRequestOnUI(IRequestOnUI<T> iRequestOnUI){
        this.iRequestOnUI=iRequestOnUI;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case BEFORE: //请求前的操作
                this.iRequestOnUI.onBeforeOnUI();
                break;
            case SUCCESS: //请求成功的操作
                this.iRequestOnUI.onSuccessOnUI((ResultMsg<T>) msg.obj);
                break;
            case FAILURE: //请求失败的操作
                this.iRequestOnUI.onFailureOnUI((ResultMsg<T>) msg.obj);
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

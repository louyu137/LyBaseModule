package cn.louyu.lylibrary.core.utils.okhttp;


import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import cn.louyu.lylibrary.core.interfaces.IRequest;
import cn.louyu.lylibrary.core.models.ResultMsg;
import okhttp3.Call;
import okhttp3.Response;

/**
 * OkHttp异步请求网络监听类
 * 作用：此类继承Handler，可以在回调函数操作与UI相关的动作
 * @author louyu
 * @version 1.0
 */

public abstract class OkHttpJsonListener extends Handler implements IRequest{

    /**
     * BEFORE={@BEFORE} 请求前的标记常量
     * */
    private final static int BEFORE=0x45287895;
    /**
     * SUCCESS={@SUCCESS} 请求成功的标记常量
     * */
    private final static int SUCCESS=0x45287896;
    /**
     * FAILURE={@FAILURE} 请求失败的标记常量
     * */
    private final static int FAILURE=0x45287897;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case BEFORE: //请求前的操作
                onBefore();
                break;
            case SUCCESS: //请求成功的操作
                onSuccess((ResultMsg) msg.obj);
                break;
            case FAILURE: //请求失败的操作
                onFailure((ResultMsg) msg.obj);
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
    public void onSuccess(Call call, Response response) throws IOException {
        String result=response.body().string();
        ResultMsg msg=JSON.parseObject(result,ResultMsg.class);
        if(msg==null){
            msg=new ResultMsg();
            msg.Msg="解析Json数据失败";
            msg.Status=response.code();
            onFailure(msg);
            return;
        }
        this.sendMessage(this.obtainMessage(SUCCESS,msg));
    }

    @Override
    public void onError(Call call, IOException e) {
        ResultMsg msg=new ResultMsg();
        msg.Msg=e.getMessage();
        msg.Success=false;
        this.sendMessage(this.obtainMessage(FAILURE,msg));
    }

    /**
     * 请求前回调函数
     * */
    protected abstract void onBefore();
    /**
     * 请求成功回调函数
     * @msg 封装好的返回信息
     * */
    protected abstract void onSuccess(ResultMsg msg);
    /**
     * 请求失败回调函数
     * @msg 封装好的返回信息
     * */
    protected abstract void onFailure(ResultMsg msg);
}

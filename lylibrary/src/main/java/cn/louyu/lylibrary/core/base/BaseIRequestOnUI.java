package cn.louyu.lylibrary.core.base;

import android.app.Activity;

import java.io.IOException;

import cn.louyu.lylibrary.core.interfaces.IRequest;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/10 0010.
 * 请求基类
 */

public abstract class BaseIRequestOnUI implements IRequest{

    //Activity对象
    private Activity activity;
    //初始化Activity
    public BaseIRequestOnUI(Activity activity){
        this.activity=activity;
    }

    //请求前
    @Override
    public void onBeforeSend() {
        if(activity==null)
            return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onBaseBeforeSend();
            }
        });
    }

    //请求成功
    @Override
    public void onSuccess(final Call call, final Response response) throws IOException {
        if(activity==null)
            return;
        final String bodyStr=response.body().string();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onBaseSuccess(call,bodyStr);
            }
        });
    }

    //请求错误
    @Override
    public void onError(final Call call, final IOException e) {
        if(activity==null)
            return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onBaseError(call,e);
            }
        });
    }

    //请求前回调
    protected abstract void onBaseBeforeSend();

    //请求成功回调
    protected abstract void onBaseSuccess(Call call, String bodyStr);

    //请求错误回调
    protected abstract void onBaseError(Call call, IOException e);
}

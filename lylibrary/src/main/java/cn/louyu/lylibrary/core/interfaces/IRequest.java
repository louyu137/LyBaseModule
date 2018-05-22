package cn.louyu.lylibrary.core.interfaces;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/9 0009.
 * 请求的回调接口
 * */
public interface IRequest {
    //请求前回调方法
    void onBeforeSend();
    //请求成功时的回调方法
    void onSuccess(Call call, Response response) throws IOException;
    //请求失败时的回调方法
    void onError(Call call, IOException e);
}

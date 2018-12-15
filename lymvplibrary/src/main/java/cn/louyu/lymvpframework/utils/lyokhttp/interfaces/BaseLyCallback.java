package cn.louyu.lymvpframework.utils.lyokhttp.interfaces;

/**
 * Created by sdj003 on 2018/12/14.
 */

public interface BaseLyCallback<T> extends LyCallback{
    /**
     * 请求前回调
     * */
    void onBeforeCallback();
    /**
     * 请求成功回调
     * @param data 封装好的返回信息
     * */
    void onSuccessCallback(T data);
    /**
     * 请求失败回调
     * @param code 状态码
     * @param msg 错误提示消息
     * */
    void onFailureCallback(int code,String msg);
}

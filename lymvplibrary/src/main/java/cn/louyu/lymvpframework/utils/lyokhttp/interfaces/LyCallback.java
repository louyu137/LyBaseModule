package cn.louyu.lymvpframework.utils.lyokhttp.interfaces;

/**
 * 最终回调接口
 */

public interface LyCallback<T> {
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

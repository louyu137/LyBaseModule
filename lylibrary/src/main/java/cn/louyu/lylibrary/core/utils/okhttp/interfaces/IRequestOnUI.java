package cn.louyu.lylibrary.core.utils.okhttp.interfaces;

import cn.louyu.lylibrary.core.utils.okhttp.entity.ResultMsg;

/**
 * Created by sdj003 on 2018/7/11.
 */

public interface IRequestOnUI<T> extends IRequest{
    /**
     * 请求前回调函数
     * */
    void onBeforeOnUI();
    /**
     * 请求成功回调函数
     * @msg 封装好的返回信息
     * */
    void onSuccessOnUI(ResultMsg<T> msg);
    /**
     * 请求失败回调函数
     * @msg 封装好的返回信息
     * */
    void onFailureOnUI(ResultMsg<T> msg);
}

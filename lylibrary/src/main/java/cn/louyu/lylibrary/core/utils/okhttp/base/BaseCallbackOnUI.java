package cn.louyu.lylibrary.core.utils.okhttp.base;

import android.os.Handler;

import cn.louyu.lylibrary.core.utils.okhttp.interfaces.IRequest;

/**
 * Created by sdj003 on 2018/7/14.
 */

public abstract class BaseCallbackOnUI extends Handler implements IRequest {
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
}

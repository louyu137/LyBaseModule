package cn.louyu.lylibrary.core.utils.okhttp;

import cn.louyu.lylibrary.core.utils.okhttp.base.BaseCallbackOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.base.BaseOkHttpClient;

/**
 * Created by sdj003 on 2018/7/14.
 */

public class OkClientOnUI extends BaseOkHttpClient{
    public OkClientOnUI(String url, BaseCallbackOnUI callbackOnUI) {
        super(url, callbackOnUI);
    }
}

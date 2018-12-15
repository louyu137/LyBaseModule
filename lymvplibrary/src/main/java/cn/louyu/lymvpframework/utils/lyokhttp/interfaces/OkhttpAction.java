package cn.louyu.lymvpframework.utils.lyokhttp.interfaces;


import java.io.File;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by sdj003 on 2018/12/14.
 */

public interface OkhttpAction {

    /**
     * 向服务器发送字符串
     * */
    Call sendString(String url, String data, LyCallback callback);

    /**
     * 向服务器提交Json数据
     * */
    Call sendJson(String url,String json,LyCallback callback);

    /**
     * 向服务器提交表单数据
     * */
    Call sendForm(String url,Map<String,String> param,LyCallback callback);

    /**
     * 向服务器提交文件
     * */
    Call sendFile(String url,File file,LyCallback callback);
}

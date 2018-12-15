package cn.louyu.lymvpframework.utils.lyokhttp.handler;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import cn.louyu.lymvpframework.utils.lyokhttp.config.Constant;
import cn.louyu.lymvpframework.utils.lyokhttp.interfaces.BaseLyCallback;
import okhttp3.Call;

/**
 * Created by sdj003 on 2018/12/14.
 */

public abstract class CallbackHandler<T> implements BaseLyCallback<T> {

    @Override
    public void onBefore() {
        this.onBeforeCallback();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        this.onFailureCallback(0,"未能成功请求服务器"+ (TextUtils.isEmpty(e.getMessage())?""
                :":["+e.getMessage()+"]") );
    }

    /**
     * 获取泛型T的Class
     * @return 获取失败返回 Object.class
     * */
    protected Class getTClass() {
        try{
            return (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }catch (ClassCastException e){

        }
        return Object.class;
    }

    /**
     * 输出日志信息
     * */
    protected void logcat(String msg){
        Log.e(Constant.LY_OKHTTP_TAG,msg+"\t\t-----错误定位["+this.getClass().getName()+"]");
    }

}

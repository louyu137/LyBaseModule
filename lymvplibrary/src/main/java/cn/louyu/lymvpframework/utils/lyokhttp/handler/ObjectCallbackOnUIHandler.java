package cn.louyu.lymvpframework.utils.lyokhttp.handler;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sdj003 on 2018/12/14.
 */

public abstract class ObjectCallbackOnUIHandler<T> extends CallbackHandler<T>{

    /**
     * 请求前的标记常量
     * */
    protected final int BEFORE=0x11191214;

    /**
     * 请求成功的标记常量
     * */
    protected final int SUCCESS=0x11191215;

    /**
     * 请求失败的标记常量
     * */
    protected final int FAILURE=0x11191216;

    /**
     * Handler
     */
    protected Handler handler;

    {
        this.handler=new LyHandler();
    }

    @Override
    public void onBefore() {
        this.handler.sendEmptyMessage(BEFORE);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        int respCode=response.code();
        Message message = this.handler.obtainMessage();
        message.arg1=respCode;
        try{
            String bodyStr = response.body().string();
            Class<T> cls=getTClass();
            if(respCode!=200){
                message.what=this.FAILURE;
                message.obj="发生错误，服务器已响应[code="+respCode+"]";
                logcat((String) message.obj);
                this.handler.sendMessage(message);
                return;
            }
            if(String.class==cls){  //如果泛型类型为String类型
                message.obj=bodyStr;//不解析直接赋值
            }else {
                message.obj=JSON.parseObject(bodyStr,cls);
            }
            message.what=this.SUCCESS;
            this.handler.sendMessage(message);
            return;
        }catch (JSONException e){
            message.what=this.FAILURE;
            message.obj="发生错误，Json解析异常["+e.getMessage()+"]，服务器已响应[code="+respCode+"]";
            logcat((String) message.obj);
            this.handler.sendMessage(message);
            return;
        }catch (Exception e){
            message.what=this.FAILURE;
            message.obj="发生错误，未知异常["+e.getMessage()+"]，服务器已响应[code="+respCode+"]";
            logcat((String) message.obj);
            this.handler.sendMessage(message);
            return;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Message message = this.handler.obtainMessage(FAILURE);
        message.arg1=0;
        message.obj="未能成功请求服务器"+ (TextUtils.isEmpty(e.getMessage())?"":":["+e.getMessage()+"]");
        this.handler.sendMessage(message);
    }

    /**
     * 防止线程更新UI的处理者
     * */
    private class LyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BEFORE: //请求前的操作
                    onBeforeCallback();
                    break;
                case SUCCESS: //请求成功的操作
                    onSuccessCallback((T)msg.obj);
                    break;
                case FAILURE: //请求失败的操作
                    onFailureCallback(msg.arg1,(String) msg.obj);
                    break;
                default:
                    throw new IllegalArgumentException("非法的请求");
            }
        }
    }
}

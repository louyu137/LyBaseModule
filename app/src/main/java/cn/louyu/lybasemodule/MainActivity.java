package cn.louyu.lybasemodule;


import android.view.View;

import com.alibaba.fastjson.JSON;

import cn.louyu.lymvpframework.base.BaseActivity;
import cn.louyu.lymvpframework.model.ResultMsg;
import cn.louyu.lymvpframework.utils.lyokhttp.LyOkHttpClient;
import cn.louyu.lymvpframework.utils.lyokhttp.handler.ObjectCallbackOnUIHandler;
import cn.louyu.lymvpframework.utils.lyokhttp.interfaces.JointCallback;
import cn.louyu.lymvpframework.utils.lyokhttp.interfaces.LyCallback;


public class MainActivity extends BaseActivity{

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void request(View view){
        LyOkHttpClient client=new LyOkHttpClient();
        ResultMsg msg=new ResultMsg();
        msg.Msg="测试,字符串";
        msg.Success=true;
        msg.Data="LyOkhttp Test";
        String json = JSON.toJSONString(msg);
        //错误方式
//        client.sendJson("http://192.168.0.106:8080/api/testOk", json, new MyCallback0());
        //方式一
//        MyCallback1 callback1=new MyCallback1();
//        callback1.setCallback("你的监听类");
//        client.sendJson("http://192.168.0.106:8080/api/testOk", json, callback1);
        //方式二
//        client.sendJson("http://192.168.0.106:8080/api/testOk", json, new MyCallback2<Object>(Object.class,"你的监听类"));
        //方式三(这种方式代码最少)
        MyCallback3 callback3 = new MyCallback3<Object>(){};
        callback3.setCallback(new LyCallback() {
            @Override
            public void onBeforeCallback() {

            }

            @Override
            public void onSuccessCallback(Object data) {
                showToast(data.toString());
            }

            @Override
            public void onFailureCallback(int code, String msg) {
                showToast(msg);
            }
        });
        client.sendJson("http://192.168.0.106:8080/api/testOk", json, callback3);

    }

    /**
     * 错误方式
     * */
    private class MyCallback0<T> extends ObjectCallbackOnUIHandler<T>  {

        @Override
        public void onBeforeCallback() {
            showLoading();
            //TODO==== to do anything ====
        }

        @Override
        public void onSuccessCallback(T data) {
            hideLoading();
            //TODO==== to do anything ====
        }

        @Override
        public void onFailureCallback(int code, String msg) {
            hideLoading();
            //TODO==== to do anything ====
        }

    }

    /**
     * 方式一
     * 明确指定泛型类型
     * */
    private class MyCallback1 extends ObjectCallbackOnUIHandler<Object> {

        LyCallback<Object> callback;

        public void setCallback(LyCallback<Object> callback) {
            this.callback = callback;
        }

        @Override
        public void onBeforeCallback() {
            showLoading();
            if(this.callback!=null) this.callback.onBeforeCallback();
        }

        @Override
        public void onSuccessCallback(Object data) {
            hideLoading();
            if(this.callback!=null) this.callback.onSuccessCallback(data);
        }

        @Override
        public void onFailureCallback(int code, String msg) {
            hideLoading();
            if(this.callback!=null) this.callback.onFailureCallback(code,msg);
        }

    }

    /**
     * 方式二
     * 重写getTClass
     * */
    private class MyCallback2<T> extends ObjectCallbackOnUIHandler<T> {

        LyCallback<T> callback;
        Class<T> tClass;

        public MyCallback2(Class<T> tClass,LyCallback<T> callback) {
            this.callback = callback;
            this.tClass=tClass;
        }

        @Override
        public void onBeforeCallback() {
            showLoading();
            if(this.callback!=null) this.callback.onBeforeCallback();
        }

        @Override
        public void onSuccessCallback(T data) {
            hideLoading();
            if(this.callback!=null) this.callback.onSuccessCallback(data);
        }

        @Override
        public void onFailureCallback(int code, String msg) {
            hideLoading();
            if(this.callback!=null) this.callback.onFailureCallback(code,msg);
        }

        @Override
        protected Class getTClass() {
            return tClass;
        }
    }

    /**
     * 方式三
     * 添加abstract，抽象子类
     * */
    private abstract class MyCallback3<T> extends ObjectCallbackOnUIHandler<T> {

        LyCallback<T> callback;

        public void setCallback(LyCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onBeforeCallback() {
            showLoading();
            if(this.callback!=null) this.callback.onBeforeCallback();
        }

        @Override
        public void onSuccessCallback(T data) {
            hideLoading();
            if(this.callback!=null) this.callback.onSuccessCallback(data);
        }

        @Override
        public void onFailureCallback(int code, String msg) {
            hideLoading();
            if(this.callback!=null) this.callback.onFailureCallback(code,msg);
        }
    }
}

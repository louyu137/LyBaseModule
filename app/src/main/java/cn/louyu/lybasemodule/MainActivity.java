package cn.louyu.lybasemodule;


import android.view.View;

import com.alibaba.fastjson.JSON;

import cn.louyu.lymvpframework.base.BaseActivity;
import cn.louyu.lymvpframework.model.ResultMsg;
import cn.louyu.lymvpframework.utils.lyokhttp.LyOkHttpClient;
import cn.louyu.lymvpframework.utils.lyokhttp.handler.ObjectCallbackOnUIHandler;


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
        client.sendJson("http://192.168.0.106:8080/api/testOk", json, new ObjectCallbackOnUIHandler<String>() {
            @Override
            public void onBeforeCallback() {

            }

            @Override
            public void onSuccessCallback(String data) {
                showToast(data);
            }

            @Override
            public void onFailureCallback(int code, String msg) {

            }
        });
    }
}

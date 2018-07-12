package cn.louyu.lylibrary.core.utils.okhttp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import cn.louyu.lylibrary.core.utils.okhttp.base.BaseCallbackOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.ResultMsg;
import okhttp3.Call;
import okhttp3.Response;

/**
 * OkHttp异步请求网络监听类
 * 作用：此类继承Handler，可以在回调函数操作与UI相关的动作
 * @author louyu
 * @version 1.0
 */

public abstract class JsonCallbackOnUI<T> extends BaseCallbackOnUI<List<T>> {

    @Override
    public void onSuccess(Call call, Response response) throws IOException {
        String result=response.body().string();
        ResultMsg msg=JSON.parseObject(result,ResultMsg.class);
        ResultMsg<List<T>> resultMsg=new ResultMsg<List<T>>();
        if(msg==null||response.code()!=200){
            resultMsg.Success=false;
            resultMsg.Msg="解析Json数据失败";
            resultMsg.Status=response.code();
            this.sendMessage(this.obtainMessage(FAILURE,resultMsg));
            return;
        }
        List<T> list=new LinkedList<T>();
        Class<T> cls=getTClass();
        if(msg.Data instanceof JSONObject){
            JSONObject json=(JSONObject)msg.Data;
            list.add(json.toJavaObject(cls));
        }else if(msg.Data instanceof JSONArray){
            JSONArray array=(JSONArray)msg.Data;
            for(int i=0;i<array.size();i++){
                JSONObject json=array.getJSONObject(i);
                list.add(json.toJavaObject(cls));
            }
        }else {
            resultMsg.Success=false;
            resultMsg.Msg="Json数据反序列化失败";
            resultMsg.Status=response.code();
            this.sendMessage(this.obtainMessage(FAILURE,resultMsg));
            return;
        }
        resultMsg.Success=true;
        resultMsg.Status=response.code();
        resultMsg.Data=list;
        this.sendMessage(this.obtainMessage(SUCCESS,resultMsg));
    }


    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}

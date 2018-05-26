package cn.louyu.lylibrary.core.utils.okhttp;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.louyu.lylibrary.core.interfaces.IRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sdj003 on 2018/5/25.
 */

public class OkHttpUtil implements Callback{
    private String method="POST";
    private IRequest iRequest=null;
    private String url;
    private Call call=null;
    private Map<String,String> param=new HashMap<String,String>();
    private Map<String,String> header=new HashMap<String,String>();
    public OkHttpUtil(String url, IRequest iRequest){
        this.url=url;
        this.iRequest=iRequest;
    }


    /**
     * 添加请求参数
     * */
    public OkHttpUtil add(String name, String value){
        param.put(name,value);
        return this;
    }

    /**
     * 添加header参数
     * */
    public OkHttpUtil header(String key, String value){
        header.put(key,value);
        return this;
    }

    /**
     * 设置请求方方式
     * */
    public void setMethod(String method) {
        if(TextUtils.isEmpty(method))
            return;
        this.method = method;
    }

    /**
     * 拼接参数并请求连接，中文编码
     * */
    public void connect(){
        if(url==null||url.trim().length()<=0||iRequest==null){
            throw new IllegalArgumentException("请求前，请检查传入参数是否正确。");
        }
        FormBody.Builder formBodyBuilder= new FormBody.Builder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            formBodyBuilder.addEncoded( entry.getKey(),entry.getValue());
        }

        RequestBody requestBody=formBodyBuilder.build();
        Request.Builder rb=new Request.Builder().url(url).method(method,requestBody);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            rb.header( entry.getKey(),entry.getValue());
        }
        Request request =rb.build();
        iRequest.onBeforeSend();
        OkHttpClient.Builder ob=new OkHttpClient.Builder();
        ob.build().newCall(request).enqueue(this);
    }

    /**
     * 取消操作
     * */
    public void cancel(){
        if(this.call==null)
            return;
        call.cancel();
    }

    //请求失败时的回调
    @Override
    public void onFailure(Call call, IOException e) {
        this.call=call;
        if(iRequest!=null)
            iRequest.onError(call,e);
    }

    //请求成功时的回调
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        this.call=call;
        if(iRequest!=null)
            iRequest.onSuccess(call,response);
    }
}

package cn.louyu.lymvpframework.utils.lyokhttp;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.louyu.lymvpframework.utils.lyokhttp.interfaces.ICallback;
import cn.louyu.lymvpframework.utils.lyokhttp.interfaces.OkhttpAction;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 网络请求客户端工具
 */

public class LyOkHttpClient implements OkhttpAction {
    /**
     * 请求方法，默认为请求POST请求
     * */
    private String method=Method.POST.method;

    /**
     * 请求头
     * */
    private Map<String,String> header=new HashMap<String,String>();


    /**
     * 请求方法
     * */
    public enum Method{
        GET("GET"),
        HEAD("HEAD"),
        POST("POST"),
        DELETE("DELETE"),
        PUT("PUT"),
        PATCH("PATCH");

        String method;

        Method(String method){
            this.method=method;
        }

    }

    /**
     * 发送的媒体类型
     * */
    public interface MediaTypes{
        MediaType TEXT = MediaType.parse("text/plain;charset=utf-8");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        MediaType STREAM = MediaType.parse("application/octet-stream");
    }

    /**
     * 添加头
     * */
    public LyOkHttpClient addHeader(String key, String value){
        this.header.put(key,value);
        return this;
    }

    /**
     * 添加header参数(注：不会清空以前的数据)
     * */
    public LyOkHttpClient addHeader(Map<String,String> header){
        if(header!=null) this.header.putAll(header);
        return this;
    }


    /**
     * 设置请求方法
     * */
    public void setMethod(Method method) {
        this.method = method.method;
    }

    @Override
    public Call sendString(String url, String data, ICallback callback) {
        Request request = getRequestByMethod(url,method,header,RequestBody.create(MediaTypes.TEXT,data==null?"":data));
        return startRequest(request,callback);
    }

    @Override
    public Call sendJson(String url, String json, ICallback callback) {
        header.put("Content-Type","application/json");
        Request request = getRequestByMethod(url,method,header,RequestBody.create(MediaTypes.JSON,json==null?"":json));
        return startRequest(request,callback);
    }

    @Override
    public Call sendForm(String url, Map<String, String> param, ICallback callback) {
        FormBody.Builder formBodyBuilder= new FormBody.Builder();
        if(param!=null){
            for (Map.Entry<String, String> entry : param.entrySet()) {
                formBodyBuilder.addEncoded( entry.getKey().trim(),entry.getValue()==null?"":entry.getValue());
            }
        }
        Request request = getRequestByMethod(url,method,header,formBodyBuilder.build());
        return startRequest(request,callback);
    }

    @Override
    public Call sendFile(String url, File file, ICallback callback) {
        if(file==null) {
            callback.onFailure(null,new IOException("文件为空"));
        }
        Request request = getRequestByMethod(url,method,header,RequestBody.create(MediaTypes.STREAM,file));
        return startRequest(request,callback);
    }

    /**
     * 检查Url是否为空
     * */
    private void urlIllegal(String url){
        if(TextUtils.isEmpty(url.trim())) new IllegalArgumentException("url不能为空");
    }

    /**
     * 开始请求
     * */
    private Call startRequest(Request request, ICallback callback){
        urlIllegal(request.url().toString());

        Call call = OkClient.getInstance().newCall(request);
        //请求前回调
        callback.onBefore();
        //加入队列
        call.enqueue(callback);
        return call;
    }

    /**
     * 根据请求方法生成Request
     * */
    private static Request getRequestByMethod(String url,String method,Map<String,String> header,RequestBody requestBody){
        Request.Builder rb;
        switch (Method.valueOf(method)){
            case POST:
            case DELETE:
            case PATCH:
            case PUT:
                rb= new Request.Builder().url(url).method(method,requestBody);
                break;
            case GET:
            case HEAD:
                rb= new Request.Builder().url(url).method(method,null);
                break;
            default:
                rb= new Request.Builder().url(url);
                break;
        }
        for (Map.Entry<String, String> entry : header.entrySet()) {
            rb.header(entry.getKey(), entry.getValue());
        }
        return rb.build();
    }
}

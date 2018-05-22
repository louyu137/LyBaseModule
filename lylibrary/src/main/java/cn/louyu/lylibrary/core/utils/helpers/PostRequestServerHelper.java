package cn.louyu.lylibrary.core.utils.helpers;

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
 * Created by Administrator on 2018/1/9 0009.
 */

public class PostRequestServerHelper {
    private IRequest iRequest;
    private String url;
    private Map<String,String> map=new HashMap<String,String>();
    /**
     * POST请求服务器
     * @param url 请求的连接
     * @param iRequest 请求后回调函数
     * */
    public PostRequestServerHelper(String url, IRequest iRequest){
        this.url=url;
        this.iRequest=iRequest;
    }

    public PostRequestServerHelper add(String name, String value){
        map.put(name,value);
        return this;
    }

    /**
     * 拼接参数并请求连接，不中文编码
     * */
    public void connect(){
        if(url==null||url.trim().length()<=0||iRequest==null){
            throw new IllegalArgumentException("请求前，请检查传入参数是否正确。");
        }
        FormBody.Builder formBodyBuilder= new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBodyBuilder.add( entry.getKey(),entry.getValue());
        }
        RequestBody requestBody=formBodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        iRequest.onBeforeSend();
        new OkHttpClient().newCall(request).enqueue(new ResponseCallback());
    }

    /**
     * 拼接参数并请求连接，进行中文编码
     * */
    public void connectEncoded(){
        if(url==null||url.trim().length()<=0||iRequest==null){
            throw new IllegalArgumentException("请求前，请检查传入参数是否正确。");
        }
        FormBody.Builder formBodyBuilder= new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBodyBuilder.addEncoded( entry.getKey(),entry.getValue());
        }
        RequestBody requestBody=formBodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        iRequest.onBeforeSend();
        new OkHttpClient().newCall(request).enqueue(new ResponseCallback());
    }

    /**
     * 请求后回调类
     * */
    private class ResponseCallback implements Callback {

        //请求失败时的回调
        @Override
        public void onFailure(Call call, IOException e) {
            if(iRequest!=null)
                iRequest.onError(call,e);
        }

        //请求成功时的回调
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(iRequest!=null)
                iRequest.onSuccess(call,response);
        }
    }
}

package cn.louyu.lylibrary.core.utils.okhttp.entity;

import okhttp3.OkHttpClient;

/**
 * Created by sdj003 on 2018/7/14.
 */

public final class OKClient {

    private OKClient(){
        throw new AssertionError();
    }

    public static class HolderClass{
        private final static OkHttpClient instance=new OkHttpClient().newBuilder().build();
    }

    public static OkHttpClient getInstance(){
        return HolderClass.instance;
    }
}

package cn.louyu.lylibrary.core.utils.okhttp.interfaces;

import java.io.IOException;
import java.nio.ByteBuffer;

import cn.louyu.lylibrary.core.utils.okhttp.entity.FileInfo;

public interface OnDownloadListener {

    /**
     * 下载即将开始
     * */
    void onDownloadBegin(FileInfo info)throws IOException;

    /**
     * 下载接收到一个ByteBuffer和当前进度
     * */
    void onDownloading(ByteBuffer buffer)throws IOException;

    /**
     * 下载结束
     * */
    void onDownloadEnd()throws IOException;

    /**
     * 下载出错
     * */
    void onDownloadFailed(String e);

    /**
     *设置打断文件跳过多少个字节
     * */
    long setBreakSkip();
}

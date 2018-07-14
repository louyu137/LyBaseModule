package cn.louyu.lylibrary.core.utils.okhttp;

import android.os.Message;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.regex.Pattern;

import cn.louyu.lylibrary.core.utils.okhttp.base.BaseCallbackOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.FileInfo;
import cn.louyu.lylibrary.core.utils.okhttp.interfaces.OnDownloadListener;
import okhttp3.Call;
import okhttp3.Response;
import okio.BufferedSource;

/**
 * Created by sdj003 on 2018/7/11.
 */

public abstract class FileCallbackOnUI extends BaseCallbackOnUI implements OnDownloadListener{

    private final int receiveProgress=0x45756698;
    private final int downloadFailed=0x45756699;
    private final int downloadBegin=0x45756700;
    private final int downloadEnd=0x45756701;


    private volatile long skip=0;

    private volatile boolean isBend=false;

    private volatile long fileSize=0;

    private volatile long progress=0;

    public long getProgress() {
        return progress;
    }

    public long getFileSize() {
        return fileSize;
    }

    @Override
    public void handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case BEFORE: //请求前的操作
                    isBend=false;
                    skip=0;
                    fileSize=0;
                    progress=0;
                    skip = this.setBreakSkip();
                    break;
                case receiveProgress:
                    this.onDownloading((ByteBuffer) msg.obj);
                    break;
                case downloadBegin:
                    this.onDownloadBegin((FileInfo) msg.obj);
                    isBend=true;
                    break;
                case downloadFailed:
                    this.onDownloadFailed((String)msg.obj);
                    break;
                case downloadEnd:
                    this.onDownloadEnd();
                    break;
                default:
                    throw new IllegalArgumentException("非法的请求");
            }
        }catch (IOException e){
            this.sendMessage(this.obtainMessage(downloadFailed,e.getMessage()));
        }
    }

    @Override
    public void onBeforeSend() {
        this.sendEmptyMessage(BEFORE);
    }

    @Override
    public void onSuccess(Call call, Response response) throws IOException {
        if(response.code()!=200){
            this.sendMessage(this.obtainMessage(downloadFailed,response.body().string()));
            return;
        }
        //获取文件名
        String filename = response.header("Content-Disposition");//获取文件名
        if(filename!=null){
            if(Pattern.matches("(.*)filename\\*=(.*)", filename)) {
                filename = URLDecoder.decode(filename, "UTF-8");
            }
        }
        fileSize = response.body().contentLength(); //获取文件大小
        FileInfo info=new FileInfo();
        info.setFileName(filename);
        info.setSize(fileSize);
        this.sendMessage(this.obtainMessage(downloadBegin,info));
        while (!isBend);
        //获取文件流
        BufferedSource buffer=response.body().source();
        if(skip>0) buffer.skip(skip);
        byte[] bytes=new byte[10240];
        int len=0;
        while((len=buffer.read(bytes))!=-1){
            ByteBuffer byteBuffer=ByteBuffer.allocate(len);
            byteBuffer.clear();
            byteBuffer.put(bytes,0,len);
            byteBuffer.flip();
            progress+=len;
            this.sendMessage(this.obtainMessage(receiveProgress,byteBuffer));
        }

        this.sendEmptyMessage(downloadEnd);
    }

    @Override
    public void onError(Call call, IOException e) {
        this.sendMessage(this.obtainMessage(downloadFailed,e.getMessage()));
    }

}

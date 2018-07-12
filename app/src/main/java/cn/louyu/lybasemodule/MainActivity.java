package cn.louyu.lybasemodule;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import cn.louyu.lylibrary.core.utils.okhttp.FileCallbackOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.FileInfo;
import cn.louyu.lylibrary.core.utils.okhttp.OkHttpHelper;

public class MainActivity extends BaseActionbarActivity{

    private ImageView image;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        image=findViewById(R.id.image);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    public void loadImage(View view){


            OkHttpHelper oh=new OkHttpHelper("http://192.168.0.103:8080/abcdef.pdf", new FileCallbackOnUI() {
                FileOutputStream fos;
                @Override
                public void onDownloadBegin(FileInfo info) throws IOException {
                    File file=new File("/sdcard/download/abcdef.pdf");
                    file.deleteOnExit();
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    fos=new FileOutputStream(file);
                }

                @Override
                public void onDownloading(ByteBuffer buffer) throws IOException {
                    fos.getChannel().write(buffer);
                    Log.i("progress",getProgress()+"/"+getFileSize());
                }

                @Override
                public void onDownloadEnd() throws IOException {
                    fos.close();
                }

                @Override
                public void onDownloadFailed(String e) {
                    showToast(e);
                }

                @Override
                public long setBreakSkip() {
                    return 0;
                }
            });
            oh.setMethod("GET");
            oh.connect();
    }
}

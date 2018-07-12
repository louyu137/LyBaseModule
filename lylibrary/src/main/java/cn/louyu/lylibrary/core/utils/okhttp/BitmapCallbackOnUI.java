package cn.louyu.lylibrary.core.utils.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import cn.louyu.lylibrary.core.utils.okhttp.base.BaseCallbackOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.ResultMsg;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sdj003 on 2018/7/11.
 */

public abstract class BitmapCallbackOnUI extends BaseCallbackOnUI<Bitmap> {


    @Override
    public void onSuccess(Call call, Response response) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
        ResultMsg<Bitmap> msg=new ResultMsg<Bitmap>();
        if(bitmap==null||response.code()!=200){
            msg.Success=false;
            msg.Msg="解析图片流失败";
            msg.Status=response.code();
            this.sendMessage(this.obtainMessage(FAILURE,msg));
            return;
        }
        msg.Success=true;
        msg.Data=bitmap;
        this.sendMessage(this.obtainMessage(SUCCESS,msg));
    }
}

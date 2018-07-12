package cn.louyu.lylibrary.core.utils.okhttp;

import java.io.IOException;

import cn.louyu.lylibrary.core.utils.okhttp.base.BaseCallbackOnUI;
import cn.louyu.lylibrary.core.utils.okhttp.entity.ResultMsg;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/10 0010.
 * 请求基类
 */

public abstract class TxtCallbackOnUI extends BaseCallbackOnUI<String> {
    @Override
    public void onSuccess(Call call, Response response) throws IOException {
        ResultMsg<String> msg=new ResultMsg<String>();
        if(response.code()!=200){
            msg.Status=response.code();
            msg.Success=false;
            this.sendMessage(this.obtainMessage(FAILURE,msg));
        }
        String txt=response.body().string();
        msg.Status=response.code();
        msg.Success=true;
        msg.Data=txt;
        this.sendMessage(this.obtainMessage(SUCCESS,msg));
    }
}

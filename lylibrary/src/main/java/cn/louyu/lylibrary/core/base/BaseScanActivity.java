package cn.louyu.lylibrary.core.base;

import android.content.Intent;
import android.view.View;

import com.google.zxing.client.android.CaptureActivity;

import cn.louyu.lylibrary.core.interfaces.IOnScanQRListener;
import cn.louyu.lylibrary.core.interfaces.IScanQRCode;

/**
 * Created by sdj003 on 2018/5/18.
 */

public abstract class BaseScanActivity extends BaseActivity implements IScanQRCode,IOnScanQRListener{
    public final static int REQUESTCODE=0x1119; //请求值

    protected boolean isScan=false; //是否成功扫了条码
    /**
     * 扫码按钮事件
     * */
    public void scanQRCode(View view){
        OnScanQRCodeBtnClick();
        Intent intent=new Intent(context,CaptureActivity.class);
        this.startActivityForResult(intent,REQUESTCODE);
    }


    /**
     * 接收到扫码结果
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                String result=data.getStringExtra(CaptureActivity.RESULTKEY);
                this.OnReceiveScanResult(result);
                isScan=true;
            }
        }
    }
}

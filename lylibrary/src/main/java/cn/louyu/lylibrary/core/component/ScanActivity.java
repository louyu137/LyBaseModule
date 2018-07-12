package cn.louyu.lylibrary.core.component;


import android.app.Activity;
import android.content.Intent;

import com.google.zxing.client.android.CaptureActivity;

import cn.louyu.lylibrary.core.utils.manager.ActivityPageManager;

/**
 * Created by sdj003 on 2018/6/25.
 */

public class ScanActivity extends CaptureActivity{
    public final static String RESULTKEY="scanResult";
    @Override
    public void onScanResult(String result) {
        Intent intent=new Intent();
        intent.putExtra(RESULTKEY,result);
        this.setResult(Activity.RESULT_OK,intent);
        ActivityPageManager.getInstance().finishActivity(this);
    }

}

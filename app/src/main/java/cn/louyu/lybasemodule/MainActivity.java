package cn.louyu.lybasemodule;
import android.view.View;

import cn.louyu.lylibrary.core.base.BaseActivity;
import cn.louyu.lylibrary.core.base.BaseScanActivity;
import cn.louyu.lylibrary.core.utils.ui.LoadingDialogHelper;

public class MainActivity extends BaseScanActivity{

    LoadingDialogHelper loadingDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    public void button1(View view){

    }

    public void button2(View view){

    }

    @Override
    public void OnScanQRCodeBtnClick() {

    }

    @Override
    public void OnReceiveScanResult(String result) {
        showToast(result);
    }
}

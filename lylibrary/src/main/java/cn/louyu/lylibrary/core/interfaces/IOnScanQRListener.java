package cn.louyu.lylibrary.core.interfaces;

/**
 * Created by sdj003 on 2018/5/18.
 */

public interface IOnScanQRListener {
    /**
     * 扫描条码按钮点击回掉
     * */
    void OnScanQRCodeBtnClick();

    /**
     * 扫码成功后返回结果回调
     * */
    void OnReceiveScanResult(String result);
}

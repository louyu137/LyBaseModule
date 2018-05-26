package cn.louyu.lybasemodule;

import android.Manifest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.louyu.lylibrary.core.base.BaseNeedPermissionsActivity;
import cn.louyu.lylibrary.core.models.ResultMsg;
import cn.louyu.lylibrary.core.utils.helpers.DialogHelper;
import cn.louyu.lylibrary.core.utils.okhttp.OkHttpJsonUtil;
import cn.louyu.lylibrary.core.utils.okhttp.OkHttpUtil;
import cn.louyu.lylibrary.core.utils.okhttp.OkHttpJsonListener;
import cn.louyu.lylibrary.core.utils.tools.AssistantUtil;

public class MainActivity extends BaseNeedPermissionsActivity{

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void _initData() {
        new OkHttpUtil("http://jxc.luchengdaomi.com:8123/AppUpdate/getLastAppInfo", new OkHttpJsonListener() {
            @Override
            protected void onBefore() {
                DialogHelper.getInstance(context).showLoadingDialog();
            }

            @Override
            protected void onSuccess(ResultMsg msg) {
                DialogHelper.getInstance(context).heidLoadingDialog();
                ApkInfo apkInfo=new ApkInfo();
                OkHttpJsonUtil.objToEntity(apkInfo,msg.Data,new String[]{});
                showToast("APK Name:"+apkInfo.getAppName()+"/Version:"+apkInfo.getVersionName());
            }

            @Override
            protected void onFailure(ResultMsg msg) {
                DialogHelper.getInstance(context).heidLoadingDialog();
            }
        }).connect();
    }

    @Override
    protected Map<String, String> getPermisstions() {
        Map<String,String> map=new HashMap<>();
        map.put(Manifest.permission.CAMERA,"相机权限");
        map.put(Manifest.permission.ACCESS_FINE_LOCATION,"位置权限");
        map.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,"读写SD卡权限");
        return map;
    }

    @Override
    public void OnPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void OnPermissionsTransientDenied(int requestCode, List<String> perms) {
        showToast("有些权限被拒绝了");
    }

    @Override
    public void OnPermissionsPermanentlyDenied(int requestCode, List<String> perms) {
        showToast("有些权限被永久拒绝了");
    }

    @Override
    public void initListener() {

    }
}

package cn.louyu.lybasemodule;

import android.Manifest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.louyu.lylibrary.core.base.BaseNeedPermissionsActivity;

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

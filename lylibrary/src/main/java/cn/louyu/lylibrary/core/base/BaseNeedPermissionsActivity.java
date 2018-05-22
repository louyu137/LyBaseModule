package cn.louyu.lylibrary.core.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import cn.louyu.lylibrary.core.utils.tools.PermissionsLogUtil;

/**
 * Created by sdj003 on 2018/5/22.
 */

public abstract class BaseNeedPermissionsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    private final static int PERMISSTIONS_REQUEST_CODE=0x2555;
    private List<String> permisstions=new ArrayList<String>();

    @Override
    public void initData() {
        for(Map.Entry<String,String> entry:getPermisstions().entrySet()){
            permisstions.add(entry.getKey());
        }
        notifyNeedPermissions();
        _initData();
    }

    protected void notifyNeedPermissions(){
        if(permisstions.isEmpty())
        {
            //没有申请任何权限
            return;
        }
        if (PermissionsLogUtil.easyCheckAllPermissions(context,permisstions)) {
            //全部权限被允许
            return;
        }
        //权限拒绝 申请权限
        String [] permissions= PermissionsLogUtil.checkDeniedPermissions(context,permisstions);
        String message="应用需要申请:\n";
        for(String p:permissions){
            String permissuonName=getPermisstions().get(p);
            if(TextUtils.isEmpty(permissuonName))
                return;
            message+=permissuonName+"\n";
        }
        message+="等权限，请您允许";
        EasyPermissions.requestPermissions(context, message, PERMISSTIONS_REQUEST_CODE,permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        OnPermissionsGranted(requestCode,perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //判断用户是否勾选了永久拒绝这些权限
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            OnPermissionsPermanentlyDenied(requestCode,perms);
            return;
        }
        OnPermissionsTransientDenied(requestCode,perms);
    }

    //接着初始化数据
    protected abstract void _initData();

    //获取权限map
    protected  abstract Map<String,String> getPermisstions();

    //当权限被允许之后
    public abstract void OnPermissionsGranted(int requestCode, List<String> perms);

    //当某些权限被拒绝后
    public abstract void OnPermissionsTransientDenied(int requestCode, List<String> perms);

    //当某些权限被永远拒绝后
    public abstract void OnPermissionsPermanentlyDenied(int requestCode,List<String> perms);
}

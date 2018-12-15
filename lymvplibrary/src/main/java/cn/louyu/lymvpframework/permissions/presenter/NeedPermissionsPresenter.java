package cn.louyu.lymvpframework.permissions.presenter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.louyu.lymvpframework.base.BasePresenter;
import cn.louyu.lymvpframework.permissions.view.NeedPermissionsView;
import cn.louyu.lymvpframework.utils.manager.PermissionsLogManager;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by sdj003 on 2018/9/28.
 */

public class NeedPermissionsPresenter extends BasePresenter<NeedPermissionsView> implements EasyPermissions.PermissionCallbacks{

    public void checkPermissions(Activity context, Map<String, String> maps,int requestCode) {
        if(maps==null||maps.isEmpty()) return;//没有申请任何权限
        List<String> permisstions=new ArrayList<String>(maps.keySet());
        if (PermissionsLogManager.easyCheckAllPermissions(context,permisstions)) {
            //全部权限被允许
            return;
        }
        //权限拒绝 申请权限
        String [] permissions= PermissionsLogManager.checkDeniedPermissions(context,permisstions);
        String message="应用需要申请:\n";
        for(String p:permissions){
            String permissuonName=maps.get(p);
            if(TextUtils.isEmpty(permissuonName))
                return;
            message+=permissuonName+"\n";
        }
        message+="等权限，请您允许";
        EasyPermissions.requestPermissions(context, message, requestCode,permissions);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        getView().OnPermissionsGranted(requestCode,perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if(getView() instanceof Activity) {
            //判断用户是否勾选了永久拒绝这些权限
            if (EasyPermissions.somePermissionPermanentlyDenied((Activity) getView(), perms)) {
                getView().OnPermissionsPermanentlyDenied(requestCode, perms);
                return;
            }
            getView().OnPermissionsTransientDenied(requestCode, perms);
        }
    }

    @TargetApi(24)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(getView() instanceof Activity) {
            ((Activity)getView()).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}

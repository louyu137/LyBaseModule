package cn.louyu.lylibrary.core.utils.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;
/**
 * @date 2018-5-22 09点54分
 * @author by louyu
 * @description 权限工具类
 * */
public class PermissionsLogManager {
    /**
     * 查看权限是否全部已申请权限
     * 如果有一个未成功申请返回false
     * @context 上下文
     * @param permissions 权限列表
     * @return 如果有一个未成功申请返回false，否则返回true
     * */
    public static boolean checkAllPermissions(Context context,String... permissions) {
        boolean isHasPermissions=true;
        for (String permission : permissions) {
            if(!isAppliedPermission(context,permission))
                return false;
        }
        return isHasPermissions;
    }

    /**
     * 返回哪些权限是没通过的
     * @context 上下文
     * @param permissions 权限列表
     * @return 返回哪些权限是没通过的
     * */
    public static String[] checkDeniedPermissions(Context context,String... permissions){
        List<String> list=new ArrayList<String>();
        for (String permission : permissions) {
            if(!isAppliedPermission(context,permission))
                list.add(permission);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 返回哪些权限是没通过的
     * @context 上下文
     * @param permissions 权限列表
     * @return 返回哪些权限是没通过的
     * */
    public static String[] checkDeniedPermissions(Context context,List<String> permissions){
        List<String> list=new ArrayList<String>();
        for (String permission : permissions) {
            if(!isAppliedPermission(context,permission))
                list.add(permission);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 如果全部通过返回true ，有某个失败时返回false
     * @context 上下文
     * @param permissions 权限列表
     * @return true if all permissions are already granted, false if at least one permission is not
     */
    public static boolean easyCheckAllPermissions(Context context,String ... permissions) {
        return EasyPermissions.hasPermissions(context,permissions);
    }

    /**
     * 如果全部通过返回true ，有某个失败时返回false
     * @context 上下文
     * @param permissions 权限列表
     * @return true if all permissions are already granted, false if at least one permission is not
     */
    public static boolean easyCheckAllPermissions(Context context,List<String> permissions){
        return EasyPermissions.hasPermissions(context,permissions.toArray(new String[]{}));
    }


    /**
     * 查看权限是否已申请
     * @context 上下文
     * @param permissions 权限列表
     * */
    private static boolean isAppliedPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT <Build.VERSION_CODES.N_MR1) //小于25返回true
            return true;
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
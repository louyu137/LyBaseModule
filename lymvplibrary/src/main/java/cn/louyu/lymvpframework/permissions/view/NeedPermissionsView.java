package cn.louyu.lymvpframework.permissions.view;

import java.util.List;
import java.util.Map;

import cn.louyu.lymvpframework.interfaces.IView;

/**
 * Created by sdj003 on 2018/9/28.
 */

public interface NeedPermissionsView extends IView{

    /**
     * 获取权限 Map<权限,描述>
     * 例：Map.put("Manifest.permission.CAMERA","相机权限");
     * @return 为null时跳过检查权限
     * */
    Map<String,String> getPermisstions();

    /**
     * 当权限被允许之后
     * */
    void OnPermissionsGranted(int requestCode, List<String> perms);

    /**
     * 当某些权限被拒绝后
     * */
    void OnPermissionsTransientDenied(int requestCode, List<String> perms);

    /**
     * 当某些权限被永远拒绝后
     * */
    void OnPermissionsPermanentlyDenied(int requestCode,List<String> perms);
}

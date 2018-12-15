package cn.louyu.lymvpframework.utils.device;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;

/**
 * Created by sdj003 on 2018/11/30.
 */

public class AppUtils {
    /**
     * 获取当前应用版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取当前apk的版本
     * */
    public static int getApkVersionCode(Context mContext,String apkFilePath){
        int versionCode = 0;
        if(!new File(apkFilePath).exists()){
            return versionCode;
        }
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, 0);
        ApplicationInfo applicationInfo = pi.applicationInfo;
        if(pi != null){
            applicationInfo.sourceDir = apkFilePath;
            applicationInfo.publicSourceDir = apkFilePath;
//            String name = applicationInfo.loadLabel(pm).toString();
//            String packageName = applicationInfo.packageName;
//            String versionName = pi.versionName;
//            int versionCode = pi.versionCode;
//            Drawable iconDrawable = applicationInfo.loadIcon(pm);
            versionCode=pi.versionCode;
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取App名称
     *
     * @param context 上下文
     * @return
     */
    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager pm=context.getPackageManager();
            appName = pm.getApplicationLabel(pm.getApplicationInfo(context.getPackageName(), 0)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }
}

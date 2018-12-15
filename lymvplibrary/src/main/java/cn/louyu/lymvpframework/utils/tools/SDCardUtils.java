package cn.louyu.lymvpframework.utils.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.os.Environment;  
import android.os.StatFs;  
  
/** 
 * SD卡相关的辅助类 
 *  
 *  
 *  
 */  
public class SDCardUtils  
{  
    private SDCardUtils()
    {  
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");  
    }  
  
    /** 
     * 判断SDCard是否可用 
     *  
     * @return 
     */  
    public static boolean isSDCardEnable()  
    {  
        return Environment.getExternalStorageState().equals(  
                Environment.MEDIA_MOUNTED);
    }
  
    /** 
     * 获取SD卡路径 
     *  
     * @return 
     */  
    public static String getSDCardPath()  
    {  
        return Environment.getExternalStorageDirectory().getAbsolutePath()  
                + File.separator;  
    }  
  
    /** 
     * 获取SD卡的剩余容量 单位byte 
     *  
     * @return 
     */  
    public static long getSDCardAllSize()  
    {  
        if (isSDCardEnable())  
        {  
            StatFs stat = new StatFs(getSDCardPath());  
            // 获取空闲的数据块的数量  
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;  
            // 获取单个数据块的大小（byte）  
            long freeBlocks = stat.getAvailableBlocks();  
            return freeBlocks * availableBlocks;  
        }  
        return 0;  
    }  
  
    /** 
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte 
     *  
     * @param filePath 
     * @return 容量字节 SDCard可用空间，内部存储可用空间 
     */  
    public static long getFreeBytes(String filePath)  
    {  
        // 如果是sd卡的下的路径，则获取sd卡可用容量  
        if (filePath.startsWith(getSDCardPath()))  
        {  
            filePath = getSDCardPath();  
        } else  
        {// 如果是内部存储的路径，则获取内存存储的可用容量  
            filePath = Environment.getDataDirectory().getAbsolutePath();  
        }  
        StatFs stat = new StatFs(filePath);  
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;  
        return stat.getBlockSize() * availableBlocks;  
    }  
  
    /** 
     * 获取系统存储路径 
     *  
     * @return 
     */  
    public static String getRootDirectoryPath()  
    {  
        return Environment.getRootDirectory().getAbsolutePath();  
    }


    /**
     * @Title: getExtSDCardPaths
     * @Description: to obtain storage paths, the first path is theoretically
     *               the returned value of
     *               Environment.getExternalStorageDirectory(), namely the
     *               primary external storage. It can be the storage of internal
     *               device, or that of external sdcard. If paths.size() >1,
     *               basically, the current device contains two type of storage:
     *               one is the storage of the device itself, one is that of
     *               external sdcard. Additionally, the paths is directory.
     * @return List<String>
     * @throws IOException 获取手机上所有可用的sd卡路径
     * @return
     */
    public static ArrayList<String> getExtSDCardPaths() {
        ArrayList<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        if (extFileStatus.equals(Environment.MEDIA_MOUNTED) && extFile.exists()
                && extFile.isDirectory()) {
            paths.add(extFile.getAbsolutePath());
        }
        try {
            // obtain executed result of command line code of 'mount', to judge
            // whether tfCard exists by the result
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
                if ((!line.contains("fat") && !line.contains("fuse") && !line.contains("storage"))
                        || line.contains("secure") || line.contains("asec")
                        || line.contains("firmware") || line.contains("shell")
                        || line.contains("obb") || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile.getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                paths.add(mountPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }


}  
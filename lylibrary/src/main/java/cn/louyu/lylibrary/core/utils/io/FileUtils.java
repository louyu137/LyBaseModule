package cn.louyu.lylibrary.core.utils.io;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by sdj003 on 2018/7/14.
 */

public class FileUtils {

    /**
     * 删除目录下所有文件
     * @param Path    路径
     */
    public static void deleteAllFile(String Path) {

        // 删除目录下所有文件
        File path = new File(Path);
        File files[] = path.listFiles();
        if (files != null) {
            for (File tfi : files) {
                if (tfi.isDirectory()) {
                    System.out.println(tfi.getName());
                }
                else {
                    tfi.delete();
                }
            }
        }
    }


    /**
     *保存文本
     * @param fileName  文件名字
     * @param content  内容
     * @param append  是否累加
     * @return  是否成功
     */
    public static boolean saveTextValue(String fileName, String content, boolean append) {

        try {
            File textFile = new File(fileName);
            if (!append && textFile.exists()) textFile.delete();

            FileOutputStream os = new FileOutputStream(textFile);
            os.write(content.getBytes("UTF-8"));
            os.close();
        } catch (Exception ee) {
            return false;
        }

        return true;
    }
}

package cn.louyu.lylibrary.core.utils.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 * @edit louyu
 * @update 2018-5-22 11点04分
 */
public class CloseUtils {

    private CloseUtils() {
        throw new AssertionError();
    }


    /**
     * Close closable and hide possible {@link IOException}
     *
     * @param closeable closeable object
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignored
            }
        }
    }

}

package cn.louyu.lylibrary.core.utils.manager;

import java.io.OutputStream;

import cn.louyu.lylibrary.core.utils.io.CloseUtils;

/**
 * 执行shell 命令，需要root权限
 */

public class RootShellCmd {
    private OutputStream os;

    /**
     * 执行shell指令
     * @param cmd 指令
     */
    public final void exec(String cmd) {
        try {
            if (os == null) {
                os = Runtime.getRuntime().exec("su").getOutputStream();
            }
            os.write(cmd.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭退出
     * */
    public final void exit(){
        CloseUtils.closeQuietly(os);
    }
}

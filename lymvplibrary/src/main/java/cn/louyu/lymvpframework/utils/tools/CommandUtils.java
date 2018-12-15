package cn.louyu.lymvpframework.utils.tools;


import android.os.SystemClock;
import android.text.TextUtils;

import java.io.DataOutputStream;
import java.io.IOException;


/**
 * Android 命令行工具
 */

public class CommandUtils {

    /**
     * 执行命令 堵塞UI
     * @param command 要执行的命令
     * @param timeOut 超时时间，当小于零时默认10s
     * @param coms 执行的操作
     * */
    public static boolean execCommand(String command,long timeOut,String... coms){
        final long timeout=timeOut<=0?10000:timeOut;
        if(TextUtils.isEmpty(command.trim())) return false;
        if(coms==null) coms=new String[]{};
        Thread tout=null;
        DataOutputStream os = null;
        try
        {
            final Process process = Runtime.getRuntime().exec(command);
            os = new DataOutputStream(process.getOutputStream());
            for(String s:coms){
                os.writeBytes(s + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
            tout=new Thread(){
                @Override
                public void run() {
                    super.run();
                    SystemClock.sleep(timeout);
                    if(process!=null)
                        process.destroy();
                }
            };
            tout.start();
            int status=-1;
            status=process.waitFor();
            if (status == 0) {
                tout.interrupt();
                if (process != null)
                    process.destroy();
                return true;
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            CloseUtils.closeQuietly(os);
        }
        return false;
    }

    /**
     *Ping IP地址
     * @param ipAddress IP地址
     **/
    public static boolean ping(String ipAddress){
        return execCommand("/system/bin/ping -c 1 -w 150 "+ipAddress,1000,"");
    }



}

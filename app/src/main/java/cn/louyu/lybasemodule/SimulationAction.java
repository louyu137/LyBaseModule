package cn.louyu.lybasemodule;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import cn.louyu.lylibrary.core.utils.manager.RootShellCmd;
import cn.louyu.lylibrary.core.utils.ui.ToastHelper;

/**
 * 模拟按键和触摸动作，需要root权限
 */

public class SimulationAction {

    private RootShellCmd cmd;
    private Context context;
    private volatile boolean isRoot;

    public SimulationAction(Context context){
        this.context=context;
        cmd=new RootShellCmd();
    }

    /**
     * 模拟全局按键
     * @param keyCode 键值
     */
    public final void simulateKey(int keyCode) {
        cmd.exec("input keyevent " + keyCode + "\n");
    }

    /**
     * 模拟点击
     * @param x 点击的x坐标
     * @param y 点击的y坐标
     */
    public final void simulateClick(int x,int y) {
        cmd.exec("input tap "+x+" "+y+"\n");
    }

    /**
     * 模拟长按 input swipe <x1> <y1> <x2> <y2> [duration(ms)]
     * @param x 点击的x坐标
     * @param y 点击的y坐标
     */
    public final void longPress(int x,int y,int time){
        cmd.exec("input swipe "+x+" "+y+" "+x+" "+y+" "+time+"/n");
    }





    public void exit(){
        cmd.exit();
    }

}

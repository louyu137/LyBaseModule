package cn.louyu.lymvpframework.utils.lyokhttp.interfaces;
import okhttp3.Callback;

/**
 * Created by sdj003 on 2018/12/14.
 */

public interface LyCallback extends Callback{
    /**
     * 请求前回调方法
     * */
    void onBefore();

}

package cn.louyu.lymvpframework.utils.lyokhttp.interfaces;
import okhttp3.Callback;

/**
 * 对okhttp3.Callback进行封装，新增了请求前回调方法
 */

public interface ICallback extends Callback{
    /**
     * 请求前回调方法
     * */
    void onBefore();
}

package cn.louyu.lylibrary.core.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sdj003 on 2018/7/13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnClick {
    //接收一个int[]，表示可以接收多个view的id,绑定到同一个click执行方法上
    @IdRes int[] value();
}

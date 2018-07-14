package cn.louyu.lylibrary.core.annotation;

import android.view.View;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * Created by sdj003 on 2018/7/13.
 */

public class InjectViewProcessor implements ProcessorIntf<Field>{
    @Override
    public boolean accept(AnnotatedElement element) {
        //如果当前这个AnnotatedElement实例加有InjectView注解，则返回true
        return element.isAnnotationPresent(InjectView.class);
    }

    @Override
    public void process(Object object, View view, Field field) {
        //如果存在该元素的指定类型的注释，则返回这些注释，否则返回 null
        InjectView iv = field.getAnnotation(InjectView.class);
        if(iv != null) {
            final int viewId = iv.value(); //获取注解值（找到控件的id）
            final View v = view.findViewById(viewId); //通过根view查找此id对应的view
            field.setAccessible(true); //设置属性值的访问权限
            try {
                field.set(object, v); //将查找到的view指定给目标对象object
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

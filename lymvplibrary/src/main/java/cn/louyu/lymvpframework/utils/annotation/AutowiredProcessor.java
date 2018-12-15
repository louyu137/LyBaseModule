package cn.louyu.lymvpframework.utils.annotation;

import android.view.View;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;

/**
 * Created by sdj003 on 2018/9/28.
 */

public class AutowiredProcessor implements ProcessorIntf<Field>{
    @Override
    public boolean accept(AnnotatedElement element) {
        return element.isAnnotationPresent(Autowired.class);
    }

    @Override
    public void process(Object object, View view, Field field) {
        //如果存在该元素的指定类型的注释，则返回这些注释，否则返回 null
        Autowired autowired = field.getAnnotation(Autowired.class);
        if(autowired==null) return;
        Class<?> type = field.getType();
        try {
            Object data=type.newInstance();
            field.setAccessible(true); //设置属性值的访问权限
            field.set(object,data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

package cn.louyu.lymvpframework.utils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdj003 on 2018/9/29.
 */

public class ProxyUtils {
    private ProxyUtils() {
        throw new AssertionError();
    }

    private static List<Class<?>> getInterfacesBySuper(Class<?> cls){
        List<Class<?>> interfaces=new ArrayList<>();
        for(Class<?> c:cls.getInterfaces()){
            interfaces.add(c);
        }
        if(cls.getSuperclass()!=null){
            interfaces.addAll(getInterfacesBySuper(cls.getSuperclass()));
        }
        return interfaces;
    }
    /**
     * 获取该类及父类实现的所有接口数组
     * @param
     * */
    public static Class<?>[] getAllInterfaces(Class<?> cls){
        List<Class<?>> classList=getInterfacesBySuper(cls);
        Class<?>[] interfaces=new Class<?>[classList.size()];
        return classList.toArray(interfaces);
    }

    public static <T> T newProxyInstance(Class<?> tClass,InvocationHandler h)throws IllegalArgumentException{
        //获得加载被代理类的 类加载器
        ClassLoader loader = tClass.getClassLoader();
        //指明被代理类实现的接口
        Class<?>[] interfaces= getAllInterfaces(tClass);
        //生成代理类并返回
        return (T)Proxy.newProxyInstance(loader,interfaces,h);
    }
}

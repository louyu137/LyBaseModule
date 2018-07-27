package cn.louyu.lylibrary.core.annotation;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Injector {
 
	private static List<? extends ProcessorIntf<? extends AccessibleObject>> chain
            = Arrays.asList(new InjectViewProcessor(), new OnClickProcessor());

	/**
     * 初始化Activity
     * */
    public static void inject(Activity act) {
    	//传入activity实例和rootview
        inject(act,act.getWindow().getDecorView());
    }

    /**
     * 初始化Fragment
     * */
    public static void inject(Fragment act) {
        //传入activity实例和rootview
        inject(act,act.getView().getRootView());
    }
 
    public static void inject(Object obj, View rootView) {
        final Class<?> aClass = obj.getClass();
        //获取当前活动中所有声明的属性，包括私有属性
        final Field[] declaredFields = aClass.getDeclaredFields();
        for (Field f : declaredFields) {
           doChain(obj,f,rootView);
        }
        //获取当前活动中所有声明的方法，包括私有方法
        final Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method m : declaredMethods) {
            doChain(obj, m, rootView);
        }
    }
    //把每个遍历到的方法或者属性，甚至是构造方法，类等等通过处理器链来询问这个注解你accept吗？接受则交给它来处理
    private static void doChain(Object obj,AccessibleObject ao, View rootView) {
            for (ProcessorIntf p : chain) {
            	//判断当前AccessibleObject(Field,Method都继承了此类)是否为ProcessorIntf具体子类类型的注解
                if(p.accept(ao)) {
                	p.process(obj,rootView,ao);
                }
            }
    }
}
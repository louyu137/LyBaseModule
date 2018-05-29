package cn.louyu.lylibrary.core.utils.okhttp;

import java.lang.reflect.Method;
import java.util.Map;


/**
 * com.alibaba.fastjson.JSONObject 转化为指定实体的工具类。需要添加依赖：compile 'com.alibaba:fastjson:1.2.47'
 * @author louyu
 * @version 1.0
 * */
public class JsonObjToEntityUtil {

    private static final  Class [] baseType=new Class[]{java.lang.String.class,java.lang.Boolean.class,java.lang.Integer.class,java.lang.Long.class,
            java.lang.Float.class,java.lang.Double.class,java.util.Date.class};

    /**
     * 把JsonObject转化为实体
     * @entity 实体对象
     * @obj com.alibaba.fastjson.JSONObject的实例
     * @excludes 需要排除不赋值的属性
     * */
    public static <T> void objToEntity(T entity, com.alibaba.fastjson.JSONObject obj, String[] excludes){
        for(Map.Entry<String, Object> entry:obj.entrySet()){
            String method=entry.getKey();
            if(isExistProp(excludes, method))
                return;
            method=method.substring(0,1).toUpperCase()+method.substring(1);
            method="set"+method;
            Method m=getMethodByName(entity,method);
            if(m==null)
                continue;
            invokeMethod(entity,m,entry.getValue());
        }
    }

    /**
     * 调用方法
     * @param entity 实体
     * @param method 方法
     * @object object 参数值
     * */
    private static <T> void invokeMethod(T entity,Method method,Object object){
        for(Class cls:baseType){
            try {
                if(method.getParameterTypes()[0].getName().equals(cls.getName())){
                    method.invoke(entity, object);
                }
            }catch (Exception e){

            }
        }
    }

    /**
     * 反射获取类的方法名称
     * @param entity 实体
     * @param methodName 方法名
     * */
    private static <T> Method getMethodByName(T entity,String methodName){
        for(Class cls:baseType){
            try {
                return entity.getClass().getMethod(methodName,cls);
            }catch (NoSuchMethodException e){
                //TODO=====没找到如此的方法=====
            }
        }
        return null;
    }
    /**
     * 判断被排除的数组（excludes）里是否有 这个（prop）属性
     * @param excludes 被排除的数组
     * @param prop 当前属性名
     * */
    private static boolean isExistProp(String[] excludes, String prop) {

        if (null != excludes)  //如果不等于null
        {
            for (String exclude : excludes)
            {
                if (prop.equals(exclude))
                {
                    return true;
                }
            }
        }
        return false;
    }
}

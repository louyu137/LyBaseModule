package cn.louyu.lylibrary.core.utils.okhttp.utils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
            try {
                invokeMethod(entity, m, entry.getValue());
            }catch (Exception e){

            }
        }
    }

    /**
     * com.alibaba.fastjson.JSONArray 数组转化为实体集合
     * @param array json集合
     * @param cls 实体的字节对象
     * @excludes 需要排除不赋值的属性
     * */
    public static <T> List<T> arryToEntities(com.alibaba.fastjson.JSONArray array,Class<T> cls,String[] excludes){
        List<T> list=new LinkedList<T>();
        try {
            for(int i=0;i<array.size();i++){
                T entity=cls.newInstance();
                com.alibaba.fastjson.JSONObject jsonObject=array.getJSONObject(i);
                objToEntity(entity,jsonObject,excludes);
                list.add(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 调用方法
     * @param entity 实体
     * @param method 方法
     * @object object 参数值
     * */
    private static <T> void invokeMethod(T entity,Method method,Object object)throws Exception{
        for(Class cls:baseType){
            if(method.getParameterTypes()[0]==cls){
                try {
                    //尝试直接设值到方法
                    method.invoke(entity, object);
                    return;
                }catch (IllegalArgumentException e){
                    try {
                        //尝试强制转换并设值
                        method.invoke(entity, cls.cast(object));
                        return;
                    }catch (ClassCastException e1){
                        if(cls==String.class)
                            //尝试转化为String
                            method.invoke(entity, String.valueOf(object));
                        return;
                    }
                }
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
    private static boolean isExistProp(String[] excludes, String prop){

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

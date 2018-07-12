package cn.louyu.lylibrary.core.utils.okhttp.utils;



import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssistantUtil
{
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      
    /** 
     * 把JSON数据转换成JAVA对象 
     * description: 函数的目的/功能
     * @param  obj 实体类对象
     * @param  data Json数据
     * @param  excludes 需要排除的字段
     */  
    public static <T> void setJsonObjData(T obj, JSONObject data, String[] excludes) throws Exception {
        // 反射获取这个类所有方法
        Method[] methods = obj.getClass().getDeclaredMethods();
        if (null != methods)  
        {
            for (Method m : methods)  //遍历
            {  
  
                String methodName = m.getName();//获得方法名
  
                if (methodName.startsWith("set"))  //判断开始名称是否为“set”
                {  
  
                    methodName = methodName.substring(3);  //截取第三个字符后面的
                    // 获取属性名称   第一个字符toLowerCase转小写
                    methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);  

                    //属性名不等于class 且没有被排除
                    if (!methodName.equalsIgnoreCase("class") && !isExistProp(excludes, methodName))  
                    {
                        try  
                        {
                            //调用这个setXxx方法，并传给它参数
                            m.invoke(obj, new Object[] { data.get(methodName) });
                        }  
                        catch (IllegalArgumentException e1)  //如果非法参数异常
                        {
                            if(m.getParameterTypes()[0].getName().equals("java.lang.Long")){   //获得它的参数类型的全名，是否等于Long
                                m.invoke(obj, new Object[] { Long.valueOf(data.get(methodName).toString())});  //如果是转换类型再赋值
                            }else if(m.getParameterTypes()[0].getName().equals("java.util.Date")){  
                                m.invoke(obj, new Object[] {data.has(methodName)?  sdf.parse(data.get(methodName).toString()) : null});
                            }else if (m.getParameterTypes()[0].getName().equals("java.lang.Double")){
                                m.invoke(obj, new Object[] { Double.valueOf(data.get(methodName).toString())});
                            }else if(m.getParameterTypes()[0].getName().equals("java.lang.Integer")){
                                m.invoke(obj, new Object[] { Integer.valueOf(data.get(methodName).toString())});
                            }else if(m.getParameterTypes()[0].getName().equals("java.lang.Float")){
                                m.invoke(obj, new Object[] { Float.valueOf(data.get(methodName).toString())});
                            }else if(m.getParameterTypes()[0].getName().equals("java.lang.Boolean")){
                                m.invoke(obj, new Object[] { Boolean.valueOf(data.get(methodName).toString())});
                            }else{
                                m.invoke(obj, new Object[] { data.getString(methodName) });
                            }
                        } catch (Exception e) {
                            //异常处理
                        }
                    }  
                }  
            }  
        }  
  
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

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
    private List<Map<String,Object>> getFiledsInfo(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap<String,Object>();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }
    /**
     * 根据属性名获取属性值
     * */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}  
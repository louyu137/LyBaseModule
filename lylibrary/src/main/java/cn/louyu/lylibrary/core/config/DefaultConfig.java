package cn.louyu.lylibrary.core.config;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sdj003 on 2018/5/19.
 */

public abstract class DefaultConfig {
    /**
     * 是否在修改此类
     * */
    public static boolean isException=false;
    /**
     * 指定根Url重新设置加载这个静态类(此方法若抛出异常请直接关闭程序，
     * 否则可能产生有不可挽回的后果，异常字段isException={@isException})
     * @param url url应符合正则表达式：((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?
     * @return 返回修改状态 -1->出现异常需要立即终止应用 0->网址不正确 1->修改顺利
     * */
    public synchronized final static int reLoadUrl(String url,String oldUrl,Class cls){
        String regEx="((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(url);
        //是否匹配
        if(!matcher.matches()){
            return 0;
        }
        try {
            Field[] fields = cls.getFields(); //获得所有非私有的方法
            for (Field f : fields) {
                if(f.get(null) instanceof String) {
                    String str = (String) f.get(null);
                    str = str.replace(oldUrl, url);
                    f.set(null, str);
                }
            }
        } catch (Exception e) {
            isException = true;
            return -1;
        }
        oldUrl = url;
        return 1;
    }
}

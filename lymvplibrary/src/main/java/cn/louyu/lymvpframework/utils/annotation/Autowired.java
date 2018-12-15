package cn.louyu.lymvpframework.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被注解标记的成员变量将被实例化（如果被注解变量没有无参构造函数,无法被注入）
 */
@Target(ElementType.FIELD) //可以在构造函数和属性上使用
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

}

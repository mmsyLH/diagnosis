package asia.lhweb.diagnosis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解仅仅只是是标识这个属性是id
 * @author :罗汉
 * @date : 2024/3/12
 */
@Retention(RetentionPolicy.RUNTIME)//运行的时候渲染
@Target(value = {ElementType.FIELD})//修饰属性字段
public @interface Id {
    String value() default "";
}

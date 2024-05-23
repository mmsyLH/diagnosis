package asia.lhweb.diagnosis.annotation;

import asia.lhweb.diagnosis.common.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})//只有在方法上能用
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {		// 自定义操作日志记录注解

     OperatorType operatorType() default OperatorType.ADMIN;	// 操作人类别
     String businessType();//操作类型
     boolean isSaveRequestData() default true;   // 是否保存请求的参数
     boolean isSaveResponseData() default true;  // 是否保存响应的参数
    
}

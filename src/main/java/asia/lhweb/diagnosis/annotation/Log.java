package asia.lhweb.diagnosis.annotation;


import asia.lhweb.diagnosis.common.enums.BusinessType;
import asia.lhweb.diagnosis.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author 罗汉
 * @date 2024/05/06
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块
     */
     String title() default "";

    /**
     * 功能
     */
     BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
     OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
     boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
     boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
     String[] excludeParamNames() default {};
}

package asia.lhweb.diagnosis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个注解仅仅只是是标识这个属性是逻辑删除的字段 0未删除 1删除
 *
 * @author 罗汉
 * @date 2024/04/29
 */
@Retention(RetentionPolicy.RUNTIME)// 运行的时候渲染
@Target(value = {ElementType.FIELD})// 修饰属性字段
public @interface DeleteMarker {
    /**
     * 未删除状态的值，默认为0，表示记录活跃（未被逻辑删除）。
     */
    int activeValue() default 0;

    /**
     * 已删除状态的值，默认为1，表示记录已被逻辑删除。
     */
    int deletedValue() default 1;

    /**
     * 数据库表中对应的逻辑删除字段名，默认为"is_delete"。
     * 在执行删除操作时，此字段的值会被更新为deletedValue。
     * 查询时，会自动添加条件确保只选取activeValue所指定状态的记录。
     */
    String columnName() default "is_delete";
}

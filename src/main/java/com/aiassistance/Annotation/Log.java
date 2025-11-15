package com.aiassistance.Annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)  // 指定注解可以应用于方法
@Retention(RetentionPolicy.RUNTIME)  // 指定注解在运行时保留
@Documented  // 指定注解应该被javadoc工具记录
public @interface Log {
    /**
     * 模块
     */
    String module() default "";

    /**
     * 操作类型
     */
    String type() default "";

    /**
     * 描述
     */
    String description() default "";
}

package com.tuacy.chain.core;

import java.lang.annotation.*;

/**
 * 只有添加了Handler注解的类，才认为是Handler处理类
 * 并且我们内部会把添加了改Handler注解，并且实现了IHandler接口的类，放入IOC容器里面去(参考HandlerScan，HandlerScanRegister部分的实现)
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 1:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Handler {

    /**
     * 我们可以把属于同一个group的Handler组成一个调用链
     */
    String group() default "";

    /**
     * 确定执行顺序(数字越小越靠前，越先执行)
     */
    int order() default 0;

}

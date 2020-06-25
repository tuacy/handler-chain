package com.tuacy.chain.core.scan;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 引导扫描添加了Handler注解并且实现了IHandler接口的类，并且把他们添加到IOC容器里面去
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 1:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HandlerScanRegister.class)
public @interface HandlerScan {

    String[] basePackages() default "";

}

package com.tuacy.chain.core.scan;

import com.tuacy.chain.core.Handler;
import com.tuacy.chain.core.IHandler;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 去扫描添加了Handler注解并且实现了IHandler接口的类，并且把他们添加到IOC容器里面去
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 1:21
 */
public class HandlerScanRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private final static String PACKAGE_NAME_KEY = "basePackages";

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 1. 从HandlerScan注解获取到元数据，指定搜索路径
        AnnotationAttributes attrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(HandlerScan.class.getName()));
        if (attrs == null || attrs.isEmpty()) {
            return;
        }
        String[] basePackages = (String[]) attrs.get(PACKAGE_NAME_KEY);
        // 2. 找到指定路径下所有添加了BeanIoc注解的类，添加到IOC容器里面去
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry, false);
        scanner.setResourceLoader(resourceLoader);
        /*
        如果类添加了Handler注解，并且实现了IHandler接口，会被添加到IOC容器里面去
         */
        scanner.addIncludeFilter(new AnnotationTypeFilter(Handler.class));
        scanner.addExcludeFilter(new ReverseAssignableTypeFilter(IHandler.class));
        scanner.scan(basePackages);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}

package com.tuacy.chain.core.scan;

import com.tuacy.chain.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 所有bean初始化的前后做相关信息的整理
 * 1. 找到添加了Handler注解，并且实现了IHandler接口的bean。组装成HandlerConfig，放到HandlerConfigStore单利里面去
 * 2. DefaultHandlerPipeline初始化完成之后，根据一定的规则获取到该DefaultHandlerPipeline需要的handler，并且组成一个调用链
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 1:39
 */
@Component
public class HandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext context;

    /**
     * 初始化bean之前调用
     */
    @Override
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        /*
            找到添加了Handler注解，并且实现了IHandler接口的bean
         */
        Handler annotation = bean.getClass().getAnnotation(Handler.class);
        if (annotation != null && (bean instanceof IHandler)) {
            // 把获取到的handler相关配置信息报错到单利里面去
            HandlerConfigStore.INSTANCE.addHandlerConfig(HandlerConfig.generateHandlerConfig(bean.getClass(), beanName, annotation));
        }
        return bean;
    }

    /**
     * 初始化bean之后调用
     */
    @Override
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        /*
            DefaultHandlerPipeline初始化完成之后，获取该DefaultHandlerPipeline需要的handler
         */
        if (bean instanceof DefaultHandlerPipeline) {
            DefaultHandlerPipeline pipeline = (DefaultHandlerPipeline) bean;
            // 当前pipeline关系哪个调用链(group)
            List<HandlerConfig> handlerConfigList = HandlerConfigStore.INSTANCE.getHandlerConfigList(pipeline.getGroup());
            if (handlerConfigList != null && handlerConfigList.size() > 0) {
                for (HandlerConfig handlerConfig : handlerConfigList) {
                    pipeline.addLast((IHandler<?>) context.getBean(handlerConfig.getBeanName()), handlerConfig);
                }
            }

        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

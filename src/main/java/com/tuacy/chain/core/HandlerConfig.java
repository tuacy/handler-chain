package com.tuacy.chain.core;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 2:36
 */
public class HandlerConfig {

    /**
     * IOC容器handler bean对应的名字
     */
    private String beanName;
    /**
     * handler对应的group
     * 我们规定group相同的可以组成handler调用连
     */
    private String group;
    /**
     * handler的调用顺序。数字越小越早调用
     */
    private int order;
    /**
     * handler对应的class
     */
    private Class<?> handlerClass;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Class<?> getHandlerClass() {
        return handlerClass;
    }

    public void setHandlerClass(Class<?> handlerClass) {
        this.handlerClass = handlerClass;
    }

    /**
     * 生成HandlerConfig对象
     *
     * @param handlerClass    handler处理类对应的class
     * @param beanName        IOC容器bean对应的名字
     * @param handlerAnnotate 添加在handler类上的Handler注解
     */
    public static HandlerConfig generateHandlerConfig(Class<?> handlerClass, String beanName, Handler handlerAnnotate) {
        HandlerConfig info = new HandlerConfig();
        info.handlerClass = handlerClass;
        info.beanName = beanName;
        info.group = handlerAnnotate.group();
        info.order = handlerAnnotate.order();
        return info;
    }

}

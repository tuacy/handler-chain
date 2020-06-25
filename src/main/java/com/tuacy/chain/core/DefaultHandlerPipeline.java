package com.tuacy.chain.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 注意DefaultHandlerPipeline(@Scope("prototype"))多例(原型)模式
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 0:11
 */
@Component()
@Scope("prototype")
public class DefaultHandlerPipeline implements IHandlerPipeline, InitializingBean {

    /**
     * 创建一个默认的handler，将其注入到首尾两个节点的HandlerContext中，其作用只是将链往下传递
     */
    private static final IHandler<?> DEFAULT_HANDLER = (ctx, source) -> {
    };

    /**
     * 创建一个头结点和尾节点，这两个节点内部没有做任何处理，只是默认的将每一层级的链往下传递，
     * 这里头结点和尾节点的主要作用就是用于标志整个链的首尾，所有的业务节点都在这两个节点中间
     */
    private HandlerContext head;
    private HandlerContext tail;

    /**
     * 当前pipe处理哪个group对应的调用链(相同group的handler可以确定一个调用链)
     */
    private String group;

    /**
     * 最初始的业务数据需要通过构造函数传入，因为这是驱动整个pipeline所需要的数据，
     */
    public DefaultHandlerPipeline(String group) {
        this.group = group;
    }

    /**
     * 这里通过实现InitializingBean接口来达到初始化Pipeline的目的，可以看到，这里初始的时候
     * 我们通过ApplicationContext实例化了两个HandlerContext对象，然后将head.next指向tail节点，
     * 将tail.prev指向head节点。也就是说，初始时，整个链只有头结点和尾节点。
     */
    @Override
    public void afterPropertiesSet() {
        head = newContext(DEFAULT_HANDLER);
        tail = newContext(DEFAULT_HANDLER);
        head.next = tail;
        tail.prev = head;
    }

    public String getGroup() {
        return group;
    }

    /**
     * HandlerContext
     */
    private HandlerContext newContext(IHandler<?> handler) {
        HandlerContext context = new HandlerContext();
        context.handler = handler;
        return context;
    }

    /**
     * 往Pipeline中添加节点,组成一个链表形式
     */
    public void addLast(IHandler<?> handler, HandlerConfig config) {
        HandlerContext handlerContext = newContext(handler);
        tail.prev.next = handlerContext; // 每次都是一个新的对象
        handlerContext.prev = tail.prev;
        handlerContext.next = tail;
        handlerContext.handlerConfig = config;
        tail.prev = handlerContext;
    }

    @Override
    public IHandlerPipeline executeHandlerProcess(Object source) {
        HandlerContext.invokeHandlerProcess(head, source);
        return this;
    }
}

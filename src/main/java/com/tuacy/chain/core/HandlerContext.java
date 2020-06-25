package com.tuacy.chain.core;

import org.springframework.core.ResolvableType;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 0:10
 */
public class HandlerContext {

    protected HandlerContext prev;
    protected HandlerContext next;
    protected HandlerConfig handlerConfig;
    protected IHandler handler;

    public <T> void executeHandlerProcess(T source) {
        invokeHandlerProcess(next, source);
    }

    public HandlerContext getPrev() {
        return prev;
    }

    public HandlerContext getNext() {
        return next;
    }

    public IHandler getHandler() {
        return handler;
    }

    public HandlerConfig getHandlerConfig() {
        return handlerConfig;
    }

    /**
     * 调用handler执行具体的业务逻辑
     */
    protected static <T> void invokeHandlerProcess(HandlerContext ctx, T source) {
        if (null != ctx) {
            /*
            如果handler实现类上的泛型和参数需要的类型不一致，跳过执行下一个
             */
            if (ctx.handlerConfig == null || isGenericMach(ctx.handlerConfig.getHandlerClass(), source)) {
                ctx.handler.handlerProcess(ctx, source);
            }
            ctx.executeHandlerProcess(source);
        }
    }

    /**
     * 判断Handler实现类IHandler接口的泛型是否和handlerProcess方法需要的对象一直
     * TODO: 这个方法现在还只是做一些简单的判断。如果泛型是 List<String> 参数需要的是List<Double>，这种情况下是判断不出来的，后续还需要优化
     *
     * @param handlerClass      handler实现类
     * @param handlerProcessArg handlerProcess方法需要的参数
     * @return 泛型和和参数对应
     */
    private static boolean isGenericMach(Class<?> handlerClass, Object handlerProcessArg) {
        ResolvableType resolvableType = ResolvableType.forClass(handlerClass);
        Class<?> resolve = resolvableType.getInterfaces()[0].getGeneric(0).resolve();
        return resolve != null && resolve.isInstance(handlerProcessArg);
    }

}

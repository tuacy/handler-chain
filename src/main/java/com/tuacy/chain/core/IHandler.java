package com.tuacy.chain.core;

/**
 * 每个handler都需要实现的接口
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 0:09
 */
public interface IHandler<T> {

    /**
     * 实现自己handler的逻辑
     *
     * @param ctx    上下文
     * @param source 数据源
     */
    void handlerProcess(HandlerContext ctx, T source);

}

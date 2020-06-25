package com.tuacy.chain.core;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 0:10
 */
public interface IHandlerPipeline {

    IHandlerPipeline executeHandlerProcess(Object source);

}

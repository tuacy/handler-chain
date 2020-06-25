package com.tuacy.chain.handler;

import com.tuacy.chain.core.Handler;
import com.tuacy.chain.core.HandlerContext;
import com.tuacy.chain.core.IHandler;
import com.tuacy.chain.entity.RealDataInfo;

import java.util.List;

/**
 * 测试实时数据调用链
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 1:41
 */
@Handler(group = "realTime", order = 2)
public class RealTimeWebsocketHandler implements IHandler<List<RealDataInfo>> {
    @Override
    public void handlerProcess(HandlerContext ctx, List<RealDataInfo> source) {
        System.out.println("RealTimeWebsocketHandler");
    }
}

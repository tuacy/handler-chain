package com.tuacy.chain.handler;

import com.tuacy.chain.core.Handler;
import com.tuacy.chain.core.HandlerContext;
import com.tuacy.chain.core.IHandler;
import com.tuacy.chain.entity.AlarmInfo;

import java.util.List;

/**
 * 测试告警调用链
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 1:41
 */
@Handler(group = "alarm", order = 1)
public class Alarm1Handler implements IHandler<List<AlarmInfo>> {

    private String name = "AlarmPush1Handler";

    @Override
    public void handlerProcess(HandlerContext ctx, List<AlarmInfo> source) {
        System.out.println("AlarmPush1Handler");
    }
}

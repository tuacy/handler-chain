package com.tuacy.chain.core;

import com.tuacy.chain.entity.AlarmInfo;
import com.tuacy.chain.entity.RealDataInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 9:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HandlerChainTest {

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @Test
    public void chainTest() {

        /*
        告警调用链
         */
        IHandlerPipeline alarmPipeLine = newPipeline("alarm");
        alarmPipeLine.executeHandlerProcess(mockAlarmDataInit());

        /*
        实时数据调用链
         */
        IHandlerPipeline realTimePipeLine = newPipeline("realTime");
        realTimePipeLine.executeHandlerProcess(mockRealDataInit());

    }

    private IHandlerPipeline newPipeline(String group) {
        return context.getBean(DefaultHandlerPipeline.class, group);
    }

    /**
     * 测试使用的数据
     */
    private List<AlarmInfo> mockAlarmDataInit() {
        List<AlarmInfo> retList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            AlarmInfo item = new AlarmInfo();
            item.setIoServerId("100" + index);
            retList.add(item);
        }
        return retList;
    }

    /**
     * 测试使用的数据
     */
    private List<RealDataInfo> mockRealDataInit() {
        List<RealDataInfo> retList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            RealDataInfo item = new RealDataInfo();
            item.setTest("100" + index);
            retList.add(item);
        }
        return retList;
    }
}

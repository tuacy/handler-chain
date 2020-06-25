package com.tuacy.chain.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 3:03
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HandlerConfigStoreTest {

    @Test
    public void store() {
        List<HandlerConfig> allHandlerConfigList = HandlerConfigStore.INSTANCE.getHandlerConfigList();
        List<HandlerConfig> alarmHandlerConfigList = HandlerConfigStore.INSTANCE.getHandlerConfigList("alarm");
        List<HandlerConfig> realTimeHandlerConfigList = HandlerConfigStore.INSTANCE.getHandlerConfigList("realTime");
    }

}

package com.tuacy.chain.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 单例模式，保存所有的handler配置信息
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 2:46
 */
public enum HandlerConfigStore {

    INSTANCE;

    /**
     * key -> group
     * value -> handler配置信息(handler对应的bean名字，handler对应的order等)
     */
    private Map<String, List<HandlerConfig>> groupHandlerMap = new HashMap<>();

    /**
     * 添加配置信息
     *
     * @param config handler配置信息
     */
    public void addHandlerConfig(HandlerConfig config) {
        // config放入map中
        if (groupHandlerMap.containsKey(config.getGroup())) {
            groupHandlerMap.get(config.getGroup()).add(config);
        } else {
            List<HandlerConfig> valueList = new ArrayList<>();
            valueList.add(config);
            groupHandlerMap.put(config.getGroup(), valueList);
        }
        // 按照order排序
        groupHandlerMap.put(
                config.getGroup(),
                groupHandlerMap.get(config.getGroup()).stream()
                        .sorted((o1, o2) -> {
                            if (o1.getOrder() == o2.getOrder()) {
                                return 0;
                            }
                            return o1.getOrder() > o2.getOrder() ? 1 : -1;
                        })
                        .collect(Collectors.toList())
        );

    }

    /**
     * 获取所有的handler信息
     *
     * @return handler信息列表
     */
    public List<HandlerConfig> getHandlerConfigList() {
        List<HandlerConfig> configList = null;
        for (Map.Entry<String, List<HandlerConfig>> entry : groupHandlerMap.entrySet()) {
            if (configList == null) {
                configList = new ArrayList<>();
            }
            if (entry.getValue() != null) {
                configList.addAll(entry.getValue());
            }
        }
        if (configList != null) {
            configList = configList.stream()
                    .sorted((o1, o2) -> {
                        if (o1.getOrder() == o2.getOrder()) {
                            return 0;
                        }
                        return o1.getOrder() > o2.getOrder() ? 1 : -1;
                    })
                    .collect(Collectors.toList());
        }
        return configList;
    }

    /**
     * 获取指定组的handler信息
     *
     * @param group 组名,如果为null则获取所有的handler
     * @return handler信息列表
     */
    public List<HandlerConfig> getHandlerConfigList(String group) {
        if (group == null) {
            // 获取所有的handler
            List<HandlerConfig> configList = null;
            for (Map.Entry<String, List<HandlerConfig>> entry : groupHandlerMap.entrySet()) {
                if (configList == null) {
                    configList = new ArrayList<>();
                }
                if (entry.getValue() != null) {
                    configList.addAll(entry.getValue());
                }
            }
            if (configList != null) {
                configList = configList.stream()
                        .sorted((o1, o2) -> {
                            if (o1.getOrder() == o2.getOrder()) {
                                return 0;
                            }
                            return o1.getOrder() > o2.getOrder() ? 1 : -1;
                        })
                        .collect(Collectors.toList());
            }
            return configList;
        } else {
            return groupHandlerMap.get(group);
        }
    }

}

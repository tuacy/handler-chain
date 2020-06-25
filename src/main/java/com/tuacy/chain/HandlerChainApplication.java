package com.tuacy.chain;

import com.tuacy.chain.core.scan.HandlerScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/25 0:07
 */
@SpringBootApplication
@HandlerScan(basePackages = "com.tuacy.chain.handler")
public class HandlerChainApplication {
    public static void main(String[] args) {
        SpringApplication.run(HandlerChainApplication.class, args);
    }
}

package com.zaccao.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {"com.zaccao.rpc.spring.reference","com.zaccao.rpc.controller","com.zaccao.rpc.annotation"})
@SpringBootApplication
public class NettyConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(NettyConsumerMain.class,args);
    }
}

package com.zaccao.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = {"com.zaccao.rpc.spring.service","com.zaccao.rpc.service"})
@SpringBootApplication
public class NettyRpcProvider {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProvider.class,args);
    }
}

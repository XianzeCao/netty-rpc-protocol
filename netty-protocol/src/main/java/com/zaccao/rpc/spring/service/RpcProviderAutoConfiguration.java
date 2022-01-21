package com.zaccao.rpc.spring.service;

import com.zaccao.rpc.IRegistryService;
import com.zaccao.rpc.RegistryFactory;
import com.zaccao.rpc.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;


@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties) throws UnknownHostException {
        IRegistryService registryService= RegistryFactory.createRegistryService(rpcServerProperties.getRegistryAddress(), RegistryType.findByCode(rpcServerProperties.getRegistryType()));
        return new SpringRpcProviderBean(rpcServerProperties.getServicePort(),registryService);
    }
}

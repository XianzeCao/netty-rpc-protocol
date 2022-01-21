package com.zaccao.rpc.spring.reference;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class RpcReferenceAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

    @Bean
    public SpringRpcReferencePostProcessor postProcessor(){
        RpcClientProperties rc=new RpcClientProperties();
        rc.setRegistryAddress(this.environment.getProperty("gp.client.registryAddress"));
      /*  int port=Integer.parseInt();
        rc.setRegistryAddress(port);*/
        rc.setRegistryType(Byte.parseByte(this.environment.getProperty("gp.client.registryType")));
        return new SpringRpcReferencePostProcessor(rc);
    }
}

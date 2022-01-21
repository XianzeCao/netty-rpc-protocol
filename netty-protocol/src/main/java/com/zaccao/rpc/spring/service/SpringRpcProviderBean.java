package com.zaccao.rpc.spring.service;

import com.zaccao.rpc.IRegistryService;
import com.zaccao.rpc.ServiceInfo;
import com.zaccao.rpc.annotation.RemoteService;
import com.zaccao.rpc.protocol.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {

    private final int serverPort;
    private final String serverAddress;
    private final IRegistryService registryService; //服务注册中心

    public SpringRpcProviderBean(int serverPort,IRegistryService registryService) throws UnknownHostException {
        this.serverPort = serverPort;
        InetAddress address=InetAddress.getLocalHost();
        this.serverAddress=address.getHostAddress();
        this.registryService=registryService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin deploy Netty Server to host {},on port {}",this.serverAddress,this.serverPort);
        new Thread(()->{
            new NettyServer(this.serverAddress,this.serverPort).startNettyServer();
        }).start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //只要bean声明了GpRemoteService注解，则需要把该服务发布到网络上
        if(bean.getClass().isAnnotationPresent(RemoteService.class)){
            Method[] methods=bean.getClass().getDeclaredMethods();
            for(Method method:methods){
                String serviceName=bean.getClass().getInterfaces()[0].getName();
                String key = serviceName+"."+method.getName();
                BeanMethod beanMethod=new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);
                Mediator.beanMethodMap.put(key,beanMethod);

                ServiceInfo serviceInfo=new ServiceInfo();
                serviceInfo.setServiceAddress(this.serverAddress);
                serviceInfo.setServicePort(this.serverPort);
                serviceInfo.setServiceName(serviceName);
                try {
                    registryService.register(serviceInfo); //住的服务
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("register serivce {} failed",serviceName,e);
                }
            }
        }
        return bean;
    }
}

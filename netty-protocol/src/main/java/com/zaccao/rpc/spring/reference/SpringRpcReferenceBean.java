package com.zaccao.rpc.spring.reference;

import com.zaccao.rpc.IRegistryService;
import com.zaccao.rpc.RegistryFactory;
import com.zaccao.rpc.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;


public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;
 /*   private String serviceAddress;
    private int servicePort;*/
    private Class<?> interfaceClass;

    private String registryAddress;
    private byte registryType;


    @Override
    public Object getObject() throws Exception {
        return object;
    }

    public void init(){
        IRegistryService registryService= RegistryFactory.createRegistryService(this.registryAddress, RegistryType.findByCode(this.registryType));
        this.object=Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInovkerProxy(registryService));
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

   /* public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }*/

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRegistryType(byte registryType) {
        this.registryType = registryType;
    }
}

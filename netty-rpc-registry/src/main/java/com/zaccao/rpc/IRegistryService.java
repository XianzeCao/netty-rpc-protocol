package com.zaccao.rpc;


public interface IRegistryService {

    /**
     * 服务注册功能
     * @param serviceInfo
     */
    void register(ServiceInfo serviceInfo) throws Exception;

    /**
     * 服务发现
     * @param serviceName
     * @return
     */
    ServiceInfo discovery(String serviceName) throws Exception;
}

package com.zaccao.rpc;


public enum RegistryType {

    ZOOKEEPER((byte)0),
    EUREKA((byte)1);
    private byte code;

    RegistryType(byte code){
        this.code=code;
    }

    public static RegistryType findByCode(int code){
        for(RegistryType reqType: RegistryType.values()){
            if(reqType.code==code){
                return reqType;
            }
        }
        return null;
    }
}

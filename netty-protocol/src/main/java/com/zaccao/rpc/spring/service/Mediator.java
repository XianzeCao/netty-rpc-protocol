package com.zaccao.rpc.spring.service;

import com.zaccao.rpc.core.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Mediator {
    public static Map<String,BeanMethod> beanMethodMap=new ConcurrentHashMap<>();

    private volatile static Mediator instance=null;

    private Mediator(){}

    public static Mediator getInstance(){
        if(instance==null){
            synchronized (Mediator.class){
                if(instance==null){
                    instance=new Mediator();
                }
            }
        }
        return instance;
    }

    public Object processor(RpcRequest request){
        String key=request.getClassName()+"."+request.getMethodName();
        BeanMethod beanMethod=beanMethodMap.get(key);
        if(beanMethod==null){
            return null;
        }
        Object bean =beanMethod.getBean();
        Method method=beanMethod.getMethod();
        try {
            return method.invoke(bean,request.getParams());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}

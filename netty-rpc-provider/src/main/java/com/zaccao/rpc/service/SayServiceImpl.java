package com.zaccao.rpc.service;

import com.zaccao.rpc.ISayService;
import com.zaccao.rpc.annotation.RemoteService;


@RemoteService
public class SayServiceImpl implements ISayService {


    @Override
    public String say() {
        return "Hello World";
    }
}

package com.zaccao.rpc.service;

import com.zaccao.rpc.IUserService;
import com.zaccao.rpc.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;



@RemoteService
@Slf4j
public class UserServiceImpl implements IUserService {
    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}",name);
        return "save User success: "+name;
    }
}

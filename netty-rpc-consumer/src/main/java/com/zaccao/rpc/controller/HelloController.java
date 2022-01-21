package com.zaccao.rpc.controller;

import com.zaccao.rpc.ISayService;
import com.zaccao.rpc.IUserService;
import com.zaccao.rpc.annotation.RemoteReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @RemoteReference
    IUserService userService; //

    @RemoteReference
    ISayService service;

    @GetMapping("/say")
    public String say(){
        return userService.saveUser("Mic");
    }

    @GetMapping("/hi")
    public String hello(){
        return service.say();
    }
}

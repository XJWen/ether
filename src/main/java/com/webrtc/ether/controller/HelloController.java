package com.webrtc.ether.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello(){
        return "Welcome to SpringBoot";
    }

    @RequestMapping(value = "/demo")
    public String demo(){
        return "demo";
    }
}

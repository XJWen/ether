package com.webrtc.ether.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WSController {

    @MessageMapping("/chatConnect")
    public void connect2other(String roomName,String socketid){

    }
}

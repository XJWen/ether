package com.webrtc.ether.bean;

import lombok.Data;


/**
 * 房间表
 * **/
@Data
public class Room {
    //房间名
    private String name;
    //房间密钥
    private String password;
}

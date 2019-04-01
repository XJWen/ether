package com.webrtc.ether.bean;

import lombok.Data;

import java.util.List;

/**
 * 房间
 * **/
@Data
public class Room {
    //房间名
    private String name;
    //当前房间用户的Socketid
    private String usersocketid;
    //房间密钥
    private String password;
}

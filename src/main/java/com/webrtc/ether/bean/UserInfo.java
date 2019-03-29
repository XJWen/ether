package com.webrtc.ether.bean;


import lombok.Data;

/**
 * 用户注册表
 * **/
@Data
public class UserInfo {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String initTime;
}

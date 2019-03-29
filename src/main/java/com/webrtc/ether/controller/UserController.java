package com.webrtc.ether.controller;

import com.webrtc.ether.bean.UserInfo;
import com.webrtc.ether.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserInfoMapper userInfoMapper;

    @GetMapping("/user/select/{id}")
    public UserInfo selectUserInfo(@PathVariable("id")Integer id){
        return userInfoMapper.queryByID(id);
    }
}

package com.webrtc.ether.controller;

import com.webrtc.ether.bean.UserInfo;
import com.webrtc.ether.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserInfoMapper userInfoMapper;

//    @GetMapping("/user/select/{username}")
    @PostMapping("/login")
    public UserInfo selectUserInfo(@PathVariable String username,@PathVariable String password){
        return userInfoMapper.queryByUsername(username,password);
    }

}

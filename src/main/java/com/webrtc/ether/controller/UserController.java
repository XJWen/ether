package com.webrtc.ether.controller;

import com.alibaba.fastjson.JSONObject;
import com.webrtc.ether.bean.UserInfo;
import com.webrtc.ether.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 用户退出
     * **/
    @PostMapping(value = "/exist",produces = {"application/json;charset=UTF-8"})
    public JSONObject exist(String username){
        JSONObject json = new JSONObject();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        int flag = userInfoMapper.updateUser(userInfo);
        log.info("登录状态(退出)："+flag);
        json.put("state","succes");
        json.put("code","200");
        return json;
    }

    /**
     * 用户登录
     * **/
    @PostMapping(value = "/selectUser",produces = {"application/json;charset=UTF-8"})
    public JSONObject selectUserInfo(String username, String password){
        log.info("username:"+username+" password:"+password);
        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = userInfoMapper.queryByUsername(username,password);
        if (userInfo==null){
            log.error("查无此用户");
            jsonObject.put("state","fail");
            jsonObject.put("code","400");
            return jsonObject;
        }
        jsonObject.put("state","success");
        jsonObject.put("code","200");
        userInfo.setIsload("1");
        int flag = userInfoMapper.updateUser(userInfo);
        log.info("登录状态(登录)："+flag);
        return jsonObject;
    }

}

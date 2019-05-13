package com.webrtc.ether.controller;

import com.alibaba.fastjson.JSONObject;
import com.webrtc.ether.bean.UserInfo;
import com.webrtc.ether.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.Date;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private  UserInfoMapper userInfoMapper;

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
     * 用户注册
     **/
    @PostMapping(value = "/registUser",produces ={"application/json;charset=UTF-8"})
    public JSONObject regist(String username,String password,String email){
        log.info("username:"+username+" password:"+password+" email:"+email);
        JSONObject json = new JSONObject();
        Date date = new Date();
        UserInfo userInfo = new UserInfo();
        String nowtime = DateFormat.getDateInstance().format(date);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setInitTime(nowtime);
        userInfo.setIsload("0");
        int flag = userInfoMapper.insertUser(userInfo);
        if (flag==0){
            json.put("state","fail");
            json.put("code","400");

        }else {
            json.put("state","succes");
            json.put("code","200");

        }
        return json;
    }

    /**
     * 用户修改用户信息
     **/
    @PostMapping(value = "/updateUser",produces ={"application/json;charset=UTF-8"} )
    public JSONObject updateUser(UserInfo user){
        JSONObject json = new JSONObject();
        int flag = userInfoMapper.updateUser(user);
        if (flag==0){
            json.put("state","fail");
            json.put("code","400");
            return json;
        }else {
            json.put("state","succes");
            json.put("code","200");
            return json;
        }
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

        Date date = new Date();
        String nowtime = DateFormat.getDateInstance().format(date);
        userInfo.setInitTime(nowtime);
        userInfo.setIsload("1");
        //登录成功更新用户状态
        try{
            int flag = userInfoMapper.updateUser(userInfo);
            log.info("登录状态(登录)："+flag);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return jsonObject;
    }

}

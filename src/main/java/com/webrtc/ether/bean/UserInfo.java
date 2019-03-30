package com.webrtc.ether.bean;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户注册表
 * **/
@Data
public class UserInfo {

    private Integer id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String email;
    private String initTime;
}

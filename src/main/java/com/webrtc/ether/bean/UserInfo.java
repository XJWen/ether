package com.webrtc.ether.bean;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户类
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
    //判断是否在线（"0"代表不在线,"1"代表在线）
    private String isload;
}

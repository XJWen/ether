package com.webrtc.ether.bean;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SDP implements Serializable {

    private String roomname;
    //用户名
    private String username;
    //用户socketid
    private String socketid;
    //offer信令，answer信令
    private String type;
    //会话描述协议（Session Description Protocol）信令
    private String description;
}

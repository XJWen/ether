package com.webrtc.ether;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author xjWen
 * */
@MapperScan(value = "com.webrtc.ether.mapper")
@SpringBootApplication
@EnableWebSocket
@EnableCaching
public class EtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtherApplication.class, args);
    }

}

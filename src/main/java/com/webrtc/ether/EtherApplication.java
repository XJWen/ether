package com.webrtc.ether;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.webrtc.ether.mapper")
@SpringBootApplication
public class EtherApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtherApplication.class, args);
    }

}

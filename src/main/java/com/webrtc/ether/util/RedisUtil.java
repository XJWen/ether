package com.webrtc.ether.util;

import com.webrtc.ether.bean.SDP;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> objectRedisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

   public void setRedisValue(String key,Object value){
       objectRedisTemplate.opsForValue().set(key,value);
   }

   public Object getRedisValue(String key){
       return objectRedisTemplate.opsForValue().get(key);
   }
}

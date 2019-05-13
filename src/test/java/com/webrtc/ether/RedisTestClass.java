package com.webrtc.ether;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webrtc.ether.bean.SDP;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class RedisTestClass {

    @Autowired
    private RedisTemplate<String,Object> template;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        List<String> sdps = new ArrayList<>();
        for(int i=1;i<5;i++){
            SDP sdp=new SDP();

            sdp.setType("offer");
            sdp.setUsername("user0"+i);
            template.opsForHash().put("myCache",String.valueOf(i),JSON.toJSONString(sdp));
            sdps.add(JSON.toJSONString(sdp));
        }
        String roonname = "SDPs";
        stringRedisTemplate.opsForList().leftPushAll(roonname,sdps);
        List<Object> list=template.opsForHash().values("myCache");
        List<String> getSDPs = stringRedisTemplate.opsForList().range("SDPs", 0, -1);
/*        for (Object str: list) {
            JSONObject json = JSON.parseObject(str.toString());
            String offer = String.valueOf(json.get("sDP"));
            System.out.println(offer);
            System.out.println(json.toJSONString());

        }*/

        JSONArray array = JSONArray.parseArray(JSON.toJSONString(getSDPs));
        System.out.println("删除前---"+array.toJSONString());
        for (String str : getSDPs){
            JSONObject json = JSON.parseObject(str.toString());
            if (json.get("username").equals("user03")){
                stringRedisTemplate.opsForList().remove("SDPs",-1,str);
            }
        }
        List<String> getSDPs2 = stringRedisTemplate.opsForList().range("SDPs", 0, -1);
        JSONArray array2 = JSONArray.parseArray(JSON.toJSONString(getSDPs));

        System.out.println("删除后---"+array2.toJSONString());

       stringRedisTemplate.delete("SDPs");

    }
}

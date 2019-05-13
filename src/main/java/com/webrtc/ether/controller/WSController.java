package com.webrtc.ether.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.webrtc.ether.bean.Room;
import com.webrtc.ether.bean.SDP;
import com.webrtc.ether.mapper.RoomMapper;
import com.webrtc.ether.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class WSController {

    /**向浏览器发送信息**/
    /**
     * 客户端主动发送消息到服务端，服务端马上回应指定的客户端消息
     * websocket可以从服务器指定发送哪个客户端，而不像http只能响应请求端
     **/
    @Autowired
    private  SimpMessagingTemplate template;
    @Autowired
    private  RoomMapper roomMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private  RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @OnOpen
    public void onOpen(@PathParam(value = "socketid")String socketid,
                       @PathParam(value = "roomname")String roomname, Session session){
        log.info("有新用户"+socketid+" 进"+roomname);
    }

    @OnClose
    public void onClose(@PathParam(value = "socketid")String socketid,
                        @PathParam(value = "roomname")String roomname, Session session){
        log.info("有用户"+socketid+"  退出"+roomname);
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        log.info("异常:"+throwable);
        try {
            session.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }



    @MessageMapping("/{roomname}/RTC")
    @SendTo("/topic/{roomname}/response")
    public JSONObject transSDP(@DestinationVariable("roomname")String roomname,SDP description){
        JSONObject json = new JSONObject();

         if (description.getType().equals("getSDP")){
            JSONArray array = popRedis(roomname);
            List<String> list = JSONObject.parseArray(array.toJSONString(),String.class);
            JSONArray response = new JSONArray();
            for (String str : list){
                JSONObject sdp = JSON.parseObject(str);
                log.info(sdp.get("username").toString()+"  descriptionusername "+description.getUsername());
                if ((!sdp.get("username").toString().equals(description.getUsername()))|| StringUtils.isEmpty(description.getUsername())){
                    log.info("添加sdp："+sdp.get("type").toString());
                    response.add(sdp);
                }
            }
            json.put("code","200");
            json.put("data",response);
        }else if (description.getType().equals("exit")){
             stringRedisTemplate.delete(roomname);
             userInfoMapper.updateLoading(description.getUsername(),"0");
             roomMapper.closeConnection(roomname);
             json.put("msg","成功退出大厅");
        }else if (description.getType().equals("leave")){
             removeRedisValue(description.getRoomname(),description.getUsername());
             json.put("msg","成功离开房间");
         }
        return json;
    }

    /**
     * 用户创建房间,将信息存入缓存
     **/
    @MessageMapping("/createRoom")
    @SendTo("/topic/created")
    public JSONObject createRoom(SDP sdp){

        JSONObject json = new JSONObject();
        List<String> list = new ArrayList<>();
        if(roomMapper.selectRoomstate(sdp.getRoomname())==null){
            list.add(JSON.toJSONString(sdp));
            pushRedis(sdp.getRoomname(),list);
            Room room = new Room();
            room.setName(sdp.getRoomname());
            room.setPassword("");
            roomMapper.getConnection(room);
            json.put("code","200");
            json.put("msg","成功创建房间");
            return json;
        }
        json.put("code","400");
        json.put("msg","创建重复房间");
        return json;
    }

    /**
     * 用户进入房间,将信息存入缓存
     **/
    @MessageMapping("/joinRoom")
    @SendTo("/topic/joined")
    public JSONObject joinRoom(SDP sdp){
        JSONObject json = new JSONObject();
        log.info("roomname:"+sdp.getRoomname()+" clientName:"+sdp.getUsername());
        if (roomMapper.selectRoomstate(sdp.getRoomname())!=null){
            List<String> list = new ArrayList<>();
            list.add(JSON.toJSONString(sdp));
            pushRedis(sdp.getRoomname(),list);
            json.put("code","200");
            json.put("msg","成功进入房间");
            return json;
        }
        json.put("code","400");
        json.put("msg","无法加入房间");
        return json;
    }

    /**
     * 将用户sdp信息插入redis
     * */
    public void pushRedis(String roomname,List<String> list){
        stringRedisTemplate.opsForList().leftPushAll(roomname,list);
    }

    /**
     * 将用户sdp信息从redis中取出
     * */
    public JSONArray popRedis(String roomname){
        List<String> socketids = stringRedisTemplate.opsForList().range(roomname, 0, -1);
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(socketids));
        return array;
    }

    /**
     * 将用户从房间名为key的redis缓存中取出
     **/
    public void removeRedisValue(String roomname,String username){
        List<String> socketids = stringRedisTemplate.opsForList().range(roomname, 0, -1);
        for (String str : socketids){
            JSONObject json = JSON.parseObject(str);
            if (json.get("username").equals(username)){
                stringRedisTemplate.opsForList().remove(roomname,-1,str);
            }
        }
    }
}

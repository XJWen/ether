package com.webrtc.ether.mapper;

import com.webrtc.ether.bean.Room;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 这是关于房间信息的数据库操作
 * **/
@Mapper
public interface RoomMapper {
    /**
     * 创建房间
     * **/
    @Insert("insert into room(roomname,password) " +
            "values(#{name},#{password})")
    int getConnection(Room room);

    /***
     * 判断是否存在此房间
     * **/
    @Select("select * from room where roomname=#{roomname}")
    Room selectRoomstate(String roomname);

    /**
     * 房间销毁
     * **/
    @Delete("delete from room where roomname=#{roomname}")
    int closeConnection(String roomname);


}

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
     * 进入房间
     * **/
    @Insert("insert into room(roomname,username,password) " +
            "values(#{name},#{usersocketid},#{password})")
    public int getConnection(Room room);

    /**
     * 离开房间
     * **/
    @Delete("delete from room where username=#{usersocketid}")
    public int closeConnection(String usersocketid);

    /**
     * 查询当前房间的所有用户
     * **/
    @Select("select username from room where roomname=#{roomname}")
    public Room getOtherUser(String roomname);
}

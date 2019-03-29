package com.webrtc.ether.mapper;

import com.webrtc.ether.bean.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 这是用户的数据库操作
 * **/
@Mapper
public interface UserInfoMapper {

    @Select("select * from user where id=#{id}")
    public UserInfo queryByID(Integer id);

    @Insert("insert into user(username,password,email,initTime) " +
            "values(#{username},#{password},#{email},#{initTime})")
    public int insertUser(UserInfo user);

    @Update("update user set username=#{username}," +
            "password=#{password},email=#{email},initTime=#{initTime} where id=#{id}")
    public int updateUser(UserInfo user);
}

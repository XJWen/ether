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
    UserInfo queryByID(Integer id);

    @Select("select * from user where username=#{username} and password=#{password}")
    UserInfo queryByUsername(String username,String password);

    @Insert("insert into user values(#{username},#{password},#{email},#{initTime})")
    int insertUser(UserInfo user);

    @Update("update user set username=#{username}," +
            "password=#{password},email=#{email},initTime=#{initTime},isload=#{isload} where id=#{id}")
    int updateUser(UserInfo user);

    @Update("update user set isload=#{isload} where username=#{username}")
   int updateLoading(String username,String isload);

    @Update("update user set isload=#{isload} where username=#{username}")
    int updateLoadState(UserInfo userInfo);
}

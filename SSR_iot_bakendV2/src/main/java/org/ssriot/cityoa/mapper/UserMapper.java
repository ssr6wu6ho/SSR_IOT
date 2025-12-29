package org.ssriot.cityoa.mapper;

import org.apache.ibatis.annotations.*;
import org.ssriot.cityoa.pojo.User;
import org.ssriot.cityoa.pojo.MyDevice;

import java.util.List;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUserName(String username);

    // 添加用户
    @Insert("INSERT INTO user(username, password, create_time) " +
            "VALUES(#{username}, #{password}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addUser(User user);

    // 查询用户拥有的设备（一对多关系）
    @Select("SELECT d.* FROM myDevice d WHERE d.belong = #{userId}")
    List<MyDevice> findDevicesByUserId(Integer userId);

    // 查询用户共享的设备（多对多关系）
    @Select("SELECT d.* FROM myDevice d " +
            "JOIN user_device ud ON d.id = ud.device_id " +
            "WHERE ud.user_id = #{userId}")
    List<MyDevice> findSharedDevicesByUserId(Integer userId);
}
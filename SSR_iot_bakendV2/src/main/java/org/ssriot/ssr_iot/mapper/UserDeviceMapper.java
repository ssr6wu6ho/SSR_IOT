package org.ssriot.ssr_iot.mapper;

import org.apache.ibatis.annotations.*;
import org.ssriot.ssr_iot.pojo.UserDevice;
import java.util.List;

@Mapper
public interface UserDeviceMapper {
    // 根据用户ID查询设备权限
    @Select("SELECT * FROM user_device WHERE user_id = #{userId}")
    List<UserDevice> findByUserId(Integer userId);

    // 根据设备ID查询共享用户
    @Select("SELECT * FROM user_device WHERE device_id = #{deviceId}")
    List<UserDevice> findByDeviceId(Integer deviceId);

    // 添加设备共享
    @Insert("INSERT INTO user_device(user_id, device_id, permission_level) " +
            "VALUES(#{userId}, #{deviceId}, #{permissionLevel})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserDevice userDevice);

    // 更新设备权限
    @Update("UPDATE user_device SET permission_level = #{permissionLevel} " +
            "WHERE id = #{id}")
    void updatePermission(UserDevice userDevice);

    // 删除设备共享
    @Delete("DELETE FROM user_device WHERE id = #{id}")
    void delete(Integer id);
}
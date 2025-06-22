package org.ssriot.ssr_iot.mapper;

import org.apache.ibatis.annotations.*;
import org.ssriot.ssr_iot.pojo.DeviceType;
import org.ssriot.ssr_iot.pojo.MyDevice;

import java.util.List;

@Mapper
public interface DeviceTypeMapper {
    // 查询所有设备类型
    @Select("SELECT * FROM device_type")
    List<DeviceType> findAll();

    // 根据ID查询设备类型
    @Select("SELECT * FROM device_type WHERE id = #{id}")
    DeviceType findById(Integer id);

    // 添加设备类型
    @Insert("INSERT INTO device_type(type_name) VALUES(#{typeName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(DeviceType deviceType);

    // 更新设备类型
    @Update("UPDATE device_type SET type_name = #{typeName} WHERE id = #{id}")
    void update(DeviceType deviceType);

    // 删除设备类型
    @Delete("DELETE FROM device_type WHERE id = #{id}")
    void delete(Integer id);

    // 查询类型下的设备（一对多关系）
    @Select("SELECT * FROM myDevice WHERE type_id = #{typeId}")
    List<MyDevice> findDevicesByTypeId(Integer typeId);
}
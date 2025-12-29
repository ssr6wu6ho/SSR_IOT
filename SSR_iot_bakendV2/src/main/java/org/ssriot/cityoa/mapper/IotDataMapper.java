package org.ssriot.cityoa.mapper;

import org.apache.ibatis.annotations.*;
import org.ssriot.cityoa.pojo.DeviceData;
import org.ssriot.cityoa.pojo.MyDevice;

import java.util.List;

@Mapper
public interface IotDataMapper {
    // 查询所有设备数据
    @Select("SELECT * FROM DeviceData")
    List<DeviceData> getDeviceData();

    // 添加设备数据
    @Insert("INSERT INTO DeviceData(device_id, temperature, humidity, update_time) " +
            "VALUES(#{deviceId}, #{temperature}, #{humidity}, NOW())")
    void postdata(double temperature, double humidity, int deviceId);

    // 删除设备数据
    @Delete("DELETE FROM DeviceData WHERE id = #{id}")
    void deleteData(Integer id);

    // 搜索设备数据
    @Select("SELECT * FROM DeviceData WHERE device_id = #{deviceId}")
    List<DeviceData> SerchData(Integer deviceId);

    // 查询数据对应的设备（多对一关系）
    @Select("SELECT * FROM myDevice WHERE id = #{deviceId}")
    MyDevice findDeviceByDataId(Integer deviceId);
}
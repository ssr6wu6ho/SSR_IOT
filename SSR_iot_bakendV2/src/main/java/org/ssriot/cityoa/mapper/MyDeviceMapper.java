package org.ssriot.cityoa.mapper;

import org.apache.ibatis.annotations.*;
import org.ssriot.cityoa.pojo.DeviceData;
import org.ssriot.cityoa.pojo.MyDevice;
import java.util.List;

@Mapper
public interface MyDeviceMapper {
    // 查询所有设备
    @Select("SELECT * FROM myDevice")
    List<MyDevice> findAll();

    // 根据ID查询设备
    @Select("SELECT * FROM myDevice WHERE id = #{id}")
    MyDevice findById(Integer id);

    // 添加设备
    @Insert("INSERT INTO myDevice(deviceName, belong, type_id, create_time) " +
            "VALUES(#{deviceName}, #{belong}, #{typeId}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "ID")
    void insert(MyDevice device);

    // 更新设备信息
    @Update("UPDATE myDevice SET deviceName = #{deviceName}, belong = #{belong}, " +
            "type_id = #{typeId} WHERE id = #{ID}")
    void update(MyDevice device);

    // 删除设备
    @Delete("DELETE FROM myDevice WHERE id = #{id}")
    void delete(Integer id);

    // 查询设备的数据（一对多关系）
    @Select("SELECT * FROM DeviceData WHERE device_id = #{deviceId}")
    List<DeviceData> findDataByDeviceId(Integer deviceId);
}
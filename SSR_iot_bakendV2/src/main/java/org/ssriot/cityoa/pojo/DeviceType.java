package org.ssriot.cityoa.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor // 添加Lombok无参构造注解
//防止MyBatis映射结果时出现的构造函数参数不匹配问题
//您的DeviceType类定义了一个包含3个参数的构造函数：(Integer id, String typeName, List<MyDevice> devices)
//但SQL查询SELECT * FROM device_type只返回2个字段（id和type_name）
//MyBatis尝试使用构造函数映射时，找不到第3个参数（devices）对应的列数据
public class DeviceType {
    private Integer id;
    private String typeName;
    private List<MyDevice> devices; // 一对多关系


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<MyDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<MyDevice> devices) {
        this.devices = devices;
    }
}

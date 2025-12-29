package org.ssriot.cityoa.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceData {
    private Integer ID;
    private Double temperature;  // 改为Double类型
    private Double humidity;     // 改为Double类型
    private LocalDateTime updateTime;
    private Integer deviceId;    // 设备ID

    // 关联属性
    private MyDevice device; // 多对一关系


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public MyDevice getDevice() {
        return device;
    }

    public void setDevice(MyDevice device) {
        this.device = device;
    }
}

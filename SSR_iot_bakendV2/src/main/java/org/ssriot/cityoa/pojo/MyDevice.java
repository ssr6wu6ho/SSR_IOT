package org.ssriot.cityoa.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MyDevice {
    private Integer ID;
    private String deviceName;
    private Integer belong; // 所属用户ID
    private Integer typeId; // 设备类型ID
    private LocalDateTime createTime;

    // 关联属性
    private User owner; // 多对一关系
    private DeviceType deviceType; // 多对一关系
    private List<DeviceData> dataList; // 一对多关系
    private List<User> sharedUsers; // 多对多关系


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getBelong() {
        return belong;
    }

    public void setBelong(Integer belong) {
        this.belong = belong;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public List<DeviceData> getDataList() {
        return dataList;
    }

    public void setDataList(List<DeviceData> dataList) {
        this.dataList = dataList;
    }

    public List<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }
}

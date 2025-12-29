package org.ssriot.cityoa.service;

import org.ssriot.cityoa.pojo.DeviceType;
import org.ssriot.cityoa.pojo.MyDevice;
import java.util.List;

public interface DeviceTypeService {
    List<DeviceType> getAllDeviceTypes();
    DeviceType getDeviceTypeById(Integer id);
    void addDeviceType(DeviceType deviceType);
    void updateDeviceType(DeviceType deviceType);
    void deleteDeviceType(Integer id);
    List<MyDevice> getDevicesByTypeId(Integer typeId);
}
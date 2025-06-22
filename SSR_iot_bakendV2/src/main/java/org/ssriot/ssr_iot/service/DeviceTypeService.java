package org.ssriot.ssr_iot.service;

import org.ssriot.ssr_iot.pojo.DeviceType;
import org.ssriot.ssr_iot.pojo.MyDevice;
import java.util.List;

public interface DeviceTypeService {
    List<DeviceType> getAllDeviceTypes();
    DeviceType getDeviceTypeById(Integer id);
    void addDeviceType(DeviceType deviceType);
    void updateDeviceType(DeviceType deviceType);
    void deleteDeviceType(Integer id);
    List<MyDevice> getDevicesByTypeId(Integer typeId);
}
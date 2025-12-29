package org.ssriot.cityoa.service;

import org.ssriot.cityoa.pojo.DeviceData;
import org.ssriot.cityoa.pojo.MyDevice;
import java.util.List;

public interface MyDeviceService {
    List<MyDevice> getAllDevices();
    MyDevice getDeviceById(Integer id);
    void addDevice(MyDevice device);
    void updateDevice(MyDevice device);
    void deleteDevice(Integer id);
    List<DeviceData> getDeviceData(Integer deviceId);
}
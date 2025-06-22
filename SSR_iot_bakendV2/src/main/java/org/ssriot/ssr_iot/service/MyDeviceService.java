package org.ssriot.ssr_iot.service;

import org.ssriot.ssr_iot.pojo.DeviceData;
import org.ssriot.ssr_iot.pojo.MyDevice;
import java.util.List;

public interface MyDeviceService {
    List<MyDevice> getAllDevices();
    MyDevice getDeviceById(Integer id);
    void addDevice(MyDevice device);
    void updateDevice(MyDevice device);
    void deleteDevice(Integer id);
    List<DeviceData> getDeviceData(Integer deviceId);
}
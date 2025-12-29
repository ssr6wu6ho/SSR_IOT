package org.ssriot.cityoa.service;

import org.ssriot.cityoa.pojo.DeviceData;
import org.ssriot.cityoa.pojo.MyDevice;

import java.util.List;

public interface IotDataService {
    List<DeviceData> getDataList();
    void postData(double temperature, double humidity, int deviceId);
    void deleteData(Integer id);
    List<DeviceData> searchData(Integer deviceId);
    MyDevice getDeviceByDataId(Integer deviceId);
}
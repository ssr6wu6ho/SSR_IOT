package org.ssriot.ssr_iot.service;

import org.ssriot.ssr_iot.pojo.DeviceData;
import org.ssriot.ssr_iot.pojo.MyDevice;

import java.util.List;

public interface IotDataService {
    List<DeviceData> getDataList();
    void postData(double temperature, double humidity, int deviceId);
    void deleteData(Integer id);
    List<DeviceData> searchData(Integer deviceId);
    MyDevice getDeviceByDataId(Integer deviceId);
}
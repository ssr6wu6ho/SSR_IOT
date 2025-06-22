package org.ssriot.ssr_iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssriot.ssr_iot.mapper.IotDataMapper;
import org.ssriot.ssr_iot.pojo.DeviceData;
import org.ssriot.ssr_iot.pojo.MyDevice;
import org.ssriot.ssr_iot.service.IotDataService;
import java.util.List;

@Service
public class IotDataImpl implements IotDataService {

    @Autowired
    private IotDataMapper iotDataMapper;

    @Override
    public List<DeviceData> getDataList() {
        return iotDataMapper.getDeviceData();
    }

    @Override
    public void postData(double temperature, double humidity, int deviceId) {
        iotDataMapper.postdata(temperature, humidity, deviceId);
    }

    @Override
    public void deleteData(Integer id) {
        iotDataMapper.deleteData(id);
    }

    @Override
    public List<DeviceData> searchData(Integer deviceId) {
        return iotDataMapper.SerchData(deviceId);
    }

    @Override
    public MyDevice getDeviceByDataId(Integer deviceId) {
        return iotDataMapper.findDeviceByDataId(deviceId);
    }
}
package org.ssriot.cityoa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssriot.cityoa.mapper.IotDataMapper;
import org.ssriot.cityoa.pojo.DeviceData;
import org.ssriot.cityoa.pojo.MyDevice;
import org.ssriot.cityoa.service.IotDataService;
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
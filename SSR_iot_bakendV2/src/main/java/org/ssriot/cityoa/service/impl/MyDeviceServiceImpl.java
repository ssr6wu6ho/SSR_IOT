package org.ssriot.cityoa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssriot.cityoa.mapper.MyDeviceMapper;
import org.ssriot.cityoa.pojo.DeviceData;
import org.ssriot.cityoa.pojo.MyDevice;
import org.ssriot.cityoa.service.MyDeviceService;
import java.util.List;

@Service
public class MyDeviceServiceImpl implements MyDeviceService {

    @Autowired
    private MyDeviceMapper myDeviceMapper;

    @Override
    public List<MyDevice> getAllDevices() {
        return myDeviceMapper.findAll();
    }

    @Override
    public MyDevice getDeviceById(Integer id) {
        return myDeviceMapper.findById(id);
    }

    @Override
    public void addDevice(MyDevice device) {
        myDeviceMapper.insert(device);
    }

    @Override
    public void updateDevice(MyDevice device) {
        myDeviceMapper.update(device);
    }

    @Override
    public void deleteDevice(Integer id) {
        myDeviceMapper.delete(id);
    }

    @Override
    public List<DeviceData> getDeviceData(Integer deviceId) {
        return myDeviceMapper.findDataByDeviceId(deviceId);
    }
}
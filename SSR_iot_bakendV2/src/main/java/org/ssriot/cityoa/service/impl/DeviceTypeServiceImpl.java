package org.ssriot.cityoa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssriot.cityoa.mapper.DeviceTypeMapper;
import org.ssriot.cityoa.pojo.DeviceType;
import org.ssriot.cityoa.pojo.MyDevice;
import org.ssriot.cityoa.service.DeviceTypeService;
import java.util.List;

@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Override
    public List<DeviceType> getAllDeviceTypes() {
        return deviceTypeMapper.findAll();
    }

    @Override
    public DeviceType getDeviceTypeById(Integer id) {
        return deviceTypeMapper.findById(id);
    }

    @Override
    public void addDeviceType(DeviceType deviceType) {
        deviceTypeMapper.insert(deviceType);
    }

    @Override
    public void updateDeviceType(DeviceType deviceType) {
        deviceTypeMapper.update(deviceType);
    }

    @Override
    public void deleteDeviceType(Integer id) {
        deviceTypeMapper.delete(id);
    }

    @Override
    public List<MyDevice> getDevicesByTypeId(Integer typeId) {
        return deviceTypeMapper.findDevicesByTypeId(typeId);
    }
}
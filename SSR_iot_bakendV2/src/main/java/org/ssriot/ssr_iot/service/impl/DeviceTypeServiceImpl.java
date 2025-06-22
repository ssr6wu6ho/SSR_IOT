package org.ssriot.ssr_iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssriot.ssr_iot.mapper.DeviceTypeMapper;
import org.ssriot.ssr_iot.pojo.DeviceType;
import org.ssriot.ssr_iot.pojo.MyDevice;
import org.ssriot.ssr_iot.service.DeviceTypeService;
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
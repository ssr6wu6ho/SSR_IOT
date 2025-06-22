package org.ssriot.ssr_iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssriot.ssr_iot.mapper.UserDeviceMapper;
import org.ssriot.ssr_iot.pojo.UserDevice;
import org.ssriot.ssr_iot.service.UserDeviceService;
import java.util.List;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    @Autowired
    private UserDeviceMapper userDeviceMapper;

    @Override
    public List<UserDevice> getUserDevices(Integer userId) {
        return userDeviceMapper.findByUserId(userId);
    }

    @Override
    public List<UserDevice> getDeviceUsers(Integer deviceId) {
        return userDeviceMapper.findByDeviceId(deviceId);
    }

    @Override
    public void shareDevice(UserDevice userDevice) {
        userDeviceMapper.insert(userDevice);
    }

    @Override
    public void updateDevicePermission(UserDevice userDevice) {
        userDeviceMapper.updatePermission(userDevice);
    }

    @Override
    public void revokeDeviceAccess(Integer userDeviceId) {
        userDeviceMapper.delete(userDeviceId);
    }
}
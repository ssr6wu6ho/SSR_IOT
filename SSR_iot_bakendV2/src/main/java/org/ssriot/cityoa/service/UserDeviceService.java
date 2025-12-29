package org.ssriot.cityoa.service;

import org.ssriot.cityoa.pojo.UserDevice;
import java.util.List;

public interface UserDeviceService {
    List<UserDevice> getUserDevices(Integer userId);
    List<UserDevice> getDeviceUsers(Integer deviceId);
    void shareDevice(UserDevice userDevice);
    void updateDevicePermission(UserDevice userDevice);
    void revokeDeviceAccess(Integer userDeviceId);
}
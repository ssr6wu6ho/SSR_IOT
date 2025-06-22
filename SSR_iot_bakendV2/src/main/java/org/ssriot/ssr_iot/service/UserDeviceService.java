package org.ssriot.ssr_iot.service;

import org.ssriot.ssr_iot.pojo.UserDevice;
import java.util.List;

public interface UserDeviceService {
    List<UserDevice> getUserDevices(Integer userId);
    List<UserDevice> getDeviceUsers(Integer deviceId);
    void shareDevice(UserDevice userDevice);
    void updateDevicePermission(UserDevice userDevice);
    void revokeDeviceAccess(Integer userDeviceId);
}
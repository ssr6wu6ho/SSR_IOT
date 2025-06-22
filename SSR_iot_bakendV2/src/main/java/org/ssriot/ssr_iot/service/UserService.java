package org.ssriot.ssr_iot.service;

import org.ssriot.ssr_iot.pojo.MyDevice;
import org.ssriot.ssr_iot.pojo.User;
import java.util.List;

public interface UserService {
    User findByUserName(String username);
    void register(User user);
    List<MyDevice> getUserDevices(Integer userId);
    List<MyDevice> getUserSharedDevices(Integer userId);
}
package org.ssriot.cityoa.service;

import org.ssriot.cityoa.pojo.MyDevice;
import org.ssriot.cityoa.pojo.User;
import java.util.List;

public interface UserService {
    User findByUserName(String username);
    void register(User user);
    List<MyDevice> getUserDevices(Integer userId);
    List<MyDevice> getUserSharedDevices(Integer userId);
}
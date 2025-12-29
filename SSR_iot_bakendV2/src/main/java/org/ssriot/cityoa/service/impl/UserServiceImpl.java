package org.ssriot.cityoa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssriot.cityoa.mapper.UserMapper;
import org.ssriot.cityoa.pojo.MyDevice;
import org.ssriot.cityoa.pojo.User;
import org.ssriot.cityoa.service.UserService;
import org.ssriot.cityoa.utils.Md5Util;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(User user) {
        // 密码加密处理
        String encryptedPwd = Md5Util.getMD5String(user.getPassword());
        user.setPassword(encryptedPwd);
        userMapper.addUser(user);
    }

    @Override
    public List<MyDevice> getUserDevices(Integer userId) {
        return userMapper.findDevicesByUserId(userId);
    }

    @Override
    public List<MyDevice> getUserSharedDevices(Integer userId) {
        return userMapper.findSharedDevicesByUserId(userId);
    }
}
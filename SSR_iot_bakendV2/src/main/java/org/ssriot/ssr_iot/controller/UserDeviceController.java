package org.ssriot.ssr_iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ssriot.ssr_iot.pojo.Result;
import org.ssriot.ssr_iot.pojo.UserDevice;
import org.ssriot.ssr_iot.service.UserDeviceService;

import java.util.List;

@RestController
@RequestMapping("/user-device")
public class UserDeviceController {

    @Autowired
    private UserDeviceService userDeviceService;

    @GetMapping("/user/{userId}")
    public Result<List<UserDevice>> getUserDevices(@PathVariable Integer userId) {
        List<UserDevice> userDevices = userDeviceService.getUserDevices(userId);
        return Result.success(userDevices);
    }

    @GetMapping("/device/{deviceId}")
    public Result<List<UserDevice>> getDeviceUsers(@PathVariable Integer deviceId) {
        List<UserDevice> deviceUsers = userDeviceService.getDeviceUsers(deviceId);
        return Result.success(deviceUsers);
    }

    @PostMapping("/share")
    public Result shareDevice(@RequestBody UserDevice userDevice) {
        userDeviceService.shareDevice(userDevice);
        return Result.success();
    }

    @PutMapping("/permission")
    public Result updateDevicePermission(@RequestBody UserDevice userDevice) {
        userDeviceService.updateDevicePermission(userDevice);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result revokeDeviceAccess(@PathVariable Integer id) {
        userDeviceService.revokeDeviceAccess(id);
        return Result.success();
    }
}
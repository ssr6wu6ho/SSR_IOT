package org.ssriot.cityoa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ssriot.cityoa.pojo.DeviceData;
import org.ssriot.cityoa.pojo.MyDevice;
import org.ssriot.cityoa.pojo.Result;
import org.ssriot.cityoa.service.MyDeviceService;

import java.util.List;

@RestController
@RequestMapping("/device")
public class MyDeviceController {

    @Autowired
    private MyDeviceService myDeviceService;

    @GetMapping("/")
    public Result<List<MyDevice>> getAllDevices() {
        List<MyDevice> devices = myDeviceService.getAllDevices();
        return Result.success(devices);
    }

    @GetMapping("/{id}")
    public Result<MyDevice> getDeviceById(@PathVariable Integer id) {
        MyDevice device = myDeviceService.getDeviceById(id);
        return Result.success(device);
    }

    @PostMapping("/")
    public Result addDevice(@RequestBody MyDevice device) {
        myDeviceService.addDevice(device);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result updateDevice(@PathVariable Integer id, @RequestBody MyDevice device) {
        device.setID(id);
        myDeviceService.updateDevice(device);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteDevice(@PathVariable Integer id) {
        myDeviceService.deleteDevice(id);
        return Result.success();
    }

    @GetMapping("/{deviceId}/data")
    public Result<List<DeviceData>> getDeviceData(@PathVariable Integer deviceId) {
        List<DeviceData> dataList = myDeviceService.getDeviceData(deviceId);
        return Result.success(dataList);
    }
}
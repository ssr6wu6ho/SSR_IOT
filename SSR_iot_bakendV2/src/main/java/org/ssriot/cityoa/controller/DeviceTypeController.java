package org.ssriot.cityoa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ssriot.cityoa.pojo.DeviceType;
import org.ssriot.cityoa.pojo.MyDevice;
import org.ssriot.cityoa.pojo.Result;
import org.ssriot.cityoa.service.DeviceTypeService;

import java.util.List;

@RestController
@RequestMapping("/device-type")
public class DeviceTypeController {

    @Autowired
    private DeviceTypeService deviceTypeService;

    @GetMapping("/")
    public Result<List<DeviceType>> getAllDeviceTypes() {
        List<DeviceType> types = deviceTypeService.getAllDeviceTypes();
        return Result.success(types);
    }

    @GetMapping("/{id}")
    public Result<DeviceType> getDeviceTypeById(@PathVariable Integer id) {
        DeviceType type = deviceTypeService.getDeviceTypeById(id);
        return Result.success(type);
    }

    @PostMapping("/")
    public Result addDeviceType(@RequestBody DeviceType deviceType) {
        deviceTypeService.addDeviceType(deviceType);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result updateDeviceType(@PathVariable Integer id, @RequestBody DeviceType deviceType) {
        deviceType.setId(id);
        deviceTypeService.updateDeviceType(deviceType);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteDeviceType(@PathVariable Integer id) {
        deviceTypeService.deleteDeviceType(id);
        return Result.success();
    }

    @GetMapping("/{typeId}/devices")
    public Result<List<MyDevice>> getDevicesByTypeId(@PathVariable Integer typeId) {
        List<MyDevice> devices = deviceTypeService.getDevicesByTypeId(typeId);
        return Result.success(devices);
    }
}
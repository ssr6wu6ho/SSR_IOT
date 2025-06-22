package org.ssriot.ssr_iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ssriot.ssr_iot.pojo.DeviceData;
import org.ssriot.ssr_iot.pojo.MyDevice;
import org.ssriot.ssr_iot.pojo.Result;
import org.ssriot.ssr_iot.service.IotDataService;

import java.util.List;

@RestController
@RequestMapping("/iot-data")
public class IotDataController {

    @Autowired
    private IotDataService iotDataService;

    @PostMapping("/")
    public Result postData(@RequestParam Double temperature,
                           @RequestParam Double humidity,
                           @RequestParam Integer deviceId) {
        if (temperature == null || humidity == null || deviceId == null) {
            return Result.error("参数不能为空");
        }
        iotDataService.postData(temperature, humidity, deviceId);
        return Result.success();
    }

    @GetMapping("/")
    public Result<List<DeviceData>> getAllData() {
        List<DeviceData> dataList = iotDataService.getDataList();
        return Result.success(dataList);
    }

    @DeleteMapping("/{id}")
    public Result deleteData(@PathVariable Integer id) {
        iotDataService.deleteData(id);
        return Result.success();
    }

    @GetMapping("/search")
    public Result<List<DeviceData>> searchData(@RequestParam Integer deviceId) {
        List<DeviceData> dataList = iotDataService.searchData(deviceId);
        return Result.success(dataList);
    }

    @GetMapping("/{id}/device")
    public Result<MyDevice> getDeviceByDataId(@PathVariable Integer id) {
        // 注意：这里需要从数据ID获取设备信息
        MyDevice device = iotDataService.getDeviceByDataId(id);
        return Result.success(device);
    }
}
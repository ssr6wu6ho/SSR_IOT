package org.ssriot.ssr_iot.controller;

import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ssriot.ssr_iot.pojo.MyDevice;
import org.ssriot.ssr_iot.pojo.Result;
import org.ssriot.ssr_iot.pojo.User;
import org.ssriot.ssr_iot.service.UserService;
import org.ssriot.ssr_iot.utils.JwtUtil;
import org.ssriot.ssr_iot.utils.Md5Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{3,16}$") String username,
                           @Pattern(regexp = "^\\S{6,16}$") String password) {
        User existingUser = userService.findByUserName(username);
        if (existingUser == null) {
            // 创建新用户对象
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            userService.register(newUser);
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{3,16}$") String username,
                                @Pattern(regexp = "^\\S{6,16}$") String password) {
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error("用户名不存在");
        }

        if (Md5Util.checkPassword(password, user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }

    @GetMapping("/devices")
    public Result<List<MyDevice>> getUserDevices(@RequestHeader("Authorization") String token) {
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("id");
        List<MyDevice> devices = userService.getUserDevices(userId);
        return Result.success(devices);
    }

    @GetMapping("/shared-devices")
    public Result<List<MyDevice>> getUserSharedDevices(@RequestHeader("Authorization") String token) {
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Integer userId = (Integer) claims.get("id");
        List<MyDevice> devices = userService.getUserSharedDevices(userId);
        return Result.success(devices);
    }
}
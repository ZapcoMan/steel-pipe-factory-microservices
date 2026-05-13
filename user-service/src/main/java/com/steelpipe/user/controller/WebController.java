package com.steelpipe.user.controller;

import com.steelpipe.common.result.R;
import com.steelpipe.user.entity.Account;
import com.steelpipe.user.service.AdminService;
import com.steelpipe.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @GetMapping("/hello")
    public R hello() {
        return R.ok().message("hello");
    }

    @PostMapping("/login")
    public R login(@Valid @RequestBody Account account) {
        Object user;
        if ("ADMIN".equals(account.getRole())) {
            user = adminService.login(account);
        } else {
            user = userService.login(account);
        }
        return R.success(user);
    }

    @PostMapping("/updatePassword")
    public R updatePassword(@RequestBody Account account) {
        if ("ADMIN".equals(account.getRole())) {
            adminService.updatePassword(account);
        } else {
            userService.updatePassword(account);
        }
        return R.ok();
    }
}

package com.steelpipe.user.controller;

import com.github.pagehelper.PageInfo;
import com.steelpipe.common.result.R;
import com.steelpipe.user.entity.User;
import com.steelpipe.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public R add(@RequestBody User user) {
        userService.add(user);
        return R.ok();
    }

    @PutMapping("/update")
    public R update(@RequestBody User user) {
        userService.update(user);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        userService.deleteById(id);
        return R.ok();
    }

    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<User> list) {
        userService.deleteBatch(list);
        return R.ok();
    }

    @GetMapping("/selectAll")
    public R selectAll() {
        List<User> list = userService.selectAll();
        return R.ok().data("userList", list);
    }

    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             User user) {
        PageInfo<User> pageInfo = userService.selectPage(pageNum, pageSize, user);
        return R.ok().data("pageInfo", pageInfo);
    }
}

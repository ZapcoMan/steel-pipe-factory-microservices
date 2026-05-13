package com.steelpipe.user.controller;

import com.github.pagehelper.PageInfo;
import com.steelpipe.common.result.R;
import com.steelpipe.user.entity.Admin;
import com.steelpipe.user.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping("/add")
    public R add(@RequestBody Admin admin) {
        adminService.add(admin);
        return R.ok();
    }

    @PutMapping("/update")
    public R update(@RequestBody Admin admin) {
        adminService.update(admin);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        adminService.deleteById(id);
        return R.ok();
    }

    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Admin> list) {
        adminService.deleteBatch(list);
        return R.ok();
    }

    @GetMapping("/selectAll")
    public R selectAll() {
        List<Admin> list = adminService.selectAll();
        return R.ok().data("adminList", list);
    }

    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Admin admin) {
        PageInfo<Admin> pageInfo = adminService.selectPage(pageNum, pageSize, admin);
        return R.ok().data("pageInfo", pageInfo);
    }
}

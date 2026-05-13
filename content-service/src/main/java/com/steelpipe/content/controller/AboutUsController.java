package com.steelpipe.content.controller;

import com.github.pagehelper.PageInfo;
import com.steelpipe.common.result.R;
import com.steelpipe.content.entity.AboutUs;
import com.steelpipe.content.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aboutUs")
public class AboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    @GetMapping("/selectAll")
    public R selectAll() {
        return R.success(aboutUsService.selectAll());
    }

    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<AboutUs> page = aboutUsService.selectPage(pageNum, pageSize);
        return R.ok().data("pageInfo", page);
    }

    @GetMapping("/selectById")
    public R selectById(@RequestParam Integer id) {
        return R.success(aboutUsService.selectById(id));
    }

    @PostMapping("/insert")
    public R insert(@RequestBody AboutUs aboutUs) {
        aboutUsService.insert(aboutUs);
        return R.ok().message("新增成功");
    }

    @PutMapping("/updateById")
    public R updateById(@RequestBody AboutUs aboutUs) {
        aboutUsService.updateById(aboutUs);
        return R.ok().message("更新成功");
    }

    @DeleteMapping("/deleteById")
    public R deleteById(@RequestParam Integer id) {
        aboutUsService.deleteById(id);
        return R.ok().message("删除成功");
    }

}
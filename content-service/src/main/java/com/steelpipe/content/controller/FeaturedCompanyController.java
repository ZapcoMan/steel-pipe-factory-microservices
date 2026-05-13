package com.steelpipe.content.controller;

import com.github.pagehelper.PageInfo;
import com.steelpipe.common.result.R;
import com.steelpipe.content.entity.FeaturedCompany;
import com.steelpipe.content.service.FeaturedCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/featuredCompany")
public class FeaturedCompanyController {

    @Autowired
    private FeaturedCompanyService featuredCompanyService;

    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        FeaturedCompany fc) {
        PageInfo<FeaturedCompany> page = featuredCompanyService.selectPage(pageNum, pageSize, fc);
        return R.ok().data("pageInfo", page);
    }

    @GetMapping("/selectAll")
    public R selectAll(FeaturedCompany fc) {
        return R.ok().data("featuredCompanyList", featuredCompanyService.selectAll(fc));
    }

    @PostMapping("/add")
    public R add(@RequestBody FeaturedCompany fc) {
        featuredCompanyService.insert(fc);
        return R.ok().message("新增成功");
    }

    @PutMapping("/update")
    public R update(@RequestBody FeaturedCompany fc) {
        featuredCompanyService.updateById(fc);
        return R.ok().message("更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        featuredCompanyService.deleteById(id);
        return R.ok().message("删除成功");
    }

    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<FeaturedCompany> list) {
        featuredCompanyService.deleteBatch(list);
        return R.ok().message("批量删除成功");
    }

}
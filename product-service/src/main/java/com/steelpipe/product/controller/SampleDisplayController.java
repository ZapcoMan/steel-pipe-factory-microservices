package com.steelpipe.product.controller;

import com.github.pagehelper.PageInfo;
import com.steelpipe.common.result.R;
import com.steelpipe.product.entity.SampleDisplay;
import com.steelpipe.product.service.SampleDisplayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sampleDisplay")
public class SampleDisplayController {

    @Autowired
    private SampleDisplayService sampleDisplayService;

    @GetMapping("/selectAll")
    public R selectAll() {
        List<SampleDisplay> list = sampleDisplayService.selectAll();
        return R.success(list);
    }

    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String category) {
        PageInfo<SampleDisplay> pageInfo = sampleDisplayService.selectPage(pageNum, pageSize, name, category);
        return R.ok().data("pageInfo", pageInfo);
    }

    @PostMapping("/add")
    public R add(@Valid @RequestBody SampleDisplay sampleDisplay) {
        sampleDisplayService.insert(sampleDisplay);
        return R.ok();
    }

    @PutMapping("/update")
    public R update(@Valid @RequestBody SampleDisplay sampleDisplay) {
        sampleDisplayService.updateById(sampleDisplay);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        sampleDisplayService.deleteById(id);
        return R.ok();
    }

    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            sampleDisplayService.deleteById(id);
        }
        return R.ok();
    }
}

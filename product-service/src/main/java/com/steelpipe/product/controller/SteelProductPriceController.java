package com.steelpipe.product.controller;

import com.github.pagehelper.PageInfo;
import com.steelpipe.common.result.R;
import com.steelpipe.product.entity.SteelProductPrice;
import com.steelpipe.product.service.SteelProductPriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/steelProductPrice")
public class SteelProductPriceController {

    @Autowired
    private SteelProductPriceService steelProductPriceService;

    @PostMapping("/add")
    public R add(@Valid @RequestBody SteelProductPrice steelProductPrice) {
        steelProductPriceService.add(steelProductPrice);
        return R.ok();
    }

    @PutMapping("/update")
    public R update(@Valid @RequestBody SteelProductPrice steelProductPrice) {
        steelProductPriceService.update(steelProductPrice);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        steelProductPriceService.deleteById(id);
        return R.ok();
    }

    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<SteelProductPrice> list) {
        steelProductPriceService.deleteBatch(list);
        return R.ok();
    }

    @GetMapping("/selectAll")
    public R selectAll() {
        List<SteelProductPrice> list = steelProductPriceService.selectAll();
        return R.ok().data("steelProductPriceList", list);
    }

    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             SteelProductPrice steelProductPrice) {
        PageInfo<SteelProductPrice> pageInfo = steelProductPriceService.selectPage(pageNum, pageSize, steelProductPrice);
        return R.ok().data("pageInfo", pageInfo);
    }
}

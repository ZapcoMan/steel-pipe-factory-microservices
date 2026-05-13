package com.steelpipe.content.controller;

import com.github.pagehelper.PageInfo;
import com.steelpipe.common.result.R;
import com.steelpipe.content.entity.ObtainInformation;
import com.steelpipe.content.service.ObtainInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obtainInformation")
public class ObtainInformationController {

    @Autowired
    private ObtainInformationService obtainInformationService;

    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        ObtainInformation oi) {
        PageInfo<ObtainInformation> page = obtainInformationService.selectPage(pageNum, pageSize, oi);
        return R.ok().data("pageInfo", page);
    }

    @GetMapping("/selectAll")
    public R selectAll(ObtainInformation oi) {
        return R.ok().data("obtainInformationList", obtainInformationService.selectAll(oi));
    }

    @PostMapping("/add")
    public R add(@RequestBody ObtainInformation oi) {
        obtainInformationService.insert(oi);
        return R.ok().message("新增成功");
    }

    @PutMapping("/update")
    public R update(@RequestBody ObtainInformation oi) {
        obtainInformationService.updateById(oi);
        return R.ok().message("更新成功");
    }

    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        obtainInformationService.deleteById(id);
        return R.ok().message("删除成功");
    }

    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<ObtainInformation> list) {
        obtainInformationService.deleteBatch(list);
        return R.ok().message("批量删除成功");
    }

}
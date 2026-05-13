package com.steelpipe.content.controller;

import com.steelpipe.common.result.R;
import com.steelpipe.content.service.AboutUsService;
import com.steelpipe.content.service.FeaturedCompanyService;
import com.steelpipe.content.service.ObtainInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/uniapp")
public class UniappController {

    @Autowired
    private FeaturedCompanyService featuredCompanyService;

    @Autowired
    private ObtainInformationService obtainInformationService;

    @Autowired
    private AboutUsService aboutUsService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getAboutData")
    public R getAboutData() {
        java.util.List<?> dataList = aboutUsService.selectAll();
        if (dataList != null && !dataList.isEmpty()) {
            return R.success(dataList.get(0));
        }
        return R.error("暂无关于我们数据");
    }

    @GetMapping("/getfeaturedCompaniesDataList")
    public R getfeaturedCompaniesDataList() {
        return R.success(featuredCompanyService.selectAll());
    }

    @GetMapping("/getObtainInformationDataList")
    public R getObtainInformationDataList() {
        return R.success(obtainInformationService.selectAll());
    }

    @GetMapping("/getIndustryNewsDetailData")
    public R getIndustryNewsDetailData(@RequestParam Integer id) {
        return R.success(obtainInformationService.selectById(id));
    }

    @GetMapping("/getSteelPriceData")
    public R getSteelPriceData() {
        try {
            Object data = restTemplate.getForObject("http://product-service/steelProductPrice/selectAll", Object.class);
            return R.success(data);
        } catch (Exception e) {
            return R.error("获取钢材价格数据失败");
        }
    }

    @GetMapping("/getSampleData")
    public R getSampleData() {
        try {
            Object data = restTemplate.getForObject("http://product-service/sampleDisplay/selectAll", Object.class);
            return R.success(data);
        } catch (Exception e) {
            return R.error("获取样品数据失败");
        }
    }
}

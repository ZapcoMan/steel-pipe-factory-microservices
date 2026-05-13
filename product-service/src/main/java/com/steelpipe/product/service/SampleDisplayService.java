package com.steelpipe.product.service;

import com.github.pagehelper.PageInfo;
import com.steelpipe.product.entity.SampleDisplay;

import java.util.List;

public interface SampleDisplayService {

    List<SampleDisplay> selectAll();

    PageInfo<SampleDisplay> selectPage(Integer pageNum, Integer pageSize, String name, String category);

    SampleDisplay selectById(Integer id);

    void insert(SampleDisplay sampleDisplay);

    void updateById(SampleDisplay sampleDisplay);

    void deleteById(Integer id);
}

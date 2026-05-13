package com.steelpipe.content.service;

import com.github.pagehelper.PageInfo;
import com.steelpipe.content.entity.FeaturedCompany;

import java.util.List;

public interface FeaturedCompanyService {
    List<FeaturedCompany> selectAll();
    List<FeaturedCompany> selectAll(FeaturedCompany fc);
    PageInfo<FeaturedCompany> selectPage(Integer pageNum, Integer pageSize, FeaturedCompany fc);
    void insert(FeaturedCompany fc);
    void updateById(FeaturedCompany fc);
    void deleteById(Integer id);
    void deleteBatch(List<FeaturedCompany> list);
}
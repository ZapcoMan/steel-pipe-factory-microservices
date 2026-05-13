package com.steelpipe.content.service;

import com.github.pagehelper.PageInfo;
import com.steelpipe.content.entity.AboutUs;

import java.util.List;

public interface AboutUsService {
    List<AboutUs> selectAll();
    AboutUs selectById(Integer id);
    PageInfo<AboutUs> selectPage(Integer pageNum, Integer pageSize);
    void insert(AboutUs aboutUs);
    void updateById(AboutUs aboutUs);
    void deleteById(Integer id);
}
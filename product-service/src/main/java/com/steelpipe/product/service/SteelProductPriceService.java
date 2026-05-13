package com.steelpipe.product.service;

import com.github.pagehelper.PageInfo;
import com.steelpipe.product.entity.SteelProductPrice;

import java.util.List;

public interface SteelProductPriceService {

    void add(SteelProductPrice steelProductPrice);

    void update(SteelProductPrice steelProductPrice);

    void deleteById(Integer id);

    void deleteBatch(List<SteelProductPrice> list);

    List<SteelProductPrice> selectAll();

    PageInfo<SteelProductPrice> selectPage(Integer pageNum, Integer pageSize, SteelProductPrice steelProductPrice);
}

package com.steelpipe.product.mapper;

import com.steelpipe.product.entity.SteelProductPrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SteelProductPriceMapper {

    void insert(SteelProductPrice steelProductPrice);

    void updateById(SteelProductPrice steelProductPrice);

    List<SteelProductPrice> selectAll();

    List<SteelProductPrice> selectAll(SteelProductPrice steelProductPrice);

    void deleteById(Integer id);

    void deleteBatch(List<SteelProductPrice> list);
}

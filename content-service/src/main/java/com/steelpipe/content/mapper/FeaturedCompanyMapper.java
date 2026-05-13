package com.steelpipe.content.mapper;

import com.steelpipe.content.entity.FeaturedCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeaturedCompanyMapper {
    void insert(FeaturedCompany fc);
    void updateById(FeaturedCompany fc);
    List<FeaturedCompany> selectAll();
    List<FeaturedCompany> selectAll(FeaturedCompany fc);
    void deleteById(Integer id);
    void deleteBatch(List<FeaturedCompany> list);
}
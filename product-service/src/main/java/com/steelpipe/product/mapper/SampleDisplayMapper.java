package com.steelpipe.product.mapper;

import com.steelpipe.product.entity.SampleDisplay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SampleDisplayMapper {

    List<SampleDisplay> selectAll();

    List<SampleDisplay> selectPage(@Param("name") String name, @Param("category") String category);

    SampleDisplay selectById(@Param("id") Integer id);

    int insert(SampleDisplay sampleDisplay);

    int updateById(SampleDisplay sampleDisplay);

    int deleteById(@Param("id") Integer id);
}

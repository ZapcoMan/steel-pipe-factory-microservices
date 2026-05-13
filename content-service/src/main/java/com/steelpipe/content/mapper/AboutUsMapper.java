package com.steelpipe.content.mapper;

import com.steelpipe.content.entity.AboutUs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AboutUsMapper {
    List<AboutUs> selectAll();
    AboutUs selectById(@Param("id") Integer id);
    int insert(AboutUs aboutUs);
    int updateById(AboutUs aboutUs);
    int deleteById(@Param("id") Integer id);
}
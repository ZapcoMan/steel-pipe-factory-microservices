package com.steelpipe.content.mapper;

import com.steelpipe.content.entity.ObtainInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ObtainInformationMapper {
    void insert(ObtainInformation oi);
    void updateById(ObtainInformation oi);
    void deleteById(Integer id);
    void deleteBatch(List<ObtainInformation> list);
    List<ObtainInformation> selectAll();
    List<ObtainInformation> selectAll(ObtainInformation oi);
    ObtainInformation selectById(Integer id);
}
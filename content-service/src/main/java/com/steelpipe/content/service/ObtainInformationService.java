package com.steelpipe.content.service;

import com.github.pagehelper.PageInfo;
import com.steelpipe.content.entity.ObtainInformation;

import java.util.List;

public interface ObtainInformationService {
    List<ObtainInformation> selectAll();
    List<ObtainInformation> selectAll(ObtainInformation oi);
    ObtainInformation selectById(Integer id);
    PageInfo<ObtainInformation> selectPage(Integer pageNum, Integer pageSize, ObtainInformation oi);
    void insert(ObtainInformation oi);
    void updateById(ObtainInformation oi);
    void deleteById(Integer id);
    void deleteBatch(List<ObtainInformation> list);
}
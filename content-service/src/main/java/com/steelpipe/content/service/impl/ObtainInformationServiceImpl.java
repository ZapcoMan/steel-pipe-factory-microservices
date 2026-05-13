package com.steelpipe.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.steelpipe.content.entity.ObtainInformation;
import com.steelpipe.content.mapper.ObtainInformationMapper;
import com.steelpipe.content.service.ObtainInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ObtainInformationServiceImpl implements ObtainInformationService {

    private static final String CACHE_KEY = "obtain:info:all";
    private static final long CACHE_TIMEOUT = 10;

    @Autowired
    private ObtainInformationMapper obtainInformationMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<ObtainInformation> selectAll() {
        Object cached = redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            return (List<ObtainInformation>) cached;
        }
        List<ObtainInformation> list = obtainInformationMapper.selectAll();
        redisTemplate.opsForValue().set(CACHE_KEY, list, CACHE_TIMEOUT, TimeUnit.MINUTES);
        return list;
    }

    @Override
    public List<ObtainInformation> selectAll(ObtainInformation oi) {
        return obtainInformationMapper.selectAll(oi);
    }

    @Override
    public ObtainInformation selectById(Integer id) {
        return obtainInformationMapper.selectById(id);
    }

    @Override
    public PageInfo<ObtainInformation> selectPage(Integer pageNum, Integer pageSize, ObtainInformation oi) {
        PageHelper.startPage(pageNum, pageSize);
        List<ObtainInformation> list = obtainInformationMapper.selectAll(oi);
        return new PageInfo<>(list);
    }

    @Override
    public void insert(ObtainInformation oi) {
        obtainInformationMapper.insert(oi);
        clearCache();
    }

    @Override
    public void updateById(ObtainInformation oi) {
        obtainInformationMapper.updateById(oi);
        clearCache();
    }

    @Override
    public void deleteById(Integer id) {
        obtainInformationMapper.deleteById(id);
        clearCache();
    }

    @Override
    public void deleteBatch(List<ObtainInformation> list) {
        obtainInformationMapper.deleteBatch(list);
        clearCache();
    }

    private void clearCache() {
        redisTemplate.delete(CACHE_KEY);
    }
}
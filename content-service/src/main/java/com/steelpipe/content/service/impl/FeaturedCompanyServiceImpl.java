package com.steelpipe.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.steelpipe.content.entity.FeaturedCompany;
import com.steelpipe.content.mapper.FeaturedCompanyMapper;
import com.steelpipe.content.service.FeaturedCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FeaturedCompanyServiceImpl implements FeaturedCompanyService {

    private static final String CACHE_KEY = "featured:company:all";
    private static final long CACHE_TIMEOUT = 10;

    @Autowired
    private FeaturedCompanyMapper featuredCompanyMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<FeaturedCompany> selectAll() {
        Object cached = redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            return (List<FeaturedCompany>) cached;
        }
        List<FeaturedCompany> list = featuredCompanyMapper.selectAll();
        redisTemplate.opsForValue().set(CACHE_KEY, list, CACHE_TIMEOUT, TimeUnit.MINUTES);
        return list;
    }

    @Override
    public List<FeaturedCompany> selectAll(FeaturedCompany fc) {
        return featuredCompanyMapper.selectAll(fc);
    }

    @Override
    public PageInfo<FeaturedCompany> selectPage(Integer pageNum, Integer pageSize, FeaturedCompany fc) {
        PageHelper.startPage(pageNum, pageSize);
        List<FeaturedCompany> list = featuredCompanyMapper.selectAll(fc);
        return new PageInfo<>(list);
    }

    @Override
    public void insert(FeaturedCompany fc) {
        featuredCompanyMapper.insert(fc);
        clearCache();
    }

    @Override
    public void updateById(FeaturedCompany fc) {
        featuredCompanyMapper.updateById(fc);
        clearCache();
    }

    @Override
    public void deleteById(Integer id) {
        featuredCompanyMapper.deleteById(id);
        clearCache();
    }

    @Override
    public void deleteBatch(List<FeaturedCompany> list) {
        featuredCompanyMapper.deleteBatch(list);
        clearCache();
    }

    private void clearCache() {
        redisTemplate.delete(CACHE_KEY);
    }
}
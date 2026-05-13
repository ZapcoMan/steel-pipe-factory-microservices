package com.steelpipe.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.steelpipe.content.entity.AboutUs;
import com.steelpipe.content.mapper.AboutUsMapper;
import com.steelpipe.content.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    private static final String CACHE_KEY = "about:us:all";
    private static final long CACHE_TIMEOUT = 10;

    @Autowired
    private AboutUsMapper aboutUsMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<AboutUs> selectAll() {
        // 先从Redis缓存获取
        Object cached = redisTemplate.opsForValue().get(CACHE_KEY);
        if (cached != null) {
            return (List<AboutUs>) cached;
        }
        // 缓存未命中，查询数据库
        List<AboutUs> list = aboutUsMapper.selectAll();
        // 写入缓存，10分钟过期
        redisTemplate.opsForValue().set(CACHE_KEY, list, CACHE_TIMEOUT, TimeUnit.MINUTES);
        return list;
    }

    @Override
    public AboutUs selectById(Integer id) {
        return aboutUsMapper.selectById(id);
    }

    @Override
    public PageInfo<AboutUs> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AboutUs> list = aboutUsMapper.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public void insert(AboutUs aboutUs) {
        aboutUsMapper.insert(aboutUs);
        clearCache();
    }

    @Override
    public void updateById(AboutUs aboutUs) {
        aboutUsMapper.updateById(aboutUs);
        clearCache();
    }

    @Override
    public void deleteById(Integer id) {
        aboutUsMapper.deleteById(id);
        clearCache();
    }

    private void clearCache() {
        redisTemplate.delete(CACHE_KEY);
    }
}
package com.steelpipe.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.steelpipe.product.entity.SampleDisplay;
import com.steelpipe.product.mapper.SampleDisplayMapper;
import com.steelpipe.product.service.SampleDisplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SampleDisplayServiceImpl implements SampleDisplayService {

    private static final Logger log = LoggerFactory.getLogger(SampleDisplayServiceImpl.class);

    private static final String CACHE_KEY_ALL = "sample:display:all";
    private static final long CACHE_EXPIRE_MINUTES = 10;

    @Autowired
    private SampleDisplayMapper sampleDisplayMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<SampleDisplay> selectAll() {
        // 先查缓存
        String cached = stringRedisTemplate.opsForValue().get(CACHE_KEY_ALL);
        if (cached != null && !cached.isEmpty()) {
            try {
                return objectMapper.readValue(cached,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, SampleDisplay.class));
            } catch (Exception e) {
                log.error("反序列化缓存数据失败", e);
            }
        }
        // 缓存未命中，查数据库
        List<SampleDisplay> list = sampleDisplayMapper.selectAll();
        try {
            String json = objectMapper.writeValueAsString(list);
            stringRedisTemplate.opsForValue().set(CACHE_KEY_ALL, json, CACHE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("序列化写入缓存失败", e);
        }
        return list;
    }

    @Override
    public PageInfo<SampleDisplay> selectPage(Integer pageNum, Integer pageSize, String name, String category) {
        PageHelper.startPage(pageNum, pageSize);
        List<SampleDisplay> list = sampleDisplayMapper.selectPage(name, category);
        return new PageInfo<>(list);
    }

    @Override
    public SampleDisplay selectById(Integer id) {
        return sampleDisplayMapper.selectById(id);
    }

    @Override
    public void insert(SampleDisplay sampleDisplay) {
        sampleDisplayMapper.insert(sampleDisplay);
        clearCache();
    }

    @Override
    public void updateById(SampleDisplay sampleDisplay) {
        sampleDisplayMapper.updateById(sampleDisplay);
        clearCache();
    }

    @Override
    public void deleteById(Integer id) {
        sampleDisplayMapper.deleteById(id);
        clearCache();
    }

    private void clearCache() {
        stringRedisTemplate.delete(CACHE_KEY_ALL);
    }
}

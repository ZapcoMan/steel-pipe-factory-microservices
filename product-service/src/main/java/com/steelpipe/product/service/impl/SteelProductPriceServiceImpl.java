package com.steelpipe.product.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.steelpipe.product.entity.SteelProductPrice;
import com.steelpipe.product.mapper.SteelProductPriceMapper;
import com.steelpipe.product.service.SteelProductPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SteelProductPriceServiceImpl implements SteelProductPriceService {

    private static final Logger log = LoggerFactory.getLogger(SteelProductPriceServiceImpl.class);

    private static final String CACHE_KEY_ALL = "steel:price:all";
    private static final long CACHE_EXPIRE_MINUTES = 5;
    private static final long DELAY_MS = 500;

    @Autowired
    private SteelProductPriceMapper steelProductPriceMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void add(SteelProductPrice steelProductPrice) {
        steelProductPriceMapper.insert(steelProductPrice);
        delayedDoubleDelete();
    }

    @Override
    public void update(SteelProductPrice steelProductPrice) {
        steelProductPriceMapper.updateById(steelProductPrice);
        delayedDoubleDelete();
    }

    @Override
    public void deleteById(Integer id) {
        steelProductPriceMapper.deleteById(id);
        delayedDoubleDelete();
    }

    @Override
    public void deleteBatch(List<SteelProductPrice> list) {
        steelProductPriceMapper.deleteBatch(list);
        delayedDoubleDelete();
    }

    @Override
    public List<SteelProductPrice> selectAll() {
        // 先查缓存
        String cached = stringRedisTemplate.opsForValue().get(CACHE_KEY_ALL);
        if (cached != null && !cached.isEmpty()) {
            try {
                return objectMapper.readValue(cached,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, SteelProductPrice.class));
            } catch (Exception e) {
                log.error("反序列化缓存数据失败", e);
            }
        }
        // 缓存未命中，查数据库
        List<SteelProductPrice> list = steelProductPriceMapper.selectAll();
        try {
            String json = objectMapper.writeValueAsString(list);
            stringRedisTemplate.opsForValue().set(CACHE_KEY_ALL, json, CACHE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("序列化写入缓存失败", e);
        }
        return list;
    }

    @Override
    public PageInfo<SteelProductPrice> selectPage(Integer pageNum, Integer pageSize, SteelProductPrice steelProductPrice) {
        PageHelper.startPage(pageNum, pageSize);
        List<SteelProductPrice> list = steelProductPriceMapper.selectAll(steelProductPrice);
        return new PageInfo<>(list);
    }

    /**
     * 延迟双删策略：立即删除缓存 + 500ms后再次删除
     */
    private void delayedDoubleDelete() {
        // 第一次删除
        stringRedisTemplate.delete(CACHE_KEY_ALL);
        // 延迟第二次删除
        new Thread(() -> {
            try {
                Thread.sleep(DELAY_MS);
                stringRedisTemplate.delete(CACHE_KEY_ALL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}

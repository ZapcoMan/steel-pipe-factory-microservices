<template>
  <!-- 顶部品牌区 -->
  <BrandHeader />

  <!-- 主功能入口 -->
  <MainFeatures />

  <!-- 精选合作企业展示 -->
  <FeaturedCompanies />

  <!-- 资讯滚动区 -->
  <NewsSection />

  <!-- 快捷入口 -->
  <!-- 已移除首页快捷入口，按需求首页不需要任何按钮 -->
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import BrandHeader from '@/components/BrandHeader.vue'
import MainFeatures from '@/components/MainFeatures.vue'
import FeaturedCompanies from '@/components/FeaturedCompanies.vue'
import NewsSection from '@/components/NewsSection.vue'
import QuickActions from '@/components/QuickActions.vue'
import homeApi from '@/api/homeApi'

// 页面加载时获取数据
onMounted(() => {
  homeApi.getHomeData().then((res: any) => {
    if (res && res.message) {
      console.log(res)
    }
  }).catch(()=>{
    console.log('获取数据失败')
  })
})
</script>

<style scoped>
page {
  background-color: #f5f5f5;
  padding-bottom: 20rpx;
}

/* 钢铁工业风格的顶部品牌区 */
.brand-header {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  padding: 60rpx 30rpx 40rpx;
  text-align: center;
  color: white;
  position: relative;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.15);
}

.logo {
  width: 120rpx;
  height: 120rpx;
  border-radius: 50%;
  border: 4rpx solid rgba(255, 255, 255, 0.3);
  margin-bottom: 20rpx;
  background: #f0f0f0;
  padding: 10rpx;
}

.brand-name {
  font-size: 36rpx;
  font-weight: bold;
  display: block;
  margin-bottom: 10rpx;
  letter-spacing: 2rpx;
  text-shadow: 2rpx 2rpx 4rpx rgba(0, 0, 0, 0.3);
}

.subtitle {
  font-size: 26rpx;
  opacity: 0.9;
  color: #ecf0f1;
}

.main-features {
  padding: 30rpx;
}

.feature-card {
  background: linear-gradient(135deg, #34495e 0%, #2c3e50 100%);
  border-radius: 20rpx;
  padding: 40rpx 30rpx;
  margin-bottom: 20rpx;
  color: white;
  box-shadow: 0 8rpx 30rpx rgba(52, 73, 94, 0.4);
  position: relative;
  overflow: hidden;
}

/* 添加工业风格的装饰元素 */
.feature-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4rpx;
  background: linear-gradient(90deg, #e74c3c, #f39c12, #e74c3c);
}

.card-icon {
  font-size: 48rpx;
  display: block;
  margin-bottom: 15rpx;
  text-align: center;
}

.card-title {
  font-size: 32rpx;
  font-weight: bold;
  display: block;
  margin-bottom: 10rpx;
  text-align: center;
}

.card-desc {
  font-size: 26rpx;
  opacity: 0.8;
  text-align: center;
}

.feature-row {
  display: flex;
  gap: 20rpx;
}

.small-card {
  flex: 1;
  background-color: white;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 15rpx rgba(0, 0, 0, 0.08);
}

.small-card .card-icon {
  font-size: 36rpx;
  margin-bottom: 10rpx;
}

.small-card .card-title {
  font-size: 28rpx;
  color: #333;
  font-weight: 500;
}

.featured-companies {
  background-color: white;
  margin: 0 30rpx 20rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
  padding-bottom: 15rpx;
  border-bottom: 2rpx solid #ecf0f1;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #2c3e50;
  position: relative;
  padding-left: 20rpx;
}

.section-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 8rpx;
  height: 30rpx;
  background: linear-gradient(to bottom, #3498db, #2980b9);
  border-radius: 4rpx;
}

.more-text {
  font-size: 26rpx;
  color: #3498db;
  font-weight: 500;
}

.company-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.company-card {
  background: linear-gradient(to right, #f8f9fa 0%, #ffffff 100%);
  border-radius: 15rpx;
  padding: 25rpx;
  border-left: 6rpx solid #3498db;
  box-shadow: 0 4rpx 10rpx rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.company-card:active {
  transform: translateY(-2rpx);
  box-shadow: 0 6rpx 15rpx rgba(0, 0, 0, 0.1);
}

.company-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10rpx;
}

.company-name {
  font-size: 28rpx;
  font-weight: bold;
  color: #2c3e50;
}

.company-type {
  font-size: 22rpx;
  color: #fff;
  background-color: #3498db;
  padding: 4rpx 12rpx;
  border-radius: 15rpx;
  font-weight: 500;
}

.business-scope {
  font-size: 24rpx;
  color: #7f8c8d;
  margin-bottom: 10rpx;
  line-height: 1.4;
}

.company-contact {
  display: flex;
  align-items: center;
  color: #e74c3c;
  font-weight: 500;
}

.phone-icon {
  font-size: 24rpx;
  margin-right: 5rpx;
}

.phone-number {
  font-size: 26rpx;
  text-decoration: underline;
}

.news-section {
  background-color: white;
  margin: 0 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.news-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.news-item {
  padding: 20rpx 0;
  border-bottom: 1rpx solid #ecf0f1;
  transition: background-color 0.3s;
}

.news-item:last-child {
  border-bottom: none;
}

.news-item:active {
  background-color: #f8f9fa;
}

.news-title {
  font-size: 28rpx;
  color: #2c3e50;
  line-height: 1.4;
  display: block;
  margin-bottom: 8rpx;
  font-weight: 500;
}

.news-time {
  font-size: 24rpx;
  color: #95a5a6;
}

.quick-actions {
  display: flex;
  gap: 30rpx;
  padding: 30rpx;
  margin-top: 20rpx;
  width: calc(100% - 60rpx);
}

.action-item {
  flex: 1;
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  border-radius: 15rpx;
  padding: 30rpx;
  text-align: center;
  box-shadow: 0 6rpx 20rpx rgba(52, 152, 219, 0.3);
  color: white;
  transition: all 0.3s ease;
}

.action-item:active {
  transform: translateY(-3rpx);
  box-shadow: 0 8rpx 25rpx rgba(52, 152, 219, 0.4);
}

.action-icon {
  font-size: 40rpx;
  display: block;
  margin-bottom: 10rpx;
}

.action-text {
  font-size: 28rpx;
  color: white;
  font-weight: 500;
}
</style>
<template>
  <view class="container">
    <!-- 顶部品牌区 -->
    <view class="brand-header">
      <image class="logo" src="/static/logo.png" mode="aspectFit"></image>
      <text class="brand-name">钢管之都 e管通</text>
      <text class="subtitle">关于我们</text>
    </view>
    
    <view class="content" v-if="aboutData">
      <view class="section">
        <text class="section-title">关于我们</text>
        <text class="description">
          {{ aboutData.aboutUs }}
        </text>
      </view>
      
      <view class="section">
        <text class="section-title">功能介绍</text>
        <view class="feature-list">
          <view class="feature-item" v-for="(feature, index) in aboutData.features" :key="index">
            • {{ feature }}
          </view>
        </view>
      </view>
      
      <view class="section">
        <text class="section-title">联系我们</text>
        <view class="contact-info">
          <view class="contact-item">
            <text class="label">电话:</text>
            <text class="value">{{ aboutData.contactInfo.phone }}</text>
          </view>
          <view class="contact-item">
            <text class="label">邮箱:</text>
            <text class="value">{{ aboutData.contactInfo.email }}</text>
          </view>
          <view class="contact-item">
            <text class="label">地址:</text>
            <text class="value">{{ aboutData.contactInfo.address }}</text>
          </view>
        </view>
      </view>
    </view>
    
    <!-- 加载提示 -->
    <view v-else class="loading-container">
      <text class="loading-text">加载中...</text>
    </view>
    
    <!-- 联系我们按钮 -->
    <view class="contact-button-section">
      <QuickActions />
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import ApiService from "@/api/api";
import QuickActions from "@/components/QuickActions.vue";

// 定义数据类型
interface AboutData {
  aboutUs: string;
  features: string[];
  contactInfo: {
    phone: string;
    email: string;
    address: string;
  };
}

const aboutData = ref<AboutData | null>(null)

// 获取关于页面数据
const fetchAboutData = async () => {
  try {
    const data = await ApiService.getAboutData()
    aboutData.value = data
  } catch (error) {
    console.error('获取关于页面数据失败:', error)
    uni.showToast({
      title: '数据加载失败',
      icon: 'none'
    })
  }
}

onMounted(() => {
  fetchAboutData()
})
</script>

<style scoped>
.container {
  background-color: #f5f5f5;
  min-height: 100vh;
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
  margin-bottom: 30rpx;
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

.content {
  padding: 0 30rpx;
}

.section {
  margin-bottom: 30rpx;
  padding: 30rpx;
  background-color: #ffffff;
  border-radius: 15rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 20rpx;
  display: block;
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

.description {
  font-size: 28rpx;
  color: #333;
  line-height: 1.6;
}

.feature-list {
  margin-left: 10rpx;
}

.feature-item {
  font-size: 28rpx;
  color: #333;
  margin-bottom: 15rpx;
  line-height: 1.5;
  position: relative;
  padding-left: 20rpx;
}

.feature-item::before {
  content: '•';
  position: absolute;
  left: 0;
  color: #3498db;
  font-weight: bold;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.contact-item {
  display: flex;
  padding: 15rpx 0;
  border-bottom: 1rpx solid #ecf0f1;
}

.contact-item:last-child {
  border-bottom: none;
}

.label {
  font-weight: bold;
  width: 100rpx;
  color: #2c3e50;
  font-size: 28rpx;
}

.value {
  flex: 1;
  color: #333;
  font-size: 28rpx;
}

.contact-button-section {
  padding: 0 30rpx;
}

.contact-button {
  width: 100%;
  padding: 25rpx;
  border: none;
  border-radius: 15rpx;
  font-size: 32rpx;
  font-weight: 500;
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  color: white;
  box-shadow: 0 6rpx 20rpx rgba(52, 152, 219, 0.3);
  transition: all 0.3s;
}

.contact-button:active {
  transform: translateY(-3rpx);
  box-shadow: 0 8rpx 25rpx rgba(52, 152, 219, 0.4);
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 200rpx 0;
}

.loading-text {
  font-size: 28rpx;
  color: #999;
}

.contact-button-section {
  padding: 0 30rpx;
  margin-top: 20rpx;
}

/* 调整QuickActions组件在关于页面的样式 */
.contact-button-section .quick-actions {
  padding: 20rpx 0 0;
  margin: 0;
}

.contact-button-section .action-item {
  padding: 25rpx 15rpx;
}
</style>
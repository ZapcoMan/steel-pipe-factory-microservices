<template>
  <view class="main-features">
    <view class="section-header">
      <text class="section-title">核心功能</text>
      <text class="section-subtitle">快速访问我们的主要服务</text>
    </view>
    <view class="feature-grid">
      <view class="feature-card" @click="goToTab('calculator')">
        <view class="card-icon">⚙️</view>
        <text class="card-title">重量计算</text>
        <text class="card-desc">各类钢材重量快速计算</text>
      </view>
      <view class="feature-card" @click="goToPage('price')">
        <view class="card-icon">📊</view>
        <text class="card-title">价格行情</text>
        <text class="card-desc">查看最新钢材价格</text>
      </view>
      <view class="feature-card" @click="goToPage('tech-data')">
        <view class="card-icon">📚</view>
        <text class="card-title">技术资料</text>
        <text class="card-desc">材质说明与对比</text>
      </view>
      <view class="feature-card" @click="goToPage('sample')">
        <view class="card-icon">🖼️</view>
        <text class="card-title">样品展示</text>
        <text class="card-desc">实物样品对比</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
// TabBar页面跳转
const goToTab = (type: string) => {
  uni.switchTab({
    url: `/pages/${type}/${type}`
  })
}

// 普通页面跳转
const goToPage = (type: string) => {
  uni.navigateTo({
    url: `/pages/${type}/${type}`
  })
}
</script>

<style scoped>
.main-features {
  padding: 30rpx;
}

.section-header {
  margin-bottom: 30rpx;
  text-align: center;
}

.section-title {
  font-size: 36rpx;
  font-weight: bold;
  color: #2c3e50;
  display: block;
  margin-bottom: 10rpx;
}

.section-subtitle {
  font-size: 26rpx;
  color: #7f8c8d;
}

.feature-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 25rpx;
}

.feature-card {
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  border-radius: 20rpx;
  padding: 35rpx 20rpx;
  color: #2c3e50;
  box-shadow: 0 6rpx 16rpx rgba(0, 0, 0, 0.08);
  position: relative;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1rpx solid #e0e0e0;
  text-align: center;
}

.feature-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4rpx;
  background: linear-gradient(90deg, #3498db, #2980b9);
}

.feature-card:active {
  transform: translateY(-5rpx);
  box-shadow: 0 10rpx 25rpx rgba(0, 0, 0, 0.15);
}

.card-icon {
  font-size: 48rpx;
  display: block;
  margin: 0 auto 15rpx;
}

.card-title {
  font-size: 30rpx;
  font-weight: bold;
  display: block;
  margin-bottom: 10rpx;
  color: #2c3e50;
}

.card-desc {
  font-size: 24rpx;
  color: #7f8c8d;
  line-height: 1.4;
}
</style>
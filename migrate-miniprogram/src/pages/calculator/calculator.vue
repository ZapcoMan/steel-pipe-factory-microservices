<template>
  <view class="container">
    <!-- 顶部品牌区 -->
    <view class="brand-header">
      <image class="logo" src="/static/logo.png" mode="aspectFit"/>
      <text class="brand-name">钢管之都 e管通</text>
      <text class="subtitle">金属重量计算器</text>
    </view>

    <!-- 钢种选择网格 -->
    <view class="grid-container">
      <view class="grid-item" @click="goToPage('steel')">
        <view class="icon">钢</view>
        <text class="label">钢管</text>
      </view>
      <view class="grid-item" @click="goToPage('rectangular')">
        <view class="icon">曰</view>
        <text class="label">方矩管</text>
      </view>
      <view class="grid-item" @click="goToPage('i-beam')">
        <view class="icon">工</view>
        <text class="label">工字钢</text>
      </view>
      <view class="grid-item" @click="goToPage('angle')">
        <view class="icon">L</view>
        <text class="label">角钢</text>
      </view>
      <view class="grid-item" @click="goToPage('channel')">
        <view class="icon">山</view>
        <text class="label">槽钢</text>
      </view>
      <view class="grid-item" @click="goToPage('aluminum')">
        <view class="icon">铝</view>
        <text class="label">铝管</text>
      </view>
      <view class="grid-item" @click="goToPage('h-beam')">
        <view class="icon">H</view>
        <text class="label">H型钢</text>
      </view>
      <view class="grid-item" @click="goToPage('c-beam')">
        <view class="icon">C</view>
        <text class="label">C型钢</text>
      </view>
      <view class="grid-item" @click="goToPage('brass')">
        <view class="icon">黄</view>
        <text class="label">黄铜管</text>
      </view>
      <view class="grid-item" @click="goToPage('stainless')">
        <view class="icon">锈</view>
        <text class="label">不锈钢管</text>
      </view>
      <view class="grid-item" @click="goToPage('hexagonal')">
        <view class="icon">六</view>
        <text class="label">六角钢</text>
      </view>
      <view class="grid-item" @click="goToPage('purple-copper')">
        <view class="icon">紫</view>
        <text class="label">紫铜管</text>
      </view>
      <view class="grid-item" @click="goToPage('flat')">
        <view class="icon">□</view>
        <text class="label">方扁钢</text>
      </view>
      <view class="grid-item" @click="goToPage('round')">
        <view class="icon">●</view>
        <text class="label">盘圆钢筋</text>
      </view>
      <view class="grid-item" @click="goToPage('octagonal')">
        <view class="icon">○</view>
        <text class="label">八角钢</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
// 跳转到对应计算页面
const goToPage = (type: string) => {
  uni.navigateTo({
    url: `/pages/calculator/detail?type=${type}`
  })
}
</script>

<style scoped>
.container {
  background-color: #f5f5f5;
  color: #000000;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
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

.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20rpx;
  padding: 0 30rpx 30rpx;
  flex: 1;
}

.grid-item {
  border-radius: 15rpx;
  padding: 30rpx 15rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  color: #2c3e50;
  cursor: pointer;
  box-shadow: 0 4rpx 10rpx rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  border: 1rpx solid #e0e0e0;
}

.grid-item:active {
  transform: translateY(-3rpx);
  box-shadow: 0 6rpx 15rpx rgba(0, 0, 0, 0.15);
}

.grid-item .icon {
  font-size: 40rpx;
  margin-bottom: 12rpx;
  font-weight: bold;
}

.grid-item .label {
  font-size: 24rpx;
  text-align: center;
  font-weight: 500;
  color: #2c3e50;
}
</style>
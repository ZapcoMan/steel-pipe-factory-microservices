<template>
  <view class="brand-header">
    <image class="logo" src="/static/logo.png" mode="aspectFit"/>
    <text class="brand-name">钢管之都 e管通</text>
    <text class="subtitle">专业钢材解决方案提供商</text>
  </view>
</template>

<script setup lang="ts">
</script>

<style scoped>
.brand-header {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  padding: 60rpx 30rpx 40rpx;
  text-align: center;
  color: white;
  position: relative;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.15);
}

.logo {
  width: 220rpx;
  height: 220rpx;
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
</style>
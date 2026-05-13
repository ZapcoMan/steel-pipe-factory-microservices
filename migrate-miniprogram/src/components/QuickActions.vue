<template>
  <view class="quick-actions">
    <view class="action-item" @click="makePhoneCall('400-123-4567')">
      <text class="action-icon">📞</text>
      <text class="action-text">联系我们</text>
    </view>
  </view>
</template>

<script setup lang="ts">
// 拨打电话功能
const makePhoneCall = (phoneNumber: string) => {
  uni.makePhoneCall({
    phoneNumber: phoneNumber,
    fail: () => {
      uni.showToast({
        title: '无法拨打电话',
        icon: 'none'
      })
    }
  })
}
</script>

<style scoped>
.quick-actions {
  display: flex;
  gap: 25rpx;
  padding: 30rpx;
  margin: 0 0 30rpx;
  width: 100%;
}

.action-item {
  flex: 1;
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  border-radius: 15rpx;
  padding: 30rpx 15rpx;
  text-align: center;
  box-shadow: 0 6rpx 20rpx rgba(52, 152, 219, 0.3);
  color: white;
  transition: all 0.3s ease;
}

.action-item:active {
  transform: translateY(-5rpx);
  box-shadow: 0 8rpx 25rpx rgba(52, 152, 219, 0.4);
}

.action-icon {
  font-size: 40rpx;
  display: block;
  margin: 0 auto 10rpx;
}

.action-text {
  font-size: 26rpx;
  color: white;
  font-weight: 500;
}
</style>
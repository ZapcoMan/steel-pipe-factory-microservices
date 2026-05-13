<template>
  <view class="featured-companies">
    <view class="section-header">
      <text class="section-title">合作企业</text>
      <text class="more-text" @click="goToPage('ads')">{{ moreText }}</text>
    </view>

    <view class="company-list">
      <view
          v-for="(company, index) in featuredCompanies"
          :key="index"
          class="company-card"
          @click="callCompany(company.phone)"
      >
        <view class="company-header">
          <text class="company-name">{{ company.name }}</text>
          <text class="company-type">{{ company.type }}</text>
        </view>
        <view class="business-scope" :title="stripHtml(company.scope)">{{ truncateText(stripHtml(company.scope), 160) }}</view>
        <view class="company-contact">
          <text class="phone-icon">📞</text>
          <text class="phone-number">{{ company.phone }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import homeApi from '@/api/homeApi';

const moreText = ref('更多 >');

// 精选合作企业数据
const featuredCompanies = ref([]);

/**
 * 获取精选合作企业数据列表
 */
const loadFeaturedCompanies = async () => {
  try {
    featuredCompanies.value = await homeApi.getfeaturedCompaniesDataList();
  } catch (error) {
    console.error('获取合作企业数据失败:', error);
  }
};

// 页面加载时获取数据
onMounted(() => {
  loadFeaturedCompanies();
});

// 公司联系电话
const callCompany = (phoneNumber: string) => {
  uni.makePhoneCall({
    phoneNumber: phoneNumber,
    fail: () => {
      uni.showToast({
        title: '无法拨打电话',
        icon: 'none'
      })
    }
  })
};

// 过滤 HTML 标签，只保留纯文本
const stripHtml = (html: string): string => {
  if (!html) return '';
  // 移除所有 HTML 标签
  return html.replace(/<[^>]*>/g, '');
};

// 截断文本，超出长度显示省略号
const truncateText = (text: string, maxLength: number): string => {
  if (!text) return '';
  if (text.length <= maxLength) return text;
  return text.substring(0, maxLength) + '...';
};

// 普通页面跳转（非TabBar页面）
const goToPage = (type: string) => {
  uni.switchTab({
    url: `/pages/${type}/${type}`
  })
};
</script>

<style scoped>
.featured-companies {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
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
  gap: 25rpx;
}

.company-card {
  background: linear-gradient(to right, #f8f9fa 0%, #ffffff 100%);
  border-radius: 15rpx;
  padding: 30rpx;
  border-left: 6rpx solid #3498db;
  box-shadow: 0 4rpx 10rpx rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  border: 1rpx solid #e9ecef;
}

.company-card:active {
  transform: translateY(-3rpx);
  box-shadow: 0 6rpx 15rpx rgba(0, 0, 0, 0.1);
}

.company-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15rpx;
}

.company-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #2c3e50;
}

.company-type {
  font-size: 24rpx;
  color: #fff;
  background-color: #3498db;
  padding: 6rpx 15rpx;
  border-radius: 20rpx;
  font-weight: 500;
}

.business-scope {
  font-size: 26rpx;
  color: #34495e;
  margin-bottom: 15rpx;
  line-height: 1.5;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  white-space: normal;
}

.company-contact {
  display: flex;
  align-items: center;
  color: #e74c3c;
  font-weight: 500;
}

.phone-icon {
  font-size: 26rpx;
  margin-right: 10rpx;
}

.phone-number {
  font-size: 28rpx;
  text-decoration: underline;
}
</style>
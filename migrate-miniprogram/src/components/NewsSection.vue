<template>
  <view class="news-section">
    <view class="section-header">
      <text class="section-title">行业资讯</text>
      <text class="more-text" @click="goToPage('news')">{{ moreText }}</text>
    </view>

    <view class="news-list">
      <view
          v-for="(item, index) in newsList"
          :key="index"
          class="news-item"
          @click="viewNewsDetail(item.id)"
      >
        <view class="news-content">
          <text class="news-title">{{ item.title }}</text>
          <view class="news-meta">
            <text class="news-time">{{ item.time }}</text>
          </view>
        </view>
        <text class="news-arrow">›</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import homeApi from '@/api/homeApi';

const moreText = ref('更多 >');

// 资讯数据
const newsList = ref([]);

/**
 * 获取资讯信息数据列表
 */
const loadNewsList = async () => {
  try {
    newsList.value = await homeApi.getObtainInformationDataList();
  } catch (error) {
    console.error('获取资讯数据失败:', error);
  }
};

// 页面加载时获取数据
onMounted(() => {
  loadNewsList();
});

// 普通页面跳转（非TabBar页面）
const goToPage = (type: string) => {
  uni.switchTab({
    url: `/pages/${type}/${type}`
  })
};

const viewNewsDetail = (id: number) => {
  uni.navigateTo({
    url: `/pages/news-detail/news-detail?id=${id}`
  })
};
</script>

<style scoped>
.news-section {
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

.news-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
}

.news-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 25rpx 0;
  border-bottom: 1rpx solid #ecf0f1;
  transition: background-color 0.3s;
}

.news-item:last-child {
  border-bottom: none;
}

.news-item:active {
  background-color: #f8f9fa;
}

.news-content {
  flex: 1;
}

.news-title {
  font-size: 28rpx;
  color: #2c3e50;
  line-height: 1.5;
  display: block;
  margin-bottom: 10rpx;
  font-weight: 500;
}

.news-meta {
  display: flex;
  align-items: center;
  gap: 20rpx;
}

.news-time {
  font-size: 24rpx;
  color: #95a5a6;
}

.news-arrow {
  font-size: 32rpx;
  color: #bdc3c7;
  margin-left: 20rpx;
}
</style>
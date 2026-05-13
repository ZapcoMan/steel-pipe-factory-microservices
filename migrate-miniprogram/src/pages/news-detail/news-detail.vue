<template>
  <view class="news-detail-page">
    <!-- 文章标题区域 -->
    <view class="article-header">
      <text class="article-title">{{ newsDetail.title }}</text>
      <view class="article-meta">
        <text class="meta-item">📅 {{ newsDetail.time }}</text>
        <text class="meta-item" v-if="newsDetail.source">📰 {{ newsDetail.source }}</text>
      </view>
    </view>

    <!-- 文章内容区域 -->
    <scroll-view scroll-y class="article-content">
      <rich-text :nodes="formattedContent" class="rich-text-content"></rich-text>
    </scroll-view>


  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
import homeApi from '@/api/homeApi';

// 从路由参数获取新闻 ID
const newsId = ref<number>(0);

// 新闻详情数据
const newsDetail = ref<any>({
  id: 0,
  title: '',
  time: '',
  source: '',
  content: ''
});

// 格式化后的内容
const formattedContent = computed(() => {
  return newsDetail.value.content || '<p>暂无内容</p>';
});

// 加载新闻详情
const loadNewsDetail = async (id: number) => {
  uni.showLoading({ title: '加载中...' });
  
  try {
    const res = await homeApi.getIndustryNewsDetailData(id);
    // 假设后台返回格式为 { code: 20000, data: {...}, message: '' }
    if (res && res.code === 20000) {
      newsDetail.value = res.data;
    } else {
      throw new Error('数据格式错误');
    }
  } catch (error) {
    console.error('加载新闻详情失败:', error);
    uni.showToast({
      title: '加载失败，请稍后重试',
      icon: 'none',
      duration: 2000
    });
    // 延迟返回上一页
    setTimeout(() => {
      uni.navigateBack();
    }, 2000);
  } finally {
    uni.hideLoading();
  }
};



// 页面加载时获取数据
onLoad((options) => {
  if (options && options.id) {
    newsId.value = parseInt(options.id);
    loadNewsDetail(newsId.value);
  } else {
    uni.showToast({
      title: '新闻 ID 不存在',
      icon: 'none'
    });
    setTimeout(() => {
      uni.navigateBack();
    }, 1500);
  }
});

// 设置页面分享
onMounted(() => {
  // 可以设置默认的分享内容
});
</script>

<style scoped>
.news-detail-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f5f5;
  overflow-x: hidden;
}

/* 文章标题区域 */
.article-header {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  padding: 40rpx 30rpx;
  color: white;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.15);
  flex-shrink: 0;
}

.article-title {
  font-size: 36rpx;
  font-weight: bold;
  line-height: 1.5;
  display: block;
  margin-bottom: 20rpx;
  text-shadow: 2rpx 2rpx 4rpx rgba(0, 0, 0, 0.3);
  word-break: break-all;
}

.article-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 20rpx;
}

.meta-item {
  font-size: 24rpx;
  opacity: 0.9;
  color: #ecf0f1;
  white-space: nowrap;
}

/* 文章内容区域 */
.article-content {
  flex: 1;
  background-color: white;
  padding: 30rpx;
  overflow-y: auto;
  overflow-x: hidden;
  box-sizing: border-box;
  width: 100%;
}

.rich-text-content {
  font-size: 30rpx;
  line-height: 1.8;
  color: #2c3e50;
  width: 100%;
  max-width: 100%;
  overflow-wrap: break-word;
  word-break: break-all;
}

/* Rich Text 样式定制 */
:deep(.rich-text-content p) {
  margin-bottom: 20rpx;
  line-height: 1.8;
  max-width: 100%;
  overflow-wrap: break-word;
}

:deep(.rich-text-content h3) {
  font-size: 32rpx;
  font-weight: bold;
  color: #2c3e50;
  margin-top: 40rpx;
  margin-bottom: 20rpx;
  padding-left: 15rpx;
  border-left: 4rpx solid #3498db;
  max-width: 100%;
  overflow-wrap: break-word;
}

:deep(.rich-text-content strong) {
  font-weight: bold;
  color: #e74c3c;
}

:deep(.rich-text-content ul),
:deep(.rich-text-content ol) {
  margin: 20rpx 0;
  padding-left: 40rpx;
  max-width: 100%;
}

:deep(.rich-text-content li) {
  margin-bottom: 10rpx;
  max-width: 100%;
  overflow-wrap: break-word;
}

:deep(.rich-text-content img) {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 20rpx 0;
}

:deep(.rich-text-content table) {
  width: 100%;
  border-collapse: collapse;
  margin: 20rpx 0;
  font-size: 28rpx;
}

:deep(.rich-text-content th),
:deep(.rich-text-content td) {
  border: 1rpx solid #ddd;
  padding: 10rpx;
  text-align: left;
  max-width: 100%;
  overflow-wrap: break-word;
}

/* 底部操作栏 */
.bottom-bar {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: white;
  padding: 20rpx 30rpx;
  border-top: 1rpx solid #e0e0e0;
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);
  flex-shrink: 0;
}

.action-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5rpx;
  padding: 10rpx 20rpx;
  transition: all 0.3s;
}

.action-btn:active {
  opacity: 0.7;
  transform: scale(0.95);
}

.btn-icon {
  font-size: 32rpx;
}

.btn-text {
  font-size: 22rpx;
  color: #7f8c8d;
  white-space: nowrap;
}
</style>

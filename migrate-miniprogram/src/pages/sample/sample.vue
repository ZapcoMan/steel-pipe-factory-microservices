<template>
  <view class="container">
    <!-- 顶部品牌区 -->
    <view class="brand-header">
      <image class="logo" src="/static/logo.png" mode="aspectFit"/>
      <text class="brand-name">钢管之都 e管通</text>
      <text class="subtitle">样品展示</text>
    </view>

    <!-- 样品分类 -->
    <view class="category-section">
      <view class="section-header">
        <text class="section-title">样品分类</text>
      </view>
      <view class="category-list">
        <view 
          class="category-item" 
          :class="{ active: selectedCategory === category.id }"
          v-for="(category, index) in categories" 
          :key="index"
          @click="selectCategory(category.id)"
        >
          <text class="category-name">{{ category.name }}</text>
        </view>
      </view>
    </view>

    <!-- 样品展示 -->
    <view class="sample-section">
      <view class="section-header">
        <text class="section-title">样品展示</text>
      </view>
      <view class="sample-grid">
        <view 
          class="sample-item" 
          v-for="(sample, index) in filteredSamples" 
          :key="index"
          @click="viewSampleDetail(sample)"
        >
          <view class="sample-image-container">
            <image 
              :src="sample.image" 
              mode="aspectFill" 
              class="sample-image"
              @error="onImageError"
            />
            <view class="sample-badge" v-if="sample.isNew">新品</view>
          </view>
          <view class="sample-info">
            <text class="sample-name">{{ sample.name }}</text>
            <text class="sample-spec">{{ sample.spec }}</text>
            <view class="sample-features">
              <text class="sample-feature" v-for="(feature, idx) in sample.features" :key="idx">
                {{ feature }}
              </text>
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 规格对比 -->
    <view class="comparison-section">
      <view class="section-header">
        <text class="section-title">规格对比</text>
      </view>
      <view class="comparison-table">
        <view class="table-header">
          <view class="table-cell">名称</view>
          <view class="table-cell">规格</view>
          <view class="table-cell">材质</view>
          <view class="table-cell">特性</view>
        </view>
        <view class="table-row" v-for="(sample, index) in comparisonSamples" :key="index">
          <view class="table-cell">{{ sample.name }}</view>
          <view class="table-cell">{{ sample.spec }}</view>
          <view class="table-cell">{{ sample.material }}</view>
          <view class="table-cell">{{ sample.features.join(', ') }}</view>
        </view>
      </view>
    </view>

    <!-- 样品详情弹窗 -->
    <view class="modal" v-if="showDetailModal" @click="closeModal">
      <view class="modal-content" @click.stop="() => {}">
        <view class="modal-header">
          <text class="modal-title">{{ selectedSample.name }}</text>
          <text class="close-btn" @click="closeModal">✕</text>
        </view>
        <view class="modal-body">
          <image :src="selectedSample.image" mode="widthFix" class="modal-image"/>
          <view class="modal-info">
            <view class="info-row">
              <text class="info-label">产品名称：</text>
              <text class="info-value">{{ selectedSample.name }}</text>
            </view>
            <view class="info-row">
              <text class="info-label">规格：</text>
              <text class="info-value">{{ selectedSample.spec }}</text>
            </view>
            <view class="info-row">
              <text class="info-label">材质：</text>
              <text class="info-value">{{ selectedSample.material }}</text>
            </view>
            <view class="info-row">
              <text class="info-label">特点：</text>
              <view class="info-value">
                <text class="feature-tag" v-for="(feature, idx) in selectedSample.features" :key="idx">
                  {{ feature }}
                </text>
              </view>
            </view>
            <view class="info-row">
              <text class="info-label">应用领域：</text>
              <text class="info-value">{{ selectedSample.application }}</text>
            </view>
          </view>
        </view>
        <view class="modal-footer">
          <button @click="contactForSample" class="contact-btn">咨询定制</button>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import sampleApi from '@/api/sampleApi';

// 样品分类
const categories = ref([
  { id: 'all', name: '全部' },
  { id: 'steel-tube', name: '钢管' },
  { id: 'rectangular', name: '方矩管' },
  { id: 'i-beam', name: '工字钢' },
  { id: 'angle', name: '角钢' },
  { id: 'channel', name: '槽钢' },
  { id: 'h-beam', name: 'H型钢' }
]);

// 当前选中的分类
const selectedCategory = ref('all');

// 样品数据
const samples = ref([]);

// 选中的样品
const selectedSample = ref({});
const showDetailModal = ref(false);

// 根据分类筛选样品
const filteredSamples = computed(() => {
  if (selectedCategory.value === 'all') {
    return samples.value;
  }
  return samples.value.filter((sample: any) => sample.category === selectedCategory.value);
});

// 用于对比表的样品（取前几个）
const comparisonSamples = computed(() => {
  return samples.value.slice(0, 5);
});

const selectCategory = (categoryId: string) => {
  selectedCategory.value = categoryId;
};

const viewSampleDetail = (sample: any) => {
  selectedSample.value = sample;
  showDetailModal.value = true;
};

const closeModal = () => {
  showDetailModal.value = false;
};

const contactForSample = () => {
  uni.makePhoneCall({
    phoneNumber: '400-123-4567',
    fail: () => {
      uni.showToast({
        title: '无法拨打电话',
        icon: 'none'
      });
    }
  });
};

const onImageError = (e: any) => {
  console.error('图片加载失败:', e);
};

onMounted(() => {
  // 加载样品数据
  loadSampleData();
});

const loadSampleData = async () => {
  try {
    const data = await sampleApi.getSampleData();
    samples.value = data;
  } catch (error) {
    console.error('获取样品数据失败:', error);
    uni.showToast({
      title: '获取样品数据失败',
      icon: 'none'
    });
  }
};
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

.category-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.category-list {
  display: flex;
  flex-wrap: wrap;
  gap: 15rpx;
}

.category-item {
  padding: 12rpx 24rpx;
  background-color: #ecf0f1;
  border-radius: 30rpx;
  font-size: 24rpx;
  color: #7f8c8d;
  transition: all 0.3s;
}

.category-item.active {
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  color: white;
  box-shadow: 0 4rpx 10rpx rgba(52, 152, 219, 0.3);
}

.category-name {
  font-size: 24rpx;
  color: #2c3e50;
}

.sample-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.sample-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20rpx;
}

.sample-item {
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  border-radius: 15rpx;
  overflow: hidden;
  box-shadow: 0 4rpx 10rpx rgba(0, 0, 0, 0.05);
  transition: all 0.3s;
  border: 1rpx solid #e0e0e0;
}

.sample-item:active {
  transform: translateY(-3rpx);
  box-shadow: 0 6rpx 15rpx rgba(0, 0, 0, 0.1);
}

.sample-image-container {
  position: relative;
  height: 180rpx;
  overflow: hidden;
}

.sample-image {
  width: 100%;
  height: 100%;
}

.sample-badge {
  position: absolute;
  top: 10rpx;
  right: 10rpx;
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
  color: white;
  font-size: 20rpx;
  padding: 6rpx 12rpx;
  border-radius: 20rpx;
  z-index: 2;
}

.sample-info {
  padding: 20rpx;
}

.sample-name {
  font-size: 28rpx;
  font-weight: 500;
  color: #2c3e50;
  display: block;
  margin-bottom: 8rpx;
}

.sample-spec {
  font-size: 24rpx;
  color: #7f8c8d;
  display: block;
  margin-bottom: 12rpx;
}

.sample-features {
  display: flex;
  flex-wrap: wrap;
  gap: 8rpx;
}

.sample-feature {
  font-size: 20rpx;
  color: #3498db;
  background-color: #e8f4fd;
  padding: 4rpx 10rpx;
  border-radius: 15rpx;
}

.comparison-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.comparison-table {
  width: 100%;
  border-collapse: collapse;
}

.table-header {
  display: flex;
  background-color: #3498db;
  color: white;
  font-weight: bold;
  border-radius: 10rpx 10rpx 0 0;
  overflow: hidden;
}

.table-row {
  display: flex;
  border-bottom: 1rpx solid #ecf0f1;
}

.table-cell {
  flex: 1;
  padding: 20rpx 10rpx;
  text-align: center;
  font-size: 24rpx;
  color: #2c3e50;
  min-height: 80rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.table-header .table-cell {
  color: white;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 30rpx;
  box-sizing: border-box;
}

.modal-content {
  background-color: white;
  border-radius: 20rpx;
  width: 100%;
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 20rpx 40rpx rgba(0, 0, 0, 0.3);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30rpx;
  border-bottom: 1rpx solid #ecf0f1;
}

.modal-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #2c3e50;
}

.close-btn {
  font-size: 36rpx;
  color: #95a5a6;
  cursor: pointer;
}

.modal-body {
  padding: 30rpx;
}

.modal-image {
  width: 100%;
  border-radius: 10rpx;
  margin-bottom: 20rpx;
}

.modal-info {
  margin-top: 20rpx;
}

.info-row {
  display: flex;
  margin-bottom: 15rpx;
  flex-wrap: wrap;
}

.info-label {
  font-weight: bold;
  color: #2c3e50;
  width: 120rpx;
  font-size: 26rpx;
}

.info-value {
  flex: 1;
  color: #333;
  font-size: 26rpx;
  word-break: break-all;
}

.feature-tag {
  display: inline-block;
  background-color: #e8f4fd;
  color: #3498db;
  padding: 4rpx 10rpx;
  border-radius: 15rpx;
  font-size: 22rpx;
  margin-right: 8rpx;
  margin-bottom: 4rpx;
}

.modal-footer {
  padding: 30rpx;
  border-top: 1rpx solid #ecf0f1;
  text-align: center;
}

.contact-btn {
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

.contact-btn:active {
  transform: translateY(-3rpx);
  box-shadow: 0 8rpx 25rpx rgba(52, 152, 219, 0.4);
}
</style>
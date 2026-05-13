<template>
  <view class="container">
    <!-- 顶部品牌区 -->
    <view class="brand-header">
      <image class="logo" src="/static/logo.png" mode="aspectFit"/>
      <text class="brand-name">钢管之都 e管通</text>
      <text class="subtitle">技术资料库</text>
    </view>

    <!-- 技术资料分类 -->
    <view class="category-section">
      <view class="section-header">
        <text class="section-title">资料分类</text>
      </view>
      <view class="category-list">
        <view class="category-item" @click="selectCategory('material')">
          <view class="category-icon">🔬</view>
          <text class="category-name">材质说明</text>
        </view>
        <view class="category-item" @click="selectCategory('property')">
          <view class="category-icon">📊</view>
          <text class="category-name">特性对比</text>
        </view>
        <view class="category-item" @click="selectCategory('application')">
          <view class="category-icon">🏭</view>
          <text class="category-name">使用场景</text>
        </view>
      </view>
    </view>

    <!-- 材质说明内容 -->
    <view class="content-section" v-if="currentCategory === 'material'">
      <view class="section-header">
        <text class="section-title">钢材材质说明</text>
      </view>
      <view class="material-list">
        <view class="material-item" v-for="(item, index) in materialData" :key="index">
          <view class="material-header">
            <text class="material-name">{{ item.name }}</text>
            <text class="material-grade">{{ item.grade }}</text>
          </view>
          <view class="material-desc">{{ item.description }}</view>
          <view class="material-features">
            <view class="feature" v-for="(feature, idx) in item.features" :key="idx">
              <text class="feature-text">• {{ feature }}</text>
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 特性对比内容 -->
    <view class="content-section" v-if="currentCategory === 'property'">
      <view class="section-header">
        <text class="section-title">钢材特性对比</text>
      </view>
      <view class="comparison-table">
        <view class="table-header">
          <view class="table-cell">材质</view>
          <view class="table-cell">强度</view>
          <view class="table-cell">韧性</view>
          <view class="table-cell">耐腐蚀性</view>
          <view class="table-cell">焊接性</view>
        </view>
        <view class="table-row" v-for="(item, index) in comparisonData" :key="index">
          <view class="table-cell">{{ item.name }}</view>
          <view class="table-cell">{{ item.strength }}</view>
          <view class="table-cell">{{ item.toughness }}</view>
          <view class="table-cell">{{ item.corrosion }}</view>
          <view class="table-cell">{{ item.welding }}</view>
        </view>
      </view>
    </view>

    <!-- 使用场景内容 -->
    <view class="content-section" v-if="currentCategory === 'application'">
      <view class="section-header">
        <text class="section-title">使用场景建议</text>
      </view>
      <view class="application-list">
        <view class="application-item" v-for="(item, index) in applicationData" :key="index">
          <view class="application-header">
            <text class="application-name">{{ item.name }}</text>
          </view>
          <view class="application-desc">{{ item.description }}</view>
          <view class="application-scenarios">
            <text class="scenario-title">适用场景：</text>
            <view class="scenario-list">
              <view class="scenario" v-for="(scenario, idx) in item.scenarios" :key="idx">
                <text class="scenario-text">• {{ scenario }}</text>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 搜索功能 -->
    <view class="search-section">
      <view class="section-header">
        <text class="section-title">搜索技术资料</text>
      </view>
      <view class="search-form">
        <input type="text" v-model="searchQuery" placeholder="输入材质、特性或应用场景关键词" class="search-input"/>
        <button @click="searchTechData" class="search-btn">搜索</button>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import techDataApi from '@/api/techDataApi';

// 当前选中的分类
const currentCategory = ref('material');

// 技术资料数据
const materialData = ref([]);
const comparisonData = ref([]);
const applicationData = ref([]);

// 搜索关键词
const searchQuery = ref('');

const selectCategory = (category: string) => {
  currentCategory.value = category;
};

const searchTechData = () => {
  if (searchQuery.value.trim() === '') {
    uni.showToast({
      title: '请输入搜索关键词',
      icon: 'none'
    });
    return;
  }
  uni.showToast({
    title: `搜索功能待实现: ${searchQuery.value}`,
    icon: 'none'
  });
};

onMounted(() => {
  // 加载技术资料数据
  loadTechData();
});

const loadTechData = async () => {
  try {
    const data = await techDataApi.getTechData();
    materialData.value = data.materials;
    comparisonData.value = data.comparison;
    applicationData.value = data.applications;
  } catch (error) {
    console.error('获取技术资料数据失败:', error);
    uni.showToast({
      title: '获取技术资料数据失败',
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
  justify-content: space-between;
}

.category-item {
  flex: 1;
  text-align: center;
  padding: 20rpx 10rpx;
  margin: 0 10rpx;
  background: linear-gradient(135deg, #f8f9fa 0%, #ffffff 100%);
  border-radius: 12rpx;
  box-shadow: 0 4rpx 10rpx rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: all 0.3s;
  border: 1rpx solid #e0e0e0;
}

.category-item:active {
  transform: translateY(-3rpx);
  box-shadow: 0 6rpx 15rpx rgba(0, 0, 0, 0.1);
}

.category-icon {
  font-size: 48rpx;
  display: block;
  margin-bottom: 10rpx;
}

.category-name {
  font-size: 26rpx;
  color: #2c3e50;
  font-weight: 500;
}

.content-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.material-item {
  padding: 25rpx 0;
  border-bottom: 1rpx solid #ecf0f1;
}

.material-item:last-child {
  border-bottom: none;
}

.material-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10rpx;
}

.material-name {
  font-size: 30rpx;
  font-weight: 500;
  color: #2c3e50;
}

.material-grade {
  font-size: 24rpx;
  color: #3498db;
  background-color: #e8f4fd;
  padding: 4rpx 12rpx;
  border-radius: 15rpx;
}

.material-desc {
  font-size: 26rpx;
  color: #333;
  line-height: 1.5;
  margin-bottom: 15rpx;
}

.material-features {
  margin-left: 10rpx;
}

.feature {
  margin-bottom: 8rpx;
}

.feature-text {
  font-size: 24rpx;
  color: #7f8c8d;
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

.application-item {
  padding: 25rpx 0;
  border-bottom: 1rpx solid #ecf0f1;
}

.application-item:last-child {
  border-bottom: none;
}

.application-header {
  margin-bottom: 10rpx;
}

.application-name {
  font-size: 30rpx;
  font-weight: 500;
  color: #2c3e50;
}

.application-desc {
  font-size: 26rpx;
  color: #333;
  line-height: 1.5;
  margin-bottom: 15rpx;
}

.scenario-title {
  font-size: 26rpx;
  color: #2c3e50;
  font-weight: 500;
  display: block;
  margin-bottom: 10rpx;
}

.scenario-list {
  margin-left: 10rpx;
}

.scenario {
  margin-bottom: 6rpx;
}

.scenario-text {
  font-size: 24rpx;
  color: #7f8c8d;
}

.search-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.search-input {
  width: 100%;
  padding: 20rpx;
  border: 2rpx solid #3498db;
  border-radius: 12rpx;
  font-size: 28rpx;
  box-sizing: border-box;
  background-color: #ffffff;
  color: #333;
  margin-bottom: 20rpx;
}

.search-btn {
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

.search-btn:active {
  transform: translateY(-3rpx);
  box-shadow: 0 8rpx 25rpx rgba(52, 152, 219, 0.4);
}
</style>
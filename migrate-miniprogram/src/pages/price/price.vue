<template>
  <view class="container">
    <!-- 顶部品牌区 -->
    <view class="brand-header">
      <image class="logo" src="/static/logo.png" mode="aspectFit"/>
      <text class="brand-name">钢管之都 e管通</text>
      <text class="subtitle">钢材价格行情</text>
    </view>

    <!-- 价格趋势图表 -->
    <view class="chart-section">
      <view class="section-header">
        <text class="section-title">价格趋势图表</text>
      </view>
      <view class="chart-container">
        <view class="simple-chart" v-if="priceList.length > 0">
          <view class="chart-axis-y">
            <text class="axis-label">价格 (元/吨)</text>
          </view>
          <view class="chart-bars">
            <view class="chart-item" v-for="(item, index) in priceList" :key="index">
              <view class="bar-container">
                <view
                    class="bar"
                    :style="{ height: calculateBarHeight(item.price) + 'rpx' }"
                    @click="onBarClick(item, index)"
                    :class="{ 'bar-active': activeBarIndex === index }">
                  <text class="bar-value">{{ item.price }}</text>
                </view>
              </view>
              <text class="bar-label">{{ item.name.substring(0, 4) }}</text>
            </view>
          </view>
        </view>
        <view class="chart-placeholder" v-else>
          <text class="chart-text">价格趋势图表</text>
          <text class="chart-desc">暂无数据</text>
        </view>
      </view>
    </view>

    <!-- 当前钢材价格列表 -->
    <view class="price-list-section">
      <view class="section-header">
        <text class="section-title">当前钢材价格</text>
      </view>
      <view class="price-list">
        <view class="price-item" v-for="(item, index) in priceList" :key="index">
          <view class="price-header">
            <text class="price-name">{{ item.name }}</text>
            <text class="price-value">¥{{ item.price }}/吨</text>
          </view>
          <view class="price-info">
            <text class="price-change" :class="{'positive': item.change >= 0, 'negative': item.change < 0}">
              {{ item.change >= 0 ? '↑' : '↓' }}{{ Math.abs(item.change) }}%
            </text>
            <text class="price-date">{{ item.date }}</text>
          </view>
          <view class="price-spec">{{ item.spec }}</view>
        </view>
      </view>
    </view>


  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import steelPriceApi from '@/api/steelPriceApi';

// uni-app生命周期钩子
import { onUnload, onShow } from '@dcloudio/uni-app';

// 定义钢材价格数据类型
interface SteelPrice {
  name: string;
  spec: string;
  price: number;
  change: number;
  date: string;
}

// 当前钢材价格列表
const priceList = ref<SteelPrice[]>([]);

// 钢材类型选择
const steelTypes = ref(['螺纹钢', '线材', '热轧板卷', '冷轧板卷', '中厚板', '钢管']);
const selectedType = ref(0);
const specification = ref('');
const activeBarIndex = ref(-1);

const onTypeChange = (e: any) => {
  selectedType.value = e.detail.value;
};

const queryPrice = () => {
  uni.showToast({
    title: '查询功能待实现',
    icon: 'none'
  });
};

onMounted(() => {
  // 加载价格数据
  loadPriceData();
});

const loadPriceData = async () => {
  try {
    const data = await steelPriceApi.getSteelPriceData();
    // API内部已经处理了错误情况并返回模拟数据，所以这里总是有数据
    priceList.value = data;
    console.log('图表：价格数据:', data); // 调试信息
  } catch (error) {
    console.error('获取价格数据失败:', error);
    // API内部已经处理了错误情况，这里应该不会到达
    // 但如果发生异常，仍然显示提示信息
    uni.showToast({
      title: '获取价格数据失败',
      icon: 'none'
    });
  }
};

// 页面卸载时清理
onUnload(() => {
  // 无需特殊清理
});

// 页面显示时重新渲染图表
onShow(() => {
  // 数据已保存在priceList中，无需重新初始化
});

// 图表触摸事件
const touchStart = (e: any) => {
  // 使用简单图表，无需特殊处理
};

const touchMove = (e: any) => {
  // 使用简单图表，无需特殊处理
};

const touchEnd = (e: any) => {
  // 使用简单图表，无需特殊处理
};

// 计算柱状图高度（固定像素值，避免百分比嵌套问题）
const calculateBarHeight = (price: number) => {
  if (!priceList.value || priceList.value.length === 0) return 100;

  // 找到最大和最小价格来标准化显示
  const prices = priceList.value.map(item => item.price);
  const maxPrice = Math.max(...prices);
  const minPrice = Math.min(...prices);

  // 避免除零错误
  const range = maxPrice - minPrice > 0 ? maxPrice - minPrice : maxPrice;
  if (range === 0) return 150; // 如果所有价格都相同，返回中间高度

  // 计算相对于最小价格的增长百分比
  const relativeHeight = ((price - minPrice) / range) * 200 + 80; // 80rpx-280rpx 之间
  return Math.min(280, Math.max(80, relativeHeight)); // 限制在 80rpx-280rpx 之间
};

// 柱形点击事件
const onBarClick = (item: any, index: number) => {
  // 更新激活的柱状图索引
  activeBarIndex.value = index;

  // 延迟显示弹窗，以便用户可以看到动画效果
  setTimeout(() => {
    uni.showModal({
      title: '钢材详情',
      content: `${item.name} - 规格: ${item.spec}\\n价格: ¥${item.price}/吨\\n日期: ${item.date}`,
      showCancel: false,
      confirmText: '确定'
    });
  }, 1000); // 300毫秒后显示弹窗，给动画效果留出时间
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

.chart-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.chart-container {
  height: 550rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.chart-wrapper {
  width: 100%;
  height: 300rpx;
}

.charts {
  width: 100%;
  height: 100%;
}

/* 简单图表样式 */
.simple-chart {
  width: 100%;
  height: 420rpx;
  display: flex;
  align-items: flex-end;
  padding: 30rpx 20rpx 20rpx;
  box-sizing: border-box;
  position: relative;
  background: linear-gradient(to top, #f8f9fa, #ffffff);
  border-radius: 15rpx;
  box-shadow: inset 0 0 10rpx rgba(0, 0, 0, 0.05);
}

.chart-axis-y {
  position: absolute;
  left: 20rpx;
  top: 0;
  bottom: 40rpx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  color: #7f8c8d;
  font-size: 20rpx;
  z-index: 1;
  padding: 10rpx 0;
  writing-mode: vertical-lr;
  text-orientation: mixed;
}

.chart-bars {
  flex: 1;
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  height: 100%;
  padding-left: 50rpx;
  padding-right: 20rpx;
  border-bottom: 4rpx solid #bdc3c7;
  position: relative;
}

.chart-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  flex: 1;
  margin: 0 8rpx;
  position: relative;
}

.bar-container {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  width: 100%;
  height: 360rpx; /* 固定高度作为计算基准 */
}

.bar {
  width: 60%;
  background: linear-gradient(135deg, #3498db, #2980b9);
  border-radius: 12rpx 12rpx 4rpx 4rpx;
  position: relative;
  box-shadow: 0 -4rpx 12rpx rgba(52, 152, 219, 0.3), inset 0 2rpx 4rpx rgba(255, 255, 255, 0.2);
}

.bar-active {
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  box-shadow: 0 -10rpx 20rpx rgba(231, 76, 60, 0.5), inset 0 2rpx 6rpx rgba(255, 255, 255, 0.3);
  z-index: 10;
}

.bar-value {
  position: absolute;
  top: -35rpx;
  left: 50%;
  transform: translateX(-50%);
  color: #2c3e50;
  font-size: 20rpx;
  font-weight: bold;
  text-align: center;
  white-space: nowrap;
  background: rgba(255, 255, 255, 0.9);
  padding: 4rpx 8rpx;
  border-radius: 6rpx;
}

.bar-label {
  margin-top: 15rpx;
  font-size: 20rpx;
  color: #34495e;
  text-align: center;
  word-break: break-all;
  white-space: nowrap;
  line-height: 1.4;
  max-width: 100%;
  font-weight: 500;
}

.chart-placeholder {
  text-align: center;
}

.chart-text {
  font-size: 32rpx;
  color: #2c3e50;
  display: block;
  margin-bottom: 10rpx;
}

.chart-desc {
  font-size: 24rpx;
  color: #7f8c8d;
}

.price-list-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.price-item {
  padding: 25rpx 0;
  border-bottom: 1rpx solid #ecf0f1;
}

.price-item:last-child {
  border-bottom: none;
}

.price-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10rpx;
}

.price-name {
  font-size: 30rpx;
  font-weight: 500;
  color: #2c3e50;
}

.price-value {
  font-size: 30rpx;
  font-weight: bold;
  color: #2980b9;
}

.price-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10rpx;
}

.price-change {
  font-size: 24rpx;
  padding: 4rpx 12rpx;
  border-radius: 20rpx;
}

.price-change.positive {
  color: #27ae60;
  background-color: #eafaf1;
}

.price-change.negative {
  color: #e74c3c;
  background-color: #fadbd8;
}

.price-date {
  font-size: 24rpx;
  color: #95a5a6;
}

.price-spec {
  font-size: 26rpx;
  color: #7f8c8d;
}

.query-section {
  background-color: #ffffff;
  margin: 0 30rpx 30rpx;
  border-radius: 15rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.input-group {
  margin-bottom: 25rpx;
}

.label {
  display: block;
  margin-bottom: 10rpx;
  font-size: 28rpx;
  font-weight: 500;
  color: #2c3e50;
}

.picker {
  width: 100%;
  padding: 20rpx;
  border: 2rpx solid #3498db;
  border-radius: 12rpx;
  font-size: 28rpx;
  background-color: #ffffff;
  color: #333;
}

.input {
  width: 100%;
  padding: 20rpx;
  border: 2rpx solid #3498db;
  border-radius: 12rpx;
  font-size: 28rpx;
  box-sizing: border-box;
  background-color: #ffffff;
  color: #333;
}

.query-btn {
  width: 100%;
  padding: 25rpx;
  margin-top: 20rpx;
  border: none;
  border-radius: 15rpx;
  font-size: 32rpx;
  font-weight: 500;
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  color: white;
  box-shadow: 0 6rpx 20rpx rgba(52, 152, 219, 0.3);
  transition: all 0.3s;
}

.query-btn:active {
  opacity: 0.9;
}

/* 图表网格线效果 */
.chart-grid-line {
  position: absolute;
  width: 100%;
  height: 1rpx;
  background: rgba(189, 195, 199, 0.3);
  left: 0;
}

.chart-grid-line:nth-child(2) {
  top: 20%;
}

.chart-grid-line:nth-child(3) {
  top: 40%;
}

.chart-grid-line:nth-child(4) {
  top: 60%;
}

.chart-grid-line:nth-child(5) {
  top: 80%;
}


</style>
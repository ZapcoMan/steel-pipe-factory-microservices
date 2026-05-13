<template>
  <view class="container">
    <!-- 顶部品牌区 -->
    <view class="brand-header">
      <image class="logo" src="/static/logo.png" mode="aspectFit"/>
      <text class="brand-name">钢管之都 e管通</text>
      <text class="subtitle">{{ title }}</text>
    </view>

    <view class="form-container">
      <!-- 钢管表单 -->
      <view v-if="currentType === 'steel'" class="form-section">
        <view class="input-group">
          <text class="label">外径 (mm)</text>
          <uni-easyinput type="number" v-model="steelParams.outerDiameter" placeholder="请输入外径" class="input" />
        </view>
        <view class="input-group">
          <text class="label">壁厚 (mm)</text>
          <uni-easyinput type="number" v-model="steelParams.wallThickness" placeholder="请输入壁厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="steelParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="steelParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 方矩管表单 -->
      <view v-if="currentType === 'rectangular'" class="form-section">
        <view class="input-group">
          <text class="label">边长A (mm)</text>
          <uni-easyinput type="number" v-model="rectangularParams.sideA" placeholder="请输入边长A" class="input" />
        </view>
        <view class="input-group">
          <text class="label">边长B (mm)</text>
          <uni-easyinput type="number" v-model="rectangularParams.sideB" placeholder="请输入边长B" class="input" />
        </view>
        <view class="input-group">
          <text class="label">壁厚 (mm)</text>
          <uni-easyinput type="number" v-model="rectangularParams.wallThickness" placeholder="请输入壁厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="rectangularParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="rectangularParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 工字钢表单 -->
      <view v-if="currentType === 'i-beam'" class="form-section">
        <view class="input-group">
          <text class="label">腰高 (mm)</text>
          <uni-easyinput type="number" v-model="iBeamParams.height" placeholder="请输入腰高" class="input" />
        </view>
        <view class="input-group">
          <text class="label">腿宽 (mm)</text>
          <uni-easyinput type="number" v-model="iBeamParams.flangeWidth" placeholder="请输入腿宽" class="input" />
        </view>
        <view class="input-group">
          <text class="label">腰厚 (mm)</text>
          <uni-easyinput type="number" v-model="iBeamParams.webThickness" placeholder="请输入腰厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="iBeamParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="iBeamParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 角钢表单 -->
      <view v-if="currentType === 'angle'" class="form-section">
        <view class="input-group">
          <text class="label">边宽A (mm)</text>
          <uni-easyinput type="number" v-model="angleParams.edgeA" placeholder="请输入边宽A" class="input" />
        </view>
        <view class="input-group">
          <text class="label">边宽B (mm)</text>
          <uni-easyinput type="number" v-model="angleParams.edgeB" placeholder="请输入边宽B" class="input" />
        </view>
        <view class="input-group">
          <text class="label">厚度 (mm)</text>
          <uni-easyinput type="number" v-model="angleParams.thickness" placeholder="请输入厚度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="angleParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="angleParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 槽钢表单 -->
      <view v-if="currentType === 'channel'" class="form-section">
        <view class="input-group">
          <text class="label">腰高 (mm)</text>
          <uni-easyinput type="number" v-model="channelParams.height" placeholder="请输入腰高" class="input" />
        </view>
        <view class="input-group">
          <text class="label">腿宽 (mm)</text>
          <uni-easyinput type="number" v-model="channelParams.flangeWidth" placeholder="请输入腿宽" class="input" />
        </view>
        <view class="input-group">
          <text class="label">腰厚 (mm)</text>
          <uni-easyinput type="number" v-model="channelParams.webThickness" placeholder="请输入腰厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="channelParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="channelParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 铝管表单 -->
      <view v-if="currentType === 'aluminum'" class="form-section">
        <view class="input-group">
          <text class="label">外径 (mm)</text>
          <uni-easyinput type="number" v-model="aluminumParams.outerDiameter" placeholder="请输入外径" class="input" />
        </view>
        <view class="input-group">
          <text class="label">壁厚 (mm)</text>
          <uni-easyinput type="number" v-model="aluminumParams.wallThickness" placeholder="请输入壁厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="aluminumParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="aluminumParams.density" placeholder="铝材约为2.7" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- H型钢表单 -->
      <view v-if="currentType === 'h-beam'" class="form-section">
        <view class="input-group">
          <text class="label">高度 (mm)</text>
          <uni-easyinput type="number" v-model="hBeamParams.height" placeholder="请输入高度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">翼缘宽度 (mm)</text>
          <uni-easyinput type="number" v-model="hBeamParams.flangeWidth" placeholder="请输入翼缘宽度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">腹板厚度 (mm)</text>
          <uni-easyinput type="number" v-model="hBeamParams.webThickness" placeholder="请输入腹板厚度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">翼缘厚度 (mm)</text>
          <uni-easyinput type="number" v-model="hBeamParams.flangeThickness" placeholder="请输入翼缘厚度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="hBeamParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="hBeamParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- C型钢表单 -->
      <view v-if="currentType === 'c-beam'" class="form-section">
        <view class="input-group">
          <text class="label">高度 (mm)</text>
          <uni-easyinput type="number" v-model="cBeamParams.height" placeholder="请输入高度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">翼缘宽度 (mm)</text>
          <uni-easyinput type="number" v-model="cBeamParams.flangeWidth" placeholder="请输入翼缘宽度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">腰厚 (mm)</text>
          <uni-easyinput type="number" v-model="cBeamParams.webThickness" placeholder="请输入腰厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="cBeamParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="cBeamParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 黄铜管表单 -->
      <view v-if="currentType === 'brass'" class="form-section">
        <view class="input-group">
          <text class="label">外径 (mm)</text>
          <uni-easyinput type="number" v-model="brassParams.outerDiameter" placeholder="请输入外径" class="input" />
        </view>
        <view class="input-group">
          <text class="label">壁厚 (mm)</text>
          <uni-easyinput type="number" v-model="brassParams.wallThickness" placeholder="请输入壁厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="brassParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="brassParams.density" placeholder="黄铜约为8.5" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 不锈钢管表单 -->
      <view v-if="currentType === 'stainless'" class="form-section">
        <view class="input-group">
          <text class="label">外径 (mm)</text>
          <uni-easyinput type="number" v-model="stainlessParams.outerDiameter" placeholder="请输入外径" class="input" />
        </view>
        <view class="input-group">
          <text class="label">壁厚 (mm)</text>
          <uni-easyinput type="number" v-model="stainlessParams.wallThickness" placeholder="请输入壁厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="stainlessParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="stainlessParams.density" placeholder="不锈钢约为7.93" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 六角钢表单 -->
      <view v-if="currentType === 'hexagonal'" class="form-section">
        <view class="input-group">
          <text class="label">对边距离 (mm)</text>
          <uni-easyinput type="number" v-model="hexagonalParams.distance" placeholder="请输入对边距离" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="hexagonalParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="hexagonalParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 紫铜管表单 -->
      <view v-if="currentType === 'purple-copper'" class="form-section">
        <view class="input-group">
          <text class="label">外径 (mm)</text>
          <uni-easyinput type="number" v-model="purpleCopperParams.outerDiameter" placeholder="请输入外径" class="input" />
        </view>
        <view class="input-group">
          <text class="label">壁厚 (mm)</text>
          <uni-easyinput type="number" v-model="purpleCopperParams.wallThickness" placeholder="请输入壁厚" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="purpleCopperParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="purpleCopperParams.density" placeholder="紫铜约为8.9" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 方扁钢表单 -->
      <view v-if="currentType === 'flat'" class="form-section">
        <view class="input-group">
          <text class="label">宽度 (mm)</text>
          <uni-easyinput type="number" v-model="flatParams.width" placeholder="请输入宽度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">厚度 (mm)</text>
          <uni-easyinput type="number" v-model="flatParams.thickness" placeholder="请输入厚度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="flatParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="flatParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 盘圆钢筋表单 -->
      <view v-if="currentType === 'round'" class="form-section">
        <view class="input-group">
          <text class="label">直径 (mm)</text>
          <uni-easyinput type="number" v-model="roundParams.diameter" placeholder="请输入直径" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="roundParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="roundParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 八角钢表单 -->
      <view v-if="currentType === 'octagonal'" class="form-section">
        <view class="input-group">
          <text class="label">对边距离 (mm)</text>
          <uni-easyinput type="number" v-model="octagonalParams.distance" placeholder="请输入对边距离" class="input" />
        </view>
        <view class="input-group">
          <text class="label">长度 (m)</text>
          <uni-easyinput type="number" v-model="octagonalParams.length" placeholder="请输入长度" class="input" />
        </view>
        <view class="input-group">
          <text class="label">材质密度 (g/cm³)</text>
          <uni-easyinput type="number" v-model="octagonalParams.density" placeholder="一般钢材为7.85" class="input" />
        </view>
        <view class="input-group">
          <text class="label">单价 (元/kg)</text>
          <uni-easyinput type="number" v-model="price" placeholder="请输入单价" class="input" />
        </view>
      </view>

      <!-- 计算按钮 -->
      <button @click="calculateWeight" class="calculate-btn" style="display: none;">计算</button>

      <!-- 错误提示 -->
      <view v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </view>

      <!-- 计算结果 -->
      <view class="result-section" v-if="weight !== null">
        <text class="result-label">重量：</text>
        <text class="result-value">{{ weight !== null ? weight.toFixed(3) : '0.000' }} kg</text>
        <text class="result-label">总价：</text>
        <text class="result-value">{{ totalPrice !== null ? totalPrice.toFixed(2) : '0.00' }} 元</text>
      </view>

      <button @click="resetForm" class="reset-btn">重置</button>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue'
import uniEasyinput from '@dcloudio/uni-ui/lib/uni-easyinput/uni-easyinput.vue'

// 页面参数
const currentType = ref('')
const title = ref('')
const price = ref(0) // 单价
const errorMessage = ref('') // 错误消息

// 钢管参数
const steelParams = ref({
  outerDiameter: 0,
  wallThickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 方矩管参数
const rectangularParams = ref({
  sideA: 0,
  sideB: 0,
  wallThickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 工字钢参数
const iBeamParams = ref({
  height: 0,
  flangeWidth: 0,
  webThickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 角钢参数
const angleParams = ref({
  edgeA: 0,
  edgeB: 0,
  thickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 槽钢参数
const channelParams = ref({
  height: 0,
  flangeWidth: 0,
  webThickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 铝管参数
const aluminumParams = ref({
  outerDiameter: 0,
  wallThickness: 0,
  length: 0,
  density: 2.7 // g/cm³
})

// H型钢参数
const hBeamParams = ref({
  height: 0,
  flangeWidth: 0,
  webThickness: 0,
  flangeThickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// C型钢参数
const cBeamParams = ref({
  height: 0,
  flangeWidth: 0,
  webThickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 黄铜管参数
const brassParams = ref({
  outerDiameter: 0,
  wallThickness: 0,
  length: 0,
  density: 8.5 // g/cm³
})

// 不锈钢管参数
const stainlessParams = ref({
  outerDiameter: 0,
  wallThickness: 0,
  length: 0,
  density: 7.93 // g/cm³
})

// 六角钢参数
const hexagonalParams = ref({
  distance: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 紫铜管参数
const purpleCopperParams = ref({
  outerDiameter: 0,
  wallThickness: 0,
  length: 0,
  density: 8.9 // g/cm³
})

// 方扁钢参数
const flatParams = ref({
  width: 0,
  thickness: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 盘圆钢筋参数
const roundParams = ref({
  diameter: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 八角钢参数
const octagonalParams = ref({
  distance: 0,
  length: 0,
  density: 7.85 // g/cm³
})

// 结果
const weight = ref<number | null>(null)

// 计算总价
const totalPrice = computed(() => {
  if (weight.value !== null && weight.value >= 0) {
    return weight.value * price.value
  }
  return 0
})

onMounted(() => {
  // 获取传递的类型参数
  const options = getCurrentPages()[getCurrentPages().length - 1].options
  currentType.value = options.type || 'steel'

  // 设置标题
  setTitle()
  
  // 添加监听器实现自动计算
  setupAutoCalculation()
})

const setTitle = () => {
  const titles: Record<string, string> = {
    steel: '钢管重量计算',
    rectangular: '方矩管重量计算',
    'i-beam': '工字钢重量计算',
    angle: '角钢重量计算',
    channel: '槽钢重量计算',
    aluminum: '铝管重量计算',
    'h-beam': 'H型钢重量计算',
    'c-beam': 'C型钢重量计算',
    brass: '黄铜管重量计算',
    stainless: '不锈钢管重量计算',
    hexagonal: '六角钢重量计算',
    'purple-copper': '紫铜管重量计算',
    flat: '方扁钢重量计算',
    round: '盘圆钢筋重量计算',
    octagonal: '八角钢重量计算'
  }
  title.value = titles[currentType.value] || '金属重量计算'
}

// 设置自动计算监听器
const setupAutoCalculation = () => {
  // 根据不同类型监听不同的参数
  switch(currentType.value) {
    case 'steel':
      watch([
        () => steelParams.value.outerDiameter,
        () => steelParams.value.wallThickness,
        () => steelParams.value.length,
        () => steelParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'rectangular':
      watch([
        () => rectangularParams.value.sideA,
        () => rectangularParams.value.sideB,
        () => rectangularParams.value.wallThickness,
        () => rectangularParams.value.length,
        () => rectangularParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'i-beam':
      watch([
        () => iBeamParams.value.height,
        () => iBeamParams.value.flangeWidth,
        () => iBeamParams.value.webThickness,
        () => iBeamParams.value.length,
        () => iBeamParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'angle':
      watch([
        () => angleParams.value.edgeA,
        () => angleParams.value.edgeB,
        () => angleParams.value.thickness,
        () => angleParams.value.length,
        () => angleParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'channel':
      watch([
        () => channelParams.value.height,
        () => channelParams.value.flangeWidth,
        () => channelParams.value.webThickness,
        () => channelParams.value.length,
        () => channelParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'aluminum':
      watch([
        () => aluminumParams.value.outerDiameter,
        () => aluminumParams.value.wallThickness,
        () => aluminumParams.value.length,
        () => aluminumParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'h-beam':
      watch([
        () => hBeamParams.value.height,
        () => hBeamParams.value.flangeWidth,
        () => hBeamParams.value.webThickness,
        () => hBeamParams.value.flangeThickness,
        () => hBeamParams.value.length,
        () => hBeamParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'c-beam':
      watch([
        () => cBeamParams.value.height,
        () => cBeamParams.value.flangeWidth,
        () => cBeamParams.value.webThickness,
        () => cBeamParams.value.length,
        () => cBeamParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'brass':
      watch([
        () => brassParams.value.outerDiameter,
        () => brassParams.value.wallThickness,
        () => brassParams.value.length,
        () => brassParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'stainless':
      watch([
        () => stainlessParams.value.outerDiameter,
        () => stainlessParams.value.wallThickness,
        () => stainlessParams.value.length,
        () => stainlessParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'hexagonal':
      watch([
        () => hexagonalParams.value.distance,
        () => hexagonalParams.value.length,
        () => hexagonalParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'purple-copper':
      watch([
        () => purpleCopperParams.value.outerDiameter,
        () => purpleCopperParams.value.wallThickness,
        () => purpleCopperParams.value.length,
        () => purpleCopperParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'flat':
      watch([
        () => flatParams.value.width,
        () => flatParams.value.thickness,
        () => flatParams.value.length,
        () => flatParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'round':
      watch([
        () => roundParams.value.diameter,
        () => roundParams.value.length,
        () => roundParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
      
    case 'octagonal':
      watch([
        () => octagonalParams.value.distance,
        () => octagonalParams.value.length,
        () => octagonalParams.value.density,
        () => price.value
      ], calculateWeight, { immediate: true })
      break
  }
}

const calculateWeight = () => {
  let calculatedWeight = 0
  let isValid = true
  errorMessage.value = '' // 清除之前的错误消息

  switch(currentType.value) {
    case 'steel':
      // 钢管重量计算：π * (外径 - 壁厚) * 壁厚 * 长度 * 密度 / 1000
      calculatedWeight = Math.PI *
          (steelParams.value.outerDiameter - steelParams.value.wallThickness) *
          steelParams.value.wallThickness *
          steelParams.value.length *
          steelParams.value.density / 1000
      break

    case 'rectangular':
      // 方矩管重量计算：(边长A + 边长B - 2*壁厚) * 壁厚 * 2 * 长度 * 密度 / 1000
      calculatedWeight = (rectangularParams.value.sideA + rectangularParams.value.sideB -
              2 * rectangularParams.value.wallThickness) *
          rectangularParams.value.wallThickness * 2 *
          rectangularParams.value.length *
          rectangularParams.value.density / 1000
      break

    case 'i-beam':
      // 工字钢重量计算：[(腰高 * 腰厚) + 2*(腿宽 * 厚度)] * 长度 * 密度 / 1000
      calculatedWeight = (iBeamParams.value.height * iBeamParams.value.webThickness +
              2 * iBeamParams.value.flangeWidth * iBeamParams.value.webThickness) *
          iBeamParams.value.length *
          iBeamParams.value.density / 1000
      break

    case 'angle':
      // 角钢重量计算：(边宽A + 边宽B - 厚度) * 厚度 * 长度 * 密度 / 1000
      calculatedWeight = (angleParams.value.edgeA + angleParams.value.edgeB -
              angleParams.value.thickness) *
          angleParams.value.thickness *
          angleParams.value.length *
          angleParams.value.density / 1000
      break

    case 'channel':
      // 槽钢重量计算：[(腰高 * 腰厚) + 2*(腿宽 * 厚度)] * 长度 * 密度 / 1000
      calculatedWeight = (channelParams.value.height * channelParams.value.webThickness +
              2 * channelParams.value.flangeWidth * channelParams.value.webThickness) *
          channelParams.value.length *
          channelParams.value.density / 1000
      break

    case 'aluminum':
      // 铝管重量计算：π * (外径 - 壁厚) * 壁厚 * 长度 * 密度 / 1000
      calculatedWeight = Math.PI *
          (aluminumParams.value.outerDiameter - aluminumParams.value.wallThickness) *
          aluminumParams.value.wallThickness *
          aluminumParams.value.length *
          aluminumParams.value.density / 1000
      break

    case 'h-beam':
      // H型钢重量计算：[(高度 * 腹板厚度) + 2*(翼缘宽度 * 翼缘厚度)] * 长度 * 密度 / 1000
      calculatedWeight = (hBeamParams.value.height * hBeamParams.value.webThickness +
              2 * hBeamParams.value.flangeWidth * hBeamParams.value.flangeThickness) *
          hBeamParams.value.length *
          hBeamParams.value.density / 1000
      break

    case 'c-beam':
      // C型钢重量计算：[(高度 * 腰厚) + 2*(翼缘宽度 * 腰厚)] * 长度 * 密度 / 1000
      calculatedWeight = (cBeamParams.value.height * cBeamParams.value.webThickness +
              2 * cBeamParams.value.flangeWidth * cBeamParams.value.webThickness) *
          cBeamParams.value.length *
          cBeamParams.value.density / 1000
      break

    case 'brass':
      // 黄铜管重量计算：π * (外径 - 壁厚) * 壁厚 * 长度 * 密度 / 1000
      calculatedWeight = Math.PI *
          (brassParams.value.outerDiameter - brassParams.value.wallThickness) *
          brassParams.value.wallThickness *
          brassParams.value.length *
          brassParams.value.density / 1000
      break

    case 'stainless':
      // 不锈钢管重量计算：π * (外径 - 壁厚) * 壁厚 * 长度 * 密度 / 1000
      calculatedWeight = Math.PI *
          (stainlessParams.value.outerDiameter - stainlessParams.value.wallThickness) *
          stainlessParams.value.wallThickness *
          stainlessParams.value.length *
          stainlessParams.value.density / 1000
      break

    case 'hexagonal':
      // 六角钢重量计算：0.866 * 对边距离² * 长度 * 密度 / 1000
      calculatedWeight = 0.866 *
          Math.pow(hexagonalParams.value.distance, 2) *
          hexagonalParams.value.length *
          hexagonalParams.value.density / 1000
      break

    case 'purple-copper':
      // 紫铜管重量计算：π * (外径 - 壁厚) * 壁厚 * 长度 * 密度 / 1000
      calculatedWeight = Math.PI *
          (purpleCopperParams.value.outerDiameter - purpleCopperParams.value.wallThickness) *
          purpleCopperParams.value.wallThickness *
          purpleCopperParams.value.length *
          purpleCopperParams.value.density / 1000
      break

    case 'flat':
      // 方扁钢重量计算：宽度 * 厚度 * 长度 * 密度 / 1000
      calculatedWeight = flatParams.value.width *
          flatParams.value.thickness *
          flatParams.value.length *
          flatParams.value.density / 1000
      break

    case 'round':
      // 盘圆钢筋重量计算：π * (直径/2)² * 长度 * 密度 / 1000
      calculatedWeight = Math.PI *
          Math.pow(roundParams.value.diameter / 2, 2) *
          roundParams.value.length *
          roundParams.value.density / 1000
      break

    case 'octagonal':
      // 八角钢重量计算：0.828 * 对边距离² * 长度 * 密度 / 1000
      calculatedWeight = 0.828 *
          Math.pow(octagonalParams.value.distance, 2) *
          octagonalParams.value.length *
          octagonalParams.value.density / 1000
      break
  }

  if (isValid) {
    weight.value = calculatedWeight
  } else {
    weight.value = calculatedWeight
  }
}

const resetForm = () => {
  // 根据当前类型重置对应的表单
  switch(currentType.value) {
    case 'steel':
      steelParams.value = {
        outerDiameter: 0,
        wallThickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'rectangular':
      rectangularParams.value = {
        sideA: 0,
        sideB: 0,
        wallThickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'i-beam':
      iBeamParams.value = {
        height: 0,
        flangeWidth: 0,
        webThickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'angle':
      angleParams.value = {
        edgeA: 0,
        edgeB: 0,
        thickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'channel':
      channelParams.value = {
        height: 0,
        flangeWidth: 0,
        webThickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'aluminum':
      aluminumParams.value = {
        outerDiameter: 0,
        wallThickness: 0,
        length: 0,
        density: 2.7
      }
      break
    case 'h-beam':
      hBeamParams.value = {
        height: 0,
        flangeWidth: 0,
        webThickness: 0,
        flangeThickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'c-beam':
      cBeamParams.value = {
        height: 0,
        flangeWidth: 0,
        webThickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'brass':
      brassParams.value = {
        outerDiameter: 0,
        wallThickness: 0,
        length: 0,
        density: 8.5
      }
      break
    case 'stainless':
      stainlessParams.value = {
        outerDiameter: 0,
        wallThickness: 0,
        length: 0,
        density: 7.93
      }
      break
    case 'hexagonal':
      hexagonalParams.value = {
        distance: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'purple-copper':
      purpleCopperParams.value = {
        outerDiameter: 0,
        wallThickness: 0,
        length: 0,
        density: 8.9
      }
      break
    case 'flat':
      flatParams.value = {
        width: 0,
        thickness: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'round':
      roundParams.value = {
        diameter: 0,
        length: 0,
        density: 7.85
      }
      break
    case 'octagonal':
      octagonalParams.value = {
        distance: 0,
        length: 0,
        density: 7.85
      }
      break
  }
  weight.value = null
  price.value = 0 // 重置价格
  errorMessage.value = '' // 清除错误消息
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

.form-container {
  background-color: #ffffff;
  border-radius: 15rpx;
  padding: 30rpx;
  margin: 0 30rpx 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  border: 1rpx solid #e0e0e0;
}

.form-section {
  margin-bottom: 20rpx;
}

.input-group {
  margin-bottom: 20rpx;
}

.label {
  display: block;
  margin-bottom: 10rpx;
  font-size: 28rpx;
  font-weight: 500;
  color: #2c3e50;
}

.input {
  width: 100%;
  padding: 20rpx;
  border: 2rpx solid #3498db;
  border-radius: 12rpx;
  font-size: 28rpx;
  box-sizing: border-box;
  background-color: #ffffff;
  transition: all 0.3s;
  color: #333;
  font-weight: 500;
}

.input:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 4rpx rgba(52, 152, 219, 0.2);
  background-color: #fff;
}

.error-message {
  color: #e74c3c;
  background-color: #fdf2f2;
  border: 1rpx solid #f5c6cb;
  border-radius: 12rpx;
  padding: 20rpx;
  margin: 20rpx 0;
  text-align: center;
  font-weight: 500;
  font-size: 28rpx;
}

.result-section {
  margin: 30rpx 0;
  padding: 30rpx;
  background: linear-gradient(135deg, #e8f4fd 0%, #d1e7ff 100%);
  border-radius: 15rpx;
  text-align: center;
  box-shadow: 0 4rpx 10rpx rgba(0, 0, 0, 0.05);
}

.result-label {
  font-size: 30rpx;
  font-weight: 500;
  color: #2c3e50;
  margin-right: 15rpx;
  display: block;
  margin-bottom: 10rpx;
}

.result-value {
  font-size: 36rpx;
  font-weight: bold;
  color: #2980b9;
  display: block;
  margin-top: 10rpx;
}

.calculate-btn, .reset-btn {
  width: 100%;
  padding: 25rpx;
  margin: 20rpx 0;
  border: none;
  border-radius: 15rpx;
  font-size: 32rpx;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s;
}

.calculate-btn {
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  color: white;
  box-shadow: 0 6rpx 20rpx rgba(52, 152, 219, 0.3);
}

.calculate-btn:active {
  transform: translateY(-3rpx);
  box-shadow: 0 8rpx 25rpx rgba(52, 152, 219, 0.4);
}

.reset-btn {
  background: linear-gradient(135deg, #f0f0f0 0%, #e0e0e0 100%);
  color: #2c3e50;
  box-shadow: 0 6rpx 20rpx rgba(0, 0, 0, 0.1);
}

.reset-btn:active {
  transform: translateY(-3rpx);
  box-shadow: 0 8rpx 25rpx rgba(0, 0, 0, 0.15);
}
</style>
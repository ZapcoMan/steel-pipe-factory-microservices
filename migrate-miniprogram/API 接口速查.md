# API 接口速查手册

## 📍 基础信息

**基础 URL**: `http://192.168.1.12:9990` (开发环境)  
**请求方式**: GET/POST  
**响应格式**: JSON  
**超时时间**: 60000ms

---

## 🔗 接口列表

### 1️⃣ 首页相关

| 接口 | 方法 | 说明 |
|------|------|------|
| `/hello` | GET | 测试接口 |
| `/uniapp/getObtainInformationDataList` | GET | 获取资讯列表 |
| `/uniapp/getfeaturedCompaniesDataList` | GET | 获取合作企业 |
| `/uniapp/getIndustryNewsDetailData?id={id}` | GET | 获取资讯详情 |

---

### 2️⃣ 价格行情

| 接口 | 方法 | 说明 |
|------|------|------|
| `/uniapp/getSteelPriceData` | GET | 获取钢材价格 |

**响应示例**:
```json
[
  {
    "name": "螺纹钢",
    "spec": "HRB400E Φ12-14mm",
    "price": 4200,
    "change": 1.2,
    "date": "2025-12-25"
  }
]
```

---

### 3️⃣ 技术资料

| 接口 | 方法 | 说明 |
|------|------|------|
| `/uniapp/getTechData` | GET | 获取技术资料 |

**响应结构**:
```json
{
  "materials": [...],      // 材质说明
  "comparison": [...],     // 特性对比
  "applications": [...]    // 应用场景
}
```

---

### 4️⃣ 样品展示

| 接口 | 方法 | 说明 |
|------|------|------|
| `/uniapp/getSampleData` | GET | 获取样品数据 |

**响应字段**:
- `id`: 样品 ID
- `name`: 样品名称
- `spec`: 规格
- `material`: 材质
- `features`: 特性数组
- `application`: 应用领域
- `image`: 图片路径
- `category`: 分类
- `isNew`: 是否新品

---

### 5️⃣ 其他

| 接口 | 方法 | 说明 |
|------|------|------|
| `/uniapp/getAboutData` | GET | 获取关于页面数据 |

---

## 💻 代码调用示例

### 导入 API
```typescript
import homeApi from '@/api/homeApi';
import steelPriceApi from '@/api/steelPriceApi';
import techDataApi from '@/api/techDataApi';
import sampleApi from '@/api/sampleApi';
```

### 调用示例
```typescript
// 获取资讯列表
const newsList = await homeApi.getObtainInformationDataList();

// 获取价格数据
const priceData = await steelPriceApi.getSteelPriceData();

// 获取技术资料
const techData = await techDataApi.getTechData();

// 获取样品数据
const sampleData = await sampleApi.getSampleData();
```

---

## ⚠️ 错误处理

所有 API 都包含容错机制，请求失败时会：
1. 返回模拟数据
2. 在控制台输出错误日志
3. 显示用户提示（如需要）

---

## 🔧 修改 API 地址

编辑 `.env` 或 `.env.production`:
```env
VITE_API_BASE_URL=http://你的服务器地址：端口
VITE_TIMEOUT=60000
```

---



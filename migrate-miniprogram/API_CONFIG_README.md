# API 配置说明

## 统一配置方式

所有 API 请求地址已经统一在配置文件中管理，方便维护和切换环境。

## 配置文件位置

- **主配置文件**: `src/config/index.ts` - 包含开发和生产环境的配置
- **环境变量文件**:
  - `.env` - 开发环境配置（默认使用）
  - `.env.production` - 生产环境配置
  - `.env.example` - 配置示例文件

## 如何修改 API 地址

### 方法一：直接修改环境变量文件（推荐）

编辑 `.env` 文件（开发环境）或 `.env.production` 文件（生产环境）：

```env
# API 基础地址
VITE_API_BASE_URL=http://192.168.1.12:9990

# 请求超时时间 (毫秒)
VITE_TIMEOUT=60000
```

### 方法二：修改配置文件

编辑 `src/config/index.ts` 文件，修改对应的配置值：

```typescript
const development = {
  API_BASE_URL: 'http://192.168.1.12:9990',
  TIMEOUT: 60000,
}

const production = {
  API_BASE_URL: 'https://api.steelfactory.com',
  TIMEOUT: 60000,
}
```

## 环境切换

- **开发环境**: 运行 `npm run dev:mp-weixin` 时自动使用 `.env` 配置
- **生产环境**: 运行 `npm run build:mp-weixin` 时自动使用 `.env.production` 配置

## 已更新的文件

以下文件已更新为使用统一配置：

- ✅ `src/utils/request.ts` - 请求工具类，从配置读取 baseURL 和 timeout
- ✅ `src/api/api.ts` - 通用 API 服务
- ✅ `src/api/homeApi.ts` - 首页 API 服务
- ✅ `src/api/sampleApi.ts` - 样品展示 API 服务
- ✅ `src/api/steelPriceApi.ts` - 钢材价格 API 服务
- ✅ `src/api/techDataApi.ts` - 技术资料 API 服务

## 注意事项

1. 所有以 `VITE_` 开头的环境变量才会暴露给客户端代码
2. 修改环境变量后需要重启开发服务器才能生效
3. 建议不要将 `.env` 文件提交到版本控制系统（如果是本地特定配置）

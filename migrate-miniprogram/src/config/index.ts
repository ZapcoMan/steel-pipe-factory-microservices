/**
 * 应用配置文件
 */

// 开发环境配置
const development = {
  // API 基础地址 - 优先使用环境变量，其次使用默认值
  API_BASE_URL: import.meta.env.VITE_API_BASE_URL || 'http://192.168.1.12:9990',
  // 请求超时时间 (毫秒)
  TIMEOUT: Number(import.meta.env.VITE_TIMEOUT) || 60000,
}

// 生产环境配置
const production = {
  API_BASE_URL: import.meta.env.VITE_API_BASE_URL || 'https://api.steelfactory.com', // 生产环境地址
  TIMEOUT: Number(import.meta.env.VITE_TIMEOUT) || 60000,
}

// 根据当前环境导出配置
const config = import.meta.env.MODE === 'production' ? production : development

export default {
  // API 基础地址
  baseURL: config.API_BASE_URL,
  // 请求超时时间
  timeout: config.TIMEOUT,
  // 当前环境
  env: import.meta.env.MODE || 'development'
}

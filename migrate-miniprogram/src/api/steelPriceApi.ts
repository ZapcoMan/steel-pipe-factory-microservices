import HttpRequest from '@/utils/request';
import type {ResponseData} from "@/types";

// 创建实例 - 使用默认配置（从 config/index.ts 读取）
const http = new HttpRequest();

/**
 * 钢材价格行情 API 服务
 */
class SteelPriceApiService {
  /**
   * 获取钢材价格行情数据
   */
  getSteelPriceData() {
    // 尝试从服务器获取数据，如果失败则返回模拟数据
    return http.get('/uniapp/getSteelPriceData').then((res: ResponseData) => {
      // 如果返回的数据有效则返回实际数据
      if (res && res.code == 20000) {
        return res.data;
      }

    }).catch(() => {
      // 网络请求失败时返回模拟数据
      // 否则返回模拟数据
      return [
        {
          name: '模拟数据： 螺纹钢 ',
          spec: '模拟数据：HRB400E Φ12-14mm',
          price: 12200,
          change: 1.2,
          date: '模拟数据：2025-12-25'
        },
        {
          name: '模拟数据：线材',
          spec: '模拟数据：HPB300 Φ6.5-12mm',
          price: 4250,
          change: 3.8,
          date: '模拟数据：2025-12-25'
        },
        {
          name: '模拟数据：热轧板卷',
          spec: '模拟数据：Q235B 3.0mm',
          price: 4150,
          change: -0.5,
          date: '模拟数据：2025-12-25'
        },
        {
          name: '模拟数据：冷轧板卷',
          spec: '模拟数据：DC01 1.0mm',
          price: 4500,
          change: 0.3,
          date: '模拟数据：2025-12-25'
        },
        {
          name: '模拟数据：中厚板',
          spec: '模拟数据：Q235B 20mm',
          price: 0,
          change: 1.5,
          date: '模拟数据：2025-12-25'
        }
      ];
    });
  }
}

export default new SteelPriceApiService();
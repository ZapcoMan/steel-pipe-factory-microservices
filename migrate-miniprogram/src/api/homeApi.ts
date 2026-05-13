import HttpRequest from '@/utils/request';
import type {ResponseData} from "@/types";

// 创建实例 - 使用默认配置（从 config/index.ts 读取）
const http = new HttpRequest();

/**
 * 首页相关 API 服务
 */
class HomeApiService {
  /**
   * 获取首页数据
   */
  getHomeData() {
    return http.get('/hello');
  }

  /**
   * 获取资讯数据
   * @returns 资讯数据列表，如果获取失败则返回模拟数据
   */
  getObtainInformationDataList(){
    // 尝试从服务器获取数据，如果失败则返回模拟数据
    return http.get('/uniapp/getObtainInformationDataList').then((res: ResponseData) => {
      // 如果返回的数据有效则返回实际数据
      if (res && res.code == 20000) {
        return res.data;
      }
      // 否则返回模拟数据
      return [
        {id: 1, title: '模拟数据：【市场动态】螺纹钢价格今日上涨20元/吨，市场交易活跃 ', time: '10:30'},
        {id: 2, title: '模拟数据：【技术分享】不锈钢焊接工艺要点及常见问题解析', time: '09:15'},
        {id: 3, title: '模拟数据：【政策解读】钢铁行业节能减排新标准正式实施', time: '昨天'},
        {id: 4, title: '模拟数据：【行情分析】热轧卷板期货主力合约持续走强趋势', time: '前天'},
        {id: 5, title: '模拟数据：【产品推荐】新型高强度钢材在建筑领域应用案例', time: '3天前'},
      ];
    }).catch(() => {
      // 网络请求失败时返回模拟数据
      return [
        {id: 1, title: '模拟数据：【市场动态】螺纹钢价格今日上涨20元/吨，市场交易活跃 ', time: '10:30'},
        {id: 2, title: '模拟数据：【技术分享】不锈钢焊接工艺要点及常见问题解析', time: '09:15'},
        {id: 3, title: '模拟数据：【政策解读】钢铁行业节能减排新标准正式实施', time: '昨天'},
        {id: 4, title: '模拟数据：【行情分析】热轧卷板期货主力合约持续走强趋势', time: '前天'},
        {id: 5, title: '模拟数据：【产品推荐】新型高强度钢材在建筑领域应用案例', time: '3天前'},
      ];
    });
  }

  /**
   * 获取精选合作企业数据列表
   */
  getfeaturedCompaniesDataList(){
    // 尝试从服务器获取数据，如果失败则返回模拟数据
    return http.get('/uniapp/getfeaturedCompaniesDataList').then((res: ResponseData) => {
      // 如果返回的数据有效则返回实际数据
      if (res && res.code == 20000) {
        return res.data;
      }
      // 否则返回模拟数据
      return [
        {
          name: '模拟数据：山东钢铁集团',
          type: '模拟数据：国企',
          scope: '模拟数据：钢铁冶炼、钢材生产销售、技术研发',
          phone: '模拟数据：0531-88888888'
        },
        {
          name: '模拟数据：临沂华强钢材',
          type: '模拟数据：民企',
          scope: '模拟数据：各类钢材贸易、仓储物流、加工配送',
          phone: '模拟数据：0539-66666666'
        },
        {
          name: '模拟数据：青岛海西重机',
          type: '模拟数据：制造业',
          scope: '模拟数据：重型机械制造、钢结构加工、设备租赁',
          phone: '模拟数据：0532-77777777'
        }
      ];
    }).catch(() => {
      // 网络请求失败时返回模拟数据
      return [
        {
          name: '模拟数据：山东钢铁集团',
          type: '模拟数据：国企',
          scope: '模拟数据：钢铁冶炼、钢材生产销售、技术研发',
          phone: '模拟数据：0531-88888888'
        },
        {
          name: '模拟数据：临沂华强钢材',
          type: '模拟数据：民企',
          scope: '模拟数据：各类钢材贸易、仓储物流、加工配送',
          phone: '模拟数据：0539-66666666'
        },
        {
          name: '模拟数据：青岛海西重机',
          type: '模拟数据：制造业',
          scope: '模拟数据：重型机械制造、钢结构加工、设备租赁',
          phone: '模拟数据：0532-77777777'
        }
      ];
    });
  }
  /**
   * 获取资讯详情数据
   * @param id 资讯 ID
   * @returns 资讯详情数据，包含 content 字段
   */
  getIndustryNewsDetailData(id: number) {
    return http.get(`/uniapp/getIndustryNewsDetailData?id=${id}`);
  }
}

export default new HomeApiService();
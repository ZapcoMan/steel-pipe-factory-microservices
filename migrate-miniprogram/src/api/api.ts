import HttpRequest from '@/utils/request';
import type {ResponseData} from "@/types";

// 创建实例 - 使用默认配置（从 config/index.ts 读取）
const http = new HttpRequest();

/**
 * 通用 API 服务
 */
class ApiService {
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
   * 获取钢材价格行情数据
   */
  getSteelPriceData() {
    // 尝试从服务器获取数据，如果失败则返回模拟数据
    return http.get('/uniapp/getSteelPriceData').then((res: ResponseData) => {
      // 如果返回的数据有效则返回实际数据
      if (res && res.code == 20000) {
        return res.data;
      }
      // 否则返回模拟数据
      return [
        {
          name: '螺纹钢',
          spec: 'HRB400E Φ12-14mm',
          price: 4200,
          change: 1.2,
          date: '2025-12-25'
        },
        {
          name: '线材',
          spec: 'HPB300 Φ6.5-12mm',
          price: 4250,
          change: 0.8,
          date: '2025-12-25'
        },
        {
          name: '热轧板卷',
          spec: 'Q235B 3.0mm',
          price: 4150,
          change: -0.5,
          date: '2025-12-25'
        },
        {
          name: '冷轧板卷',
          spec: 'DC01 1.0mm',
          price: 4500,
          change: 0.3,
          date: '2025-12-25'
        },
        {
          name: '中厚板',
          spec: 'Q235B 20mm',
          price: 4300,
          change: 1.5,
          date: '2025-12-25'
        }
      ];
    }).catch(() => {
      // 网络请求失败时返回模拟数据
      return [
        {
          name: '螺纹钢',
          spec: 'HRB400E Φ12-14mm',
          price: 4200,
          change: 1.2,
          date: '2025-12-25'
        },
        {
          name: '线材',
          spec: 'HPB300 Φ6.5-12mm',
          price: 4250,
          change: 0.8,
          date: '2025-12-25'
        },
        {
          name: '热轧板卷',
          spec: 'Q235B 3.0mm',
          price: 4150,
          change: -0.5,
          date: '2025-12-25'
        },
        {
          name: '冷轧板卷',
          spec: 'DC01 1.0mm',
          price: 4500,
          change: 0.3,
          date: '2025-12-25'
        },
        {
          name: '中厚板',
          spec: 'Q235B 20mm',
          price: 4300,
          change: 1.5,
          date: '2025-12-25'
        }
      ];
    });
  }
  
  /**
   * 获取技术资料数据
   */
  getTechData() {
    // 尝试从服务器获取数据，如果失败则返回模拟数据
    return http.get('/uniapp/getTechData').then((res: ResponseData) => {
      // 如果返回的数据有效则返回实际数据
      if (res && res.code == 20000) {
        return res.data;
      }
      // 否则返回模拟数据
      return {
        materials: [
          {
            name: '碳素结构钢',
            grade: 'Q235',
            description: '碳素结构钢具有良好的综合力学性能和工艺性能，广泛应用于建筑结构、机械零件等领域。',
            features: [
              '良好的塑性和韧性',
              '易于焊接和加工',
              '成本较低',
              '耐大气腐蚀性一般'
            ]
          },
          {
            name: '优质碳素结构钢',
            grade: '45#钢',
            description: '含碳量约0.45%的优质碳素结构钢，具有较高的强度和耐磨性，适用于制造机械零件。',
            features: [
              '较高的强度和硬度',
              '良好的切削加工性',
              '淬透性较好',
              '焊接性能一般'
            ]
          },
          {
            name: '低合金高强度钢',
            grade: 'Q345',
            description: '在碳素结构钢基础上添加少量合金元素，具有更高的强度和良好的综合性能。',
            features: [
              '高强度重量比',
              '良好的焊接性能',
              '较好的低温韧性',
              '耐大气腐蚀性较好'
            ]
          },
          {
            name: '不锈钢',
            grade: '304',
            description: '含铬镍的不锈钢，具有优异的耐腐蚀性和良好的加工性能，广泛应用于各种环境。',
            features: [
              '优异的耐腐蚀性',
              '良好的耐热性',
              '优良的加工性能',
              '无磁性或弱磁性'
            ]
          }
        ],
        comparison: [
          {
            name: 'Q235',
            strength: '中等',
            toughness: '良好',
            corrosion: '一般',
            welding: '良好'
          },
          {
            name: 'Q345',
            strength: '较高',
            toughness: '良好',
            corrosion: '一般',
            welding: '良好'
          },
          {
            name: '45#钢',
            strength: '高',
            toughness: '中等',
            corrosion: '差',
            welding: '一般'
          },
          {
            name: '304不锈钢',
            strength: '中等',
            toughness: '良好',
            corrosion: '优异',
            welding: '良好'
          },
          {
            name: '42CrMo',
            strength: '很高',
            toughness: '良好',
            corrosion: '差',
            welding: '需预热'
          }
        ],
        applications: [
          {
            name: '建筑工程用钢',
            description: '主要用于建筑结构、桥梁、厂房等建筑领域，要求具有良好的强度、韧性和焊接性能。',
            scenarios: [
              '高层建筑结构',
              '大跨度桥梁',
              '工业厂房',
              '塔桅结构'
            ]
          },
          {
            name: '机械制造用钢',
            description: '用于制造各种机械零件，要求具有较高的强度、耐磨性和疲劳强度。',
            scenarios: [
              '轴类零件',
              '齿轮传动',
              '紧固件',
              '模具制造'
            ]
          },
          {
            name: '管道运输用钢',
            description: '用于输送油、气、水等介质的管道，要求具有良好的耐压性和耐腐蚀性。',
            scenarios: [
              '石油天然气管道',
              '城市供水管道',
              '化工管道',
              '蒸汽管道'
            ]
          },
          {
            name: '海洋工程用钢',
            description: '用于海上平台、船舶等海洋环境，要求具有优异的耐海水腐蚀性和抗疲劳性能。',
            scenarios: [
              '海上钻井平台',
              '船舶制造',
              '海底管道',
              '码头设施'
            ]
          }
        ]
      };
    }).catch(() => {
      // 网络请求失败时返回模拟数据
      return {
        materials: [
          {
            name: '碳素结构钢',
            grade: 'Q235',
            description: '碳素结构钢具有良好的综合力学性能和工艺性能，广泛应用于建筑结构、机械零件等领域。',
            features: [
              '良好的塑性和韧性',
              '易于焊接和加工',
              '成本较低',
              '耐大气腐蚀性一般'
            ]
          },
          {
            name: '优质碳素结构钢',
            grade: '45#钢',
            description: '含碳量约0.45%的优质碳素结构钢，具有较高的强度和耐磨性，适用于制造机械零件。',
            features: [
              '较高的强度和硬度',
              '良好的切削加工性',
              '淬透性较好',
              '焊接性能一般'
            ]
          },
          {
            name: '低合金高强度钢',
            grade: 'Q345',
            description: '在碳素结构钢基础上添加少量合金元素，具有更高的强度和良好的综合性能。',
            features: [
              '高强度重量比',
              '良好的焊接性能',
              '较好的低温韧性',
              '耐大气腐蚀性较好'
            ]
          },
          {
            name: '不锈钢',
            grade: '304',
            description: '含铬镍的不锈钢，具有优异的耐腐蚀性和良好的加工性能，广泛应用于各种环境。',
            features: [
              '优异的耐腐蚀性',
              '良好的耐热性',
              '优良的加工性能',
              '无磁性或弱磁性'
            ]
          }
        ],
        comparison: [
          {
            name: 'Q235',
            strength: '中等',
            toughness: '良好',
            corrosion: '一般',
            welding: '良好'
          },
          {
            name: 'Q345',
            strength: '较高',
            toughness: '良好',
            corrosion: '一般',
            welding: '良好'
          },
          {
            name: '45#钢',
            strength: '高',
            toughness: '中等',
            corrosion: '差',
            welding: '一般'
          },
          {
            name: '304不锈钢',
            strength: '中等',
            toughness: '良好',
            corrosion: '优异',
            welding: '良好'
          },
          {
            name: '42CrMo',
            strength: '很高',
            toughness: '良好',
            corrosion: '差',
            welding: '需预热'
          }
        ],
        applications: [
          {
            name: '建筑工程用钢',
            description: '主要用于建筑结构、桥梁、厂房等建筑领域，要求具有良好的强度、韧性和焊接性能。',
            scenarios: [
              '高层建筑结构',
              '大跨度桥梁',
              '工业厂房',
              '塔桅结构'
            ]
          },
          {
            name: '机械制造用钢',
            description: '用于制造各种机械零件，要求具有较高的强度、耐磨性和疲劳强度。',
            scenarios: [
              '轴类零件',
              '齿轮传动',
              '紧固件',
              '模具制造'
            ]
          },
          {
            name: '管道运输用钢',
            description: '用于输送油、气、水等介质的管道，要求具有良好的耐压性和耐腐蚀性。',
            scenarios: [
              '石油天然气管道',
              '城市供水管道',
              '化工管道',
              '蒸汽管道'
            ]
          },
          {
            name: '海洋工程用钢',
            description: '用于海上平台、船舶等海洋环境，要求具有优异的耐海水腐蚀性和抗疲劳性能。',
            scenarios: [
              '海上钻井平台',
              '船舶制造',
              '海底管道',
              '码头设施'
            ]
          }
        ]
      };
    });
  }
  
  /**
   * 获取样品展示数据
   */
  getSampleData() {
    // 尝试从服务器获取数据，如果失败则返回模拟数据
    return http.get('/uniapp/getSampleData').then((res: ResponseData) => {
      // 如果返回的数据有效则返回实际数据
      if (res && res.code == 20000) {
        return res.data;
      }
      // 否则返回模拟数据
      return [
        {
          id: 1,
          name: '无缝钢管',
          spec: 'Φ108×4.0mm',
          material: '20#钢',
          features: ['高强度', '耐压', '耐腐蚀'],
          application: '石油化工、流体输送',
          image: '/static/logo.png',
          category: 'steel-tube',
          isNew: true
        },
        {
          id: 2,
          name: '直缝焊管',
          spec: 'Φ159×5.0mm',
          material: 'Q235B',
          features: ['成本低', '易加工', '规格全'],
          application: '建筑结构、流体输送',
          image: '/static/logo.png',
          category: 'steel-tube',
          isNew: false
        },
        {
          id: 3,
          name: '方管',
          spec: '100×100×4.0mm',
          material: 'Q345B',
          features: ['截面利用率高', '便于连接', '承载能力强'],
          application: '建筑结构、机械制造',
          image: '/static/logo.png',
          category: 'rectangular',
          isNew: true
        },
        {
          id: 4,
          name: '矩形管',
          spec: '150×100×5.0mm',
          material: 'Q235B',
          features: ['截面惯性矩大', '抗弯性能好', '美观实用'],
          application: '建筑装饰、机械结构',
          image: '/static/logo.png',
          category: 'rectangular',
          isNew: false
        },
        {
          id: 5,
          name: '工字钢',
          spec: '250×118×10mm',
          material: 'Q345B',
          features: ['抗弯能力强', '截面合理', '节省材料'],
          application: '工业建筑、大跨度结构',
          image: '/static/logo.png',
          category: 'i-beam',
          isNew: false
        },
        {
          id: 6,
          name: '角钢',
          spec: '∠75×75×8mm',
          material: 'Q235B',
          features: ['承载能力强', '便于连接', '成本低'],
          application: '建筑结构、设备框架',
          image: '/static/logo.png',
          category: 'angle',
          isNew: false
        },
        {
          id: 7,
          name: '槽钢',
          spec: '[20a 200×73×7mm]',
          material: 'Q235B',
          features: ['截面形状合理', '承载能力强', '便于固定'],
          application: '建筑结构、车辆制造',
          image: '/static/logo.png',
          category: 'channel',
          isNew: false
        },
        {
          id: 8,
          name: 'H 型钢',
          spec: 'HW300×300×10×15mm',
          material: 'Q345B',
          features: ['截面模数大', '抗弯能力强', '省材料'],
          application: '高层建筑、工业厂房',
          image: '/static/logo.png',
          category: 'h-beam',
          isNew: true
        }
      ];
    }).catch(() => {
      // 网络请求失败时返回模拟数据
      return [
        {
          id: 1,
          name: '无缝钢管',
          spec: 'Φ108×4.0mm',
          material: '20#钢',
          features: ['高强度', '耐压', '耐腐蚀'],
          application: '石油化工、流体输送',
          image: '/static/logo.png',
          category: 'steel-tube',
          isNew: true
        },
        {
          id: 2,
          name: '直缝焊管',
          spec: 'Φ159×5.0mm',
          material: 'Q235B',
          features: ['成本低', '易加工', '规格全'],
          application: '建筑结构、流体输送',
          image: '/static/logo.png',
          category: 'steel-tube',
          isNew: false
        },
        {
          id: 3,
          name: '方管',
          spec: '100×100×4.0mm',
          material: 'Q345B',
          features: ['截面利用率高', '便于连接', '承载能力强'],
          application: '建筑结构、机械制造',
          image: '/static/logo.png',
          category: 'rectangular',
          isNew: true
        },
        {
          id: 4,
          name: '矩形管',
          spec: '150×100×5.0mm',
          material: 'Q235B',
          features: ['截面惯性矩大', '抗弯性能好', '美观实用'],
          application: '建筑装饰、机械结构',
          image: '/static/logo.png',
          category: 'rectangular',
          isNew: false
        },
        {
          id: 5,
          name: '工字钢',
          spec: '250×118×10mm',
          material: 'Q345B',
          features: ['抗弯能力强', '截面合理', '节省材料'],
          application: '工业建筑、大跨度结构',
          image: '/static/logo.png',
          category: 'i-beam',
          isNew: false
        },
        {
          id: 6,
          name: '角钢',
          spec: '∠75×75×8mm',
          material: 'Q235B',
          features: ['承载能力强', '便于连接', '成本低'],
          application: '建筑结构、设备框架',
          image: '/static/logo.png',
          category: 'angle',
          isNew: false
        },
        {
          id: 7,
          name: '槽钢',
          spec: '[20a 200×73×7mm]',
          material: 'Q235B',
          features: ['截面形状合理', '承载能力强', '便于固定'],
          application: '建筑结构、车辆制造',
          image: '/static/logo.png',
          category: 'channel',
          isNew: false
        },
        {
          id: 8,
          name: 'H 型钢',
          spec: 'HW300×300×10×15mm',
          material: 'Q345B',
          features: ['截面模数大', '抗弯能力强', '省材料'],
          application: '高层建筑、工业厂房',
          image: '/static/logo.png',
          category: 'h-beam',
          isNew: true
        }
      ];
    });
  }

  /**
   * 获取关于我们页面数据
   */
  getAboutData() {
    // 尝试从服务器获取数据，如果失败则返回模拟数据
    return http.get('/uniapp/getAboutData').then((res: ResponseData) => {
      // 如果返回的数据有效则返回实际数据
      if (res && res.code == 20000) {
        return res.data;
      }
      // 否则返回模拟数据
      return {
        aboutUs: '这是一个专为钢铁工厂设计的小程序，提供生产管理、数据分析和计算工具等功能，帮助工厂提高生产效率和管理水平。',
        features: [
          '生产数据统计与分析',
          '材料成本计算器',
          '设备维护提醒',
          '质量检测记录'
        ],
        contactInfo: {
          phone: '400-123-4567',
          email: 'support@steelfactory.com',
          address: '北京市朝阳区钢铁工业区 123 号'
        }
      };
    }).catch(() => {
      // 网络请求失败时返回模拟数据
      return {
        aboutUs: '这是一个专为钢铁工厂设计的小程序，提供生产管理、数据分析和计算工具等功能，帮助工厂提高生产效率和管理水平。',
        features: [
          '生产数据统计与分析',
          '材料成本计算器',
          '设备维护提醒',
          '质量检测记录'
        ],
        contactInfo: {
          phone: '400-123-4567',
          email: 'support@steelfactory.com',
          address: '北京市朝阳区钢铁工业区 123 号'
        }
      };
    });
  }
}

export default new ApiService();
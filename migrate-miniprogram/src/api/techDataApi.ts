import HttpRequest from '@/utils/request';
import type {ResponseData} from "@/types";

// 创建实例 - 使用默认配置（从 config/index.ts 读取）
const http = new HttpRequest();

/**
 * 技术资料库 API 服务
 */
class TechDataApiService {
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
}

export default new TechDataApiService();
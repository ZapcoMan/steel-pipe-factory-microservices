import HttpRequest from '@/utils/request';
import type {ResponseData} from "@/types";

// 创建实例 - 使用默认配置（从 config/index.ts 读取）
const http = new HttpRequest();

/**
 * 样品展示 API 服务
 */
class SampleApiService {
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
          name: 'H型钢',
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
          name: 'H型钢',
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
}

export default new SampleApiService();
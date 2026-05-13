import request from '@/utils/request.js';

/**
 * 获取关于我们分页数据
 */
export function fetchAboutUsPage(params) {
  return request.get('/aboutUs/selectPage', { params });
}

/**
 * 获取所有关于我们数据
 */
export function fetchAllAboutUs() {
  return request.get('/aboutUs/selectAll');
}

/**
 * 根据 ID 查询关于我们数据
 */
export function fetchAboutUsById(id) {
  return request.get('/aboutUs/selectById', { params: { id } });
}

/**
 * 添加关于我们数据
 */
export function addAboutUs(data) {
  return request.post('/aboutUs/insert', data);
}

/**
 * 更新关于我们数据
 */
export function updateAboutUs(data) {
  return request.put('/aboutUs/updateById', data);
}

/**
 * 删除关于我们数据
 */
export function deleteAboutUs(id) {
  return request.delete('/aboutUs/deleteById', { params: { id } });
}

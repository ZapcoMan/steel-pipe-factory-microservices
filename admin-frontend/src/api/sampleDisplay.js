import request from '@/utils/request.js';

/**
 * 获取样品展示分页数据
 */
export function fetchSampleDisplay(params) {
  return request.get('/sampleDisplay/selectPage', { params });
}

/**
 * 添加样品展示
 */
export function addSampleDisplay(data) {
  return request.post('/sampleDisplay/add', data);
}

/**
 * 更新样品展示
 */
export function updateSampleDisplay(data) {
  return request.put('/sampleDisplay/update', data);
}

/**
 * 删除样品展示
 */
export function deleteSampleDisplay(id) {
  return request.delete('/sampleDisplay/delete/' + id);
}

/**
 * 批量删除样品展示
 */
export function deleteSampleDisplayBatch(ids) {
  return request.delete('/sampleDisplay/deleteBatch', { data: ids });
}

import request from "@/utils/request.js";

// 分页查询特色企业
export const fetchFeaturedCompanies = (params) => {
    return request.get('/featuredCompany/selectPage', { params })
}

// 新增特色企业
export const addFeaturedCompany = (data) => {
    return request.post('/featuredCompany/add', data)
}

// 修改特色企业
export const updateFeaturedCompany = (data) => {
    return request.put('/featuredCompany/update', data)
}

// 删除单个特色企业
export const deleteFeaturedCompany = (id) => {
    return request.delete(`/featuredCompany/delete/${id}`)
}

// 批量删除特色企业
export const deleteFeaturedCompanies = (rows) => {
    return request.delete('/featuredCompany/deleteBatch', { data: rows })
}
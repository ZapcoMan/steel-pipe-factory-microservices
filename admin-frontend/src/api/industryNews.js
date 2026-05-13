import request from "@/utils/request.js";

// 分页查询行业资讯
export const fetchIndustryNews = (params) => {
    return request.get('/obtainInformation/selectPage', { params })
}

// 新增行业资讯
export const addIndustryNews = (data) => {
    return request.post('/obtainInformation/add', data)
}

// 修改行业资讯
export const updateIndustryNews = (data) => {
    return request.put('/obtainInformation/update', data)
}

// 删除单个行业资讯
export const deleteIndustryNews = (id) => {
    return request.delete(`/obtainInformation/delete/${id}`)
}

// 批量删除行业资讯
export const deleteIndustryNewsBatch = (rows) => {
    return request.delete('/obtainInformation/deleteBatch', { data: rows })
}
import request from '@/utils/request'

/**
 * 查询企业基础信息列表
 * @param query 查询参数
 * @returns Promise
 */
export function listCompany(query) {
  return request({
    url: '/portal/base/company/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询企业基础信息详细
 * @param infoId 信息ID
 * @returns Promise
 */
export function getCompany(infoId) {
  return request({
    url: '/portal/base/company/' + infoId,
    method: 'get'
  })
}

/**
 * 新增企业基础信息
 * @param data 企业信息数据
 * @returns Promise
 */
export function addCompany(data) {
  return request({
    url: '/portal/base/company',
    method: 'post',
    data: data
  })
}

/**
 * 修改企业基础信息
 * @param data 企业信息数据
 * @returns Promise
 */
export function updateCompany(data) {
  return request({
    url: '/portal/base/company',
    method: 'put',
    data: data
  })
}

/**
 * 删除企业基础信息
 * @param infoId 信息ID
 * @returns Promise
 */
export function delCompany(infoId) {
  return request({
    url: '/portal/base/company/' + infoId,
    method: 'delete'
  })
}

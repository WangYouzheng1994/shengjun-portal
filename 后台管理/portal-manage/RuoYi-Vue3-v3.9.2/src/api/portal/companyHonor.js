import request from '@/utils/request'

/**
 * 查询企业荣誉墙列表
 * @param query 查询参数
 * @returns Promise
 */
export function listCompanyHonor(query) {
  return request({
    url: '/portal/base/honor/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询企业荣誉墙详细
 * @param honorId 荣誉ID
 * @returns Promise
 */
export function getCompanyHonor(honorId) {
  return request({
    url: '/portal/base/honor/' + honorId,
    method: 'get'
  })
}

/**
 * 新增企业荣誉墙
 * @param data 荣誉数据
 * @returns Promise
 */
export function addCompanyHonor(data) {
  return request({
    url: '/portal/base/honor',
    method: 'post',
    data: data
  })
}

/**
 * 修改企业荣誉墙
 * @param data 荣誉数据
 * @returns Promise
 */
export function updateCompanyHonor(data) {
  return request({
    url: '/portal/base/honor',
    method: 'put',
    data: data
  })
}

/**
 * 删除企业荣誉墙
 * @param honorId 荣誉ID
 * @returns Promise
 */
export function delCompanyHonor(honorId) {
  return request({
    url: '/portal/base/honor/' + honorId,
    method: 'delete'
  })
}

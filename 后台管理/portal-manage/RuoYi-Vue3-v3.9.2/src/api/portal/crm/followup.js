import request from '@/utils/request'

/**
 * 查询跟进记录列表
 * @param query 查询参数
 * @returns Promise
 */
export function listFollowUp(query) {
  return request({
    url: '/portal/crm/followup/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询跟进记录详细
 * @param followId 跟进ID
 * @returns Promise
 */
export function getFollowUp(followId) {
  return request({
    url: '/portal/crm/followup/' + followId,
    method: 'get'
  })
}

/**
 * 根据客户ID查询跟进记录
 * @param customerId 客户ID
 * @param query 分页参数
 * @returns Promise
 */
export function listFollowUpByCustomer(customerId, query) {
  return request({
    url: '/portal/crm/followup/customer/' + customerId,
    method: 'get',
    params: query
  })
}

/**
 * 新增跟进记录
 * @param data 跟进数据
 * @returns Promise
 */
export function addFollowUp(data) {
  return request({
    url: '/portal/crm/followup',
    method: 'post',
    data: data
  })
}

/**
 * 修改跟进记录
 * @param data 跟进数据
 * @returns Promise
 */
export function updateFollowUp(data) {
  return request({
    url: '/portal/crm/followup',
    method: 'put',
    data: data
  })
}

/**
 * 删除跟进记录
 * @param followIds 跟进ID数组（逗号分隔）
 * @returns Promise
 */
export function delFollowUp(followIds) {
  return request({
    url: '/portal/crm/followup/' + followIds,
    method: 'delete'
  })
}

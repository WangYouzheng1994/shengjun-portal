import request from '@/utils/request'

/**
 * 查询客户列表
 * @param query 查询参数
 * @returns Promise
 */
export function listCustomer(query) {
  return request({
    url: '/portal/crm/customer/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询客户详细
 * @param customerId 客户ID
 * @returns Promise
 */
export function getCustomer(customerId) {
  return request({
    url: '/portal/crm/customer/' + customerId,
    method: 'get'
  })
}

/**
 * 新增客户
 * @param data 客户数据
 * @returns Promise
 */
export function addCustomer(data) {
  return request({
    url: '/portal/crm/customer',
    method: 'post',
    data: data
  })
}

/**
 * 修改客户
 * @param data 客户数据
 * @returns Promise
 */
export function updateCustomer(data) {
  return request({
    url: '/portal/crm/customer',
    method: 'put',
    data: data
  })
}

/**
 * 删除客户
 * @param customerIds 客户ID数组（逗号分隔）
 * @returns Promise
 */
export function delCustomer(customerIds) {
  return request({
    url: '/portal/crm/customer/' + customerIds,
    method: 'delete'
  })
}

/**
 * 导出客户
 * @param query 查询参数
 * @returns Promise
 */
export function exportCustomer(query) {
  return request({
    url: '/portal/crm/customer/export',
    method: 'post',
    params: query
  })
}

/**
 * 查询所有客户（用于下拉选择）
 * @returns Promise
 */
export function listAllCustomer() {
  return request({
    url: '/portal/crm/customer/list',
    method: 'get',
    params: { pageNum: 1, pageSize: 1000 }
  })
}

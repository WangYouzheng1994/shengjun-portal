import request from '@/utils/request'

/**
 * 查询企业下的所有办公点
 * @param infoId 企业信息ID
 * @returns Promise
 */
export function listOfficeLocation(infoId) {
  return request({
    url: '/portal/base/company/office/list/' + infoId,
    method: 'get'
  })
}

/**
 * 获取办公点详细信息
 * @param locationId 办公点ID
 * @returns Promise
 */
export function getOfficeLocation(locationId) {
  return request({
    url: '/portal/base/company/office/' + locationId,
    method: 'get'
  })
}

/**
 * 新增企业办公点
 * @param data 办公点数据
 * @returns Promise
 */
export function addOfficeLocation(data) {
  return request({
    url: '/portal/base/company/office',
    method: 'post',
    data: data
  })
}

/**
 * 修改企业办公点
 * @param data 办公点数据
 * @returns Promise
 */
export function updateOfficeLocation(data) {
  return request({
    url: '/portal/base/company/office',
    method: 'put',
    data: data
  })
}

/**
 * 删除企业办公点
 * @param locationIds 办公点ID数组
 * @returns Promise
 */
export function delOfficeLocation(locationIds) {
  return request({
    url: '/portal/base/company/office/' + locationIds,
    method: 'delete'
  })
}

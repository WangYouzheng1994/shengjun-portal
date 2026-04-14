import request from '@/utils/request'

/**
 * 查询标签列表
 * @param query 查询参数
 * @returns Promise
 */
export function listTag(query) {
  return request({
    url: '/portal/crm/tag/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询标签详细
 * @param tagId 标签ID
 * @returns Promise
 */
export function getTag(tagId) {
  return request({
    url: '/portal/crm/tag/' + tagId,
    method: 'get'
  })
}

/**
 * 新增标签
 * @param data 标签数据
 * @returns Promise
 */
export function addTag(data) {
  return request({
    url: '/portal/crm/tag',
    method: 'post',
    data: data
  })
}

/**
 * 修改标签
 * @param data 标签数据
 * @returns Promise
 */
export function updateTag(data) {
  return request({
    url: '/portal/crm/tag',
    method: 'put',
    data: data
  })
}

/**
 * 删除标签
 * @param tagIds 标签ID数组（逗号分隔）
 * @returns Promise
 */
export function delTag(tagIds) {
  return request({
    url: '/portal/crm/tag/' + tagIds,
    method: 'delete'
  })
}

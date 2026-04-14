import request from '@/utils/request'

/**
 * 查询属性模板列表
 * @param query 查询参数
 * @returns Promise
 */
export function listTemplate(query) {
  return request({
    url: '/portal/product/attrTemplate/list',
    method: 'get',
    params: query
  })
}

/**
 * 获取属性模板详情（包含属性定义列表）
 * @param templateId 模板ID
 * @returns Promise
 */
export function getTemplate(templateId) {
  return request({
    url: '/portal/product/attrTemplate/' + templateId,
    method: 'get'
  })
}

/**
 * 新增属性模板（同时保存属性定义）
 * @param data 模板数据（包含attrDefs属性定义数组）
 * @returns Promise
 */
export function addTemplate(data) {
  return request({
    url: '/portal/product/attrTemplate',
    method: 'post',
    data: data
  })
}

/**
 * 修改属性模板
 * @param data 模板数据
 * @returns Promise
 */
export function updateTemplate(data) {
  return request({
    url: '/portal/product/attrTemplate',
    method: 'put',
    data: data
  })
}

/**
 * 删除属性模板
 * @param templateIds 模板ID数组（逗号分隔）
 * @returns Promise
 */
export function delTemplate(templateIds) {
  return request({
    url: '/portal/product/attrTemplate/' + templateIds,
    method: 'delete'
  })
}

/**
 * 导出属性模板
 * @param query 查询参数
 * @returns Promise
 */
export function exportTemplate(query) {
  return request({
    url: '/portal/product/attrTemplate/export',
    method: 'post',
    params: query
  })
}

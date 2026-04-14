import request from '@/utils/request'

/**
 * 查询模板列表
 * @param query 查询参数
 * @returns Promise
 */
export function listTemplate(query) {
  return request({
    url: '/portal/crm/template/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询模板详细
 * @param templateId 模板ID
 * @returns Promise
 */
export function getTemplate(templateId) {
  return request({
    url: '/portal/crm/template/' + templateId,
    method: 'get'
  })
}

/**
 * 新增模板
 * @param data 模板数据
 * @returns Promise
 */
export function addTemplate(data) {
  return request({
    url: '/portal/crm/template',
    method: 'post',
    data: data
  })
}

/**
 * 修改模板
 * @param data 模板数据
 * @returns Promise
 */
export function updateTemplate(data) {
  return request({
    url: '/portal/crm/template',
    method: 'put',
    data: data
  })
}

/**
 * 删除模板
 * @param templateIds 模板ID数组（逗号分隔）
 * @returns Promise
 */
export function delTemplate(templateIds) {
  return request({
    url: '/portal/crm/template/' + templateIds,
    method: 'delete'
  })
}

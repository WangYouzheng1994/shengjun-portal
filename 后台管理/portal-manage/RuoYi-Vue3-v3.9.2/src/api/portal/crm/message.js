import request from '@/utils/request'

/**
 * 查询消息通知列表
 * @param query 查询参数
 * @returns Promise
 */
export function listMessage(query) {
  return request({
    url: '/portal/crm/message/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询消息通知详细
 * @param messageId 消息通知ID
 * @returns Promise
 */
export function getMessage(messageId) {
  return request({
    url: '/portal/crm/message/' + messageId,
    method: 'get'
  })
}

/**
 * 新增消息通知（前台提交）
 * @param data 消息通知数据
 * @returns Promise
 */
export function addMessage(data) {
  return request({
    url: '/portal/crm/message',
    method: 'post',
    data: data
  })
}

/**
 * 回复消息通知
 * @param data 消息通知数据（包含messageId、replyContent）
 * @returns Promise
 */
export function replyMessage(data) {
  return request({
    url: '/portal/crm/message/reply',
    method: 'put',
    data: data
  })
}

/**
 * 修改消息通知
 * @param data 消息通知数据
 * @returns Promise
 */
export function updateMessage(data) {
  return request({
    url: '/portal/crm/message',
    method: 'put',
    data: data
  })
}

/**
 * 删除消息通知
 * @param messageIds 消息通知ID数组（逗号分隔）
 * @returns Promise
 */
export function delMessage(messageIds) {
  return request({
    url: '/portal/crm/message/' + messageIds,
    method: 'delete'
  })
}

/**
 * 导出消息通知
 * @param query 查询参数
 * @returns Promise
 */
export function exportMessage(query) {
  return request({
    url: '/portal/crm/message/export',
    method: 'post',
    params: query
  })
}

/**
 * 将消息通知转换为客户
 * @param messageId 消息通知ID
 * @param data 客户补充信息（公司名称、职位等）
 * @returns Promise
 */
export function convertMessageToCustomer(messageId, data) {
  return request({
    url: '/portal/crm/message/convert/' + messageId,
    method: 'post',
    data: data
  })
}

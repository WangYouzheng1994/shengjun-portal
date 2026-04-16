import request from '@/utils/request'

export function getFeishuConfig() {
  return request({
    url: '/message/channel/feishu/config',
    method: 'get'
  })
}

export function addFeishuConfig(data) {
  return request({
    url: '/message/channel/feishu/config',
    method: 'post',
    data: data
  })
}

export function updateFeishuConfig(data) {
  return request({
    url: '/message/channel/feishu/config',
    method: 'put',
    data: data
  })
}

export function delFeishuConfig(configId) {
  return request({
    url: '/message/channel/feishu/config/' + configId,
    method: 'delete'
  })
}
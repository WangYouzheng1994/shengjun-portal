import request from '@/utils/request'

export function getWeComConfig() {
  return request({
    url: '/message/channel/wecom/config',
    method: 'get'
  })
}

export function addWeComConfig(data) {
  return request({
    url: '/message/channel/wecom/config',
    method: 'post',
    data: data
  })
}

export function updateWeComConfig(data) {
  return request({
    url: '/message/channel/wecom/config',
    method: 'put',
    data: data
  })
}

export function delWeComConfig(configId) {
  return request({
    url: '/message/channel/wecom/config/' + configId,
    method: 'delete'
  })
}
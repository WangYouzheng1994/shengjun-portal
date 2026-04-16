import request from '@/utils/request'

export function getDingtalkConfig() {
  return request({
    url: '/message/channel/dingtalk/config',
    method: 'get'
  })
}

export function addDingtalkConfig(data) {
  return request({
    url: '/message/channel/dingtalk/config',
    method: 'post',
    data: data
  })
}

export function updateDingtalkConfig(data) {
  return request({
    url: '/message/channel/dingtalk/config',
    method: 'put',
    data: data
  })
}

export function delDingtalkConfig(configId) {
  return request({
    url: '/message/channel/dingtalk/config/' + configId,
    method: 'delete'
  })
}
import request from '@/utils/request'

export function getEmailConfig() {
  return request({
    url: '/message/channel/email/config',
    method: 'get'
  })
}

export function addEmailConfig(data) {
  return request({
    url: '/message/channel/email/config',
    method: 'post',
    data: data
  })
}

export function updateEmailConfig(data) {
  return request({
    url: '/message/channel/email/config',
    method: 'put',
    data: data
  })
}

export function delEmailConfig(configId) {
  return request({
    url: '/message/channel/email/config/' + configId,
    method: 'delete'
  })
}
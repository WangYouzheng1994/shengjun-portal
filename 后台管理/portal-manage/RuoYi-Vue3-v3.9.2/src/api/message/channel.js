import request from '@/utils/request'

export function listChannel(query) {
  return request({
    url: '/message/channel/list',
    method: 'get',
    params: query
  })
}

export function getChannel(channelId) {
  return request({
    url: '/message/channel/' + channelId,
    method: 'get'
  })
}

export function getChannelByType(channelType) {
  return request({
    url: '/message/channel/type/' + channelType,
    method: 'get'
  })
}

export function addChannel(data) {
  return request({
    url: '/message/channel',
    method: 'post',
    data: data
  })
}

export function updateChannel(data) {
  return request({
    url: '/message/channel',
    method: 'put',
    data: data
  })
}

export function delChannel(channelIds) {
  return request({
    url: '/message/channel/' + channelIds,
    method: 'delete'
  })
}

export function exportChannel(query) {
  return request({
    url: '/message/channel/export',
    method: 'post',
    responseType: 'blob',
    params: query
  })
}

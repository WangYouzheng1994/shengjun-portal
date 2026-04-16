import request from '@/utils/request'

export function listSendLog(query) {
  return request({
    url: '/message/sendLog/list',
    method: 'get',
    params: query
  })
}

export function getSendLog(logId) {
  return request({
    url: '/message/sendLog/' + logId,
    method: 'get'
  })
}

export function delSendLog(logIds) {
  return request({
    url: '/message/sendLog/' + logIds,
    method: 'delete'
  })
}

export function exportSendLog(query) {
  return request({
    url: '/message/sendLog/export',
    method: 'post',
    responseType: 'blob',
    params: query
  })
}

export function sendText(data) {
  return request({
    url: '/message/send/text',
    method: 'post',
    data: data
  })
}

export function sendRichText(data) {
  return request({
    url: '/message/send/richText',
    method: 'post',
    data: data
  })
}

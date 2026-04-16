import request from '@/utils/request'

export function listTemplate(query) {
  return request({
    url: '/message/template/list',
    method: 'get',
    params: query
  })
}

export function getTemplate(templateId) {
  return request({
    url: '/message/template/' + templateId,
    method: 'get'
  })
}

export function addTemplate(data) {
  return request({
    url: '/message/template',
    method: 'post',
    data: data
  })
}

export function updateTemplate(data) {
  return request({
    url: '/message/template',
    method: 'put',
    data: data
  })
}

export function delTemplate(templateId) {
  return request({
    url: '/message/template/' + templateId,
    method: 'delete'
  })
}

export function exportTemplate(query) {
  return request({
    url: '/message/template/export',
    method: 'post',
    responseType: 'blob',
    params: query
  })
}
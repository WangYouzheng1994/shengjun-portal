import request from '@/utils/request'

/**
 * 查询公告列表
 * @param query 查询参数
 * @returns Promise
 */
export function listNotice(query) {
  return request({
    url: '/portal/notice/notice/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询公告详细
 * @param noticeId 公告ID
 * @returns Promise
 */
export function getNotice(noticeId) {
  return request({
    url: '/portal/notice/notice/' + noticeId,
    method: 'get'
  })
}

/**
 * 新增公告
 * @param data 公告数据
 * @returns Promise
 */
export function addNotice(data) {
  return request({
    url: '/portal/notice/notice',
    method: 'post',
    data: data
  })
}

/**
 * 修改公告
 * @param data 公告数据
 * @returns Promise
 */
export function updateNotice(data) {
  return request({
    url: '/portal/notice/notice',
    method: 'put',
    data: data
  })
}

/**
 * 删除公告
 * @param noticeId 公告ID
 * @returns Promise
 */
export function delNotice(noticeId) {
  return request({
    url: '/portal/notice/notice/' + noticeId,
    method: 'delete'
  })
}

/**
 * 导出公告
 * @param query 查询参数
 * @returns Promise
 */
export function exportNotice(query) {
  return request({
    url: '/portal/notice/notice/export',
    method: 'post',
    params: query
  })
}

/**
 * 发布公告
 * @param data 公告数据（包含noticeId）
 * @returns Promise
 */
export function publishNotice(data) {
  return request({
    url: '/portal/notice/notice/publish',
    method: 'post',
    data: data
  })
}

/**
 * 停用公告
 * @param data 公告数据（包含noticeId）
 * @returns Promise
 */
export function offlineNotice(data) {
  return request({
    url: '/portal/notice/notice/offline',
    method: 'post',
    data: data
  })
}

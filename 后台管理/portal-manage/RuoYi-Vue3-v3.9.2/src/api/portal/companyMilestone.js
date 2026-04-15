import request from '@/utils/request'

/**
 * 查询企业发展历程里程碑列表
 *
 * @param query 查询参数（必须包含companyId）
 * @returns Promise
 */
export function listMilestone(query) {
  return request({
    url: '/portal/base/milestone/list',
    method: 'get',
    params: query
  })
}

/**
 * 获取里程碑详情
 *
 * @param milestoneId 里程碑ID
 * @returns Promise
 */
export function getMilestone(milestoneId) {
  return request({
    url: '/portal/base/milestone/' + milestoneId,
    method: 'get'
  })
}

/**
 * 新增里程碑
 *
 * @param data 里程碑数据（必须包含companyId）
 * @returns Promise
 */
export function addMilestone(data) {
  return request({
    url: '/portal/base/milestone',
    method: 'post',
    data: data
  })
}

/**
 * 修改里程碑
 *
 * @param data 里程碑数据
 * @returns Promise
 */
export function updateMilestone(data) {
  return request({
    url: '/portal/base/milestone',
    method: 'put',
    data: data
  })
}

/**
 * 删除里程碑
 *
 * @param milestoneId 里程碑ID或ID数组
 * @returns Promise
 */
export function delMilestone(milestoneId) {
  return request({
    url: '/portal/base/milestone/' + milestoneId,
    method: 'delete'
  })
}

/**
 * 导出里程碑数据
 *
 * @param query 查询参数（必须包含companyId）
 * @returns Promise (Blob)
 */
export function exportMilestone(query) {
  return request({
    url: '/portal/base/milestone/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

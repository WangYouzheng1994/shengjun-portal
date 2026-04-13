import request from '@/utils/request'

/**
 * 获取门户数据概览
 * @returns Promise
 */
export function getDashboardOverview() {
  return request({
    url: '/portal/dashboard/overview',
    method: 'get'
  })
}

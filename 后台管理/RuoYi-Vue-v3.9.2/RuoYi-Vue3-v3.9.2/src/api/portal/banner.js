import request from '@/utils/request'

/**
 * 查询轮播图配置列表
 * @param query 查询参数
 * @returns Promise
 */
export function listBanner(query) {
  return request({
    url: '/portal/base/banner/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询轮播图配置详细
 * @param bannerId 轮播图ID
 * @returns Promise
 */
export function getBanner(bannerId) {
  return request({
    url: '/portal/base/banner/' + bannerId,
    method: 'get'
  })
}

/**
 * 新增轮播图配置
 * @param data 轮播图数据
 * @returns Promise
 */
export function addBanner(data) {
  return request({
    url: '/portal/base/banner',
    method: 'post',
    data: data
  })
}

/**
 * 修改轮播图配置
 * @param data 轮播图数据
 * @returns Promise
 */
export function updateBanner(data) {
  return request({
    url: '/portal/base/banner',
    method: 'put',
    data: data
  })
}

/**
 * 删除轮播图配置
 * @param bannerId 轮播图ID
 * @returns Promise
 */
export function delBanner(bannerId) {
  return request({
    url: '/portal/base/banner/' + bannerId,
    method: 'delete'
  })
}

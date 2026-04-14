import request from '@/utils/request'

/**
 * 查询品牌列表
 * @param query 查询参数
 * @returns Promise
 */
export function listBrand(query) {
  return request({
    url: '/portal/product/brand/list',
    method: 'get',
    params: query
  })
}

/**
 * 获取品牌详情
 * @param brandId 品牌ID
 * @returns Promise
 */
export function getBrand(brandId) {
  return request({
    url: '/portal/product/brand/' + brandId,
    method: 'get'
  })
}

/**
 * 新增品牌
 * @param data 品牌数据
 * @returns Promise
 */
export function addBrand(data) {
  return request({
    url: '/portal/product/brand',
    method: 'post',
    data: data
  })
}

/**
 * 修改品牌
 * @param data 品牌数据
 * @returns Promise
 */
export function updateBrand(data) {
  return request({
    url: '/portal/product/brand',
    method: 'put',
    data: data
  })
}

/**
 * 删除品牌
 * @param brandIds 品牌ID数组（逗号分隔）
 * @returns Promise
 */
export function delBrand(brandIds) {
  return request({
    url: '/portal/product/brand/' + brandIds,
    method: 'delete'
  })
}

/**
 * 导出品牌
 * @param query 查询参数
 * @returns Promise
 */
export function exportBrand(query) {
  return request({
    url: '/portal/product/brand/export',
    method: 'post',
    params: query
  })
}

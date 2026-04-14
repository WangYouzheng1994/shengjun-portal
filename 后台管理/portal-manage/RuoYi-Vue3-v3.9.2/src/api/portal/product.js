import request from '@/utils/request'

/**
 * 查询产品列表
 * @param query 查询参数
 * @returns Promise
 */
export function listProduct(query) {
  return request({
    url: '/portal/product/info/list',
    method: 'get',
    params: query
  })
}

/**
 * 获取产品详情（包含属性值和SKU）
 * @param productId 产品ID
 * @returns Promise
 */
export function getProduct(productId) {
  return request({
    url: '/portal/product/info/' + productId,
    method: 'get'
  })
}

/**
 * 新增产品（同时保存属性值和SKU）
 * @param data 产品数据（包含attrValues属性值数组和skus数组）
 * @returns Promise
 */
export function addProduct(data) {
  return request({
    url: '/portal/product/info',
    method: 'post',
    data: data
  })
}

/**
 * 修改产品
 * @param data 产品数据
 * @returns Promise
 */
export function updateProduct(data) {
  return request({
    url: '/portal/product/info',
    method: 'put',
    data: data
  })
}

/**
 * 删除产品
 * @param productIds 产品ID数组（逗号分隔）
 * @returns Promise
 */
export function delProduct(productIds) {
  return request({
    url: '/portal/product/info/' + productIds,
    method: 'delete'
  })
}

/**
 * 上架/下架产品
 * @param productId 产品ID
 * @param status 状态（0下架 1上架）
 * @returns Promise
 */
export function updateProductStatus(productId, status) {
  return request({
    url: '/portal/product/info/status/' + productId + '/' + status,
    method: 'put'
  })
}

/**
 * 导出产品
 * @param query 查询参数
 * @returns Promise
 */
export function exportProduct(query) {
  return request({
    url: '/portal/product/info/export',
    method: 'post',
    params: query
  })
}

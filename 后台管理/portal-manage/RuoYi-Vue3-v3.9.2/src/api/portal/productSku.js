import request from '@/utils/request'

/**
 * 根据产品ID查询SKU列表
 * @param productId 产品ID
 * @returns Promise
 */
export function listSkuByProductId(productId) {
  return request({
    url: '/portal/product/sku/list/' + productId,
    method: 'get'
  })
}

/**
 * 获取SKU详情
 * @param skuId SKU ID
 * @returns Promise
 */
export function getSku(skuId) {
  return request({
    url: '/portal/product/sku/' + skuId,
    method: 'get'
  })
}

/**
 * 新增SKU
 * @param data SKU数据
 * @returns Promise
 */
export function addSku(data) {
  return request({
    url: '/portal/product/sku',
    method: 'post',
    data: data
  })
}

/**
 * 修改SKU
 * @param data SKU数据
 * @returns Promise
 */
export function updateSku(data) {
  return request({
    url: '/portal/product/sku',
    method: 'put',
    data: data
  })
}

/**
 * 删除SKU
 * @param skuIds SKU ID数组（逗号分隔）
 * @returns Promise
 */
export function delSku(skuIds) {
  return request({
    url: '/portal/product/sku/' + skuIds,
    method: 'delete'
  })
}

import request from '@/utils/request'

/**
 * 查询产品分类列表
 * @param query 查询参数
 * @returns Promise
 */
export function listCategory(query) {
  return request({
    url: '/portal/product/category/list',
    method: 'get',
    params: query
  })
}

/**
 * 获取产品分类树形结构
 * @param query 查询参数
 * @returns Promise
 */
export function listCategoryTree(query) {
  return request({
    url: '/portal/product/category/tree',
    method: 'get',
    params: query
  })
}

/**
 * 获取产品分类下拉树列表
 * @param query 查询参数
 * @returns Promise
 */
export function categoryTreeSelect(query) {
  return request({
    url: '/portal/product/category/treeselect',
    method: 'get',
    params: query
  })
}

/**
 * 查询产品分类详情
 * @param categoryId 分类ID
 * @returns Promise
 */
export function getCategory(categoryId) {
  return request({
    url: '/portal/product/category/' + categoryId,
    method: 'get'
  })
}

/**
 * 新增产品分类
 * @param data 分类数据
 * @returns Promise
 */
export function addCategory(data) {
  return request({
    url: '/portal/product/category',
    method: 'post',
    data: data
  })
}

/**
 * 修改产品分类
 * @param data 分类数据
 * @returns Promise
 */
export function updateCategory(data) {
  return request({
    url: '/portal/product/category',
    method: 'put',
    data: data
  })
}

/**
 * 删除产品分类
 * @param categoryIds 分类ID数组（逗号分隔）
 * @returns Promise
 */
export function delCategory(categoryIds) {
  return request({
    url: '/portal/product/category/' + categoryIds,
    method: 'delete'
  })
}

/**
 * 导出产品分类
 * @param query 查询参数
 * @returns Promise
 */
export function exportCategory(query) {
  return request({
    url: '/portal/product/category/export',
    method: 'post',
    params: query
  })
}

import request from '@/utils/request'

/**
 * 查询文章分类列表
 * @param query 查询参数
 * @returns Promise
 */
export function listCategory(query) {
  return request({
    url: '/portal/article/category/list',
    method: 'get',
    params: query
  })
}

/**
 * 获取文章分类下拉树列表
 * @param query 查询参数
 * @returns Promise
 */
export function categoryTreeSelect(query) {
  return request({
    url: '/portal/article/category/treeselect',
    method: 'get',
    params: query
  })
}

/**
 * 查询文章分类详细
 * @param categoryId 分类ID
 * @returns Promise
 */
export function getCategory(categoryId) {
  return request({
    url: '/portal/article/category/' + categoryId,
    method: 'get'
  })
}

/**
 * 新增文章分类
 * @param data 分类数据
 * @returns Promise
 */
export function addCategory(data) {
  return request({
    url: '/portal/article/category',
    method: 'post',
    data: data
  })
}

/**
 * 修改文章分类
 * @param data 分类数据
 * @returns Promise
 */
export function updateCategory(data) {
  return request({
    url: '/portal/article/category',
    method: 'put',
    data: data
  })
}

/**
 * 删除文章分类
 * @param categoryId 分类ID
 * @returns Promise
 */
export function delCategory(categoryId) {
  return request({
    url: '/portal/article/category/' + categoryId,
    method: 'delete'
  })
}

/**
 * 导出文章分类
 * @param query 查询参数
 * @returns Promise
 */
export function exportCategory(query) {
  return request({
    url: '/portal/article/category/export',
    method: 'post',
    params: query
  })
}

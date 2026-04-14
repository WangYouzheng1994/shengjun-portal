import request from '@/utils/request'

/**
 * 查询文章列表
 * @param query 查询参数
 * @returns Promise
 */
export function listArticle(query) {
  return request({
    url: '/portal/article/article/list',
    method: 'get',
    params: query
  })
}

/**
 * 查询文章详细
 * @param articleId 文章ID
 * @returns Promise
 */
export function getArticle(articleId) {
  return request({
    url: '/portal/article/article/' + articleId,
    method: 'get'
  })
}

/**
 * 新增文章
 * @param data 文章数据
 * @returns Promise
 */
export function addArticle(data) {
  return request({
    url: '/portal/article/article',
    method: 'post',
    data: data
  })
}

/**
 * 修改文章
 * @param data 文章数据
 * @returns Promise
 */
export function updateArticle(data) {
  return request({
    url: '/portal/article/article',
    method: 'put',
    data: data
  })
}

/**
 * 删除文章
 * @param articleId 文章ID
 * @returns Promise
 */
export function delArticle(articleId) {
  return request({
    url: '/portal/article/article/' + articleId,
    method: 'delete'
  })
}

/**
 * 导出文章
 * @param query 查询参数
 * @returns Promise
 */
export function exportArticle(query) {
  return request({
    url: '/portal/article/article/export',
    method: 'post',
    params: query
  })
}

/**
 * 发布文章
 * @param data 文章数据（包含articleId）
 * @returns Promise
 */
export function publishArticle(data) {
  return request({
    url: '/portal/article/article/publish',
    method: 'post',
    data: data
  })
}

/**
 * 下架文章
 * @param data 文章数据（包含articleId）
 * @returns Promise
 */
export function offlineArticle(data) {
  return request({
    url: '/portal/article/article/offline',
    method: 'post',
    data: data
  })
}

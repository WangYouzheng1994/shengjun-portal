/**
 * Portal Service - Vue3 + Axios 签名拦截器
 * 
 * 用于在Vue3项目中自动为所有请求添加签名
 * 支持按需签名（仅对需要签名的接口自动签名）
 * 
 * @version 1.0.0
 * @author 王有政
 */

import axios from 'axios';
import { ElMessage } from 'element-plus';

// 引入签名工具（根据实际路径调整）
import PortalSign from './portal-sign';

/**
 * 需要签名的接口路径列表（正则表达式）
 * 匹配规则：所有 /open/* 路径下的接口都需要签名
 */
const SIGN_REQUIRED_PATTERNS = [
    /^\/open\//,           // 所有开放接口
    /^\/api\/secure\//,    // 安全接口（如需要）
];

/**
 * 不需要签名的白名单路径
 * 这些接口即使匹配了上面的规则也不签名
 */
const SIGN_EXCLUDE_PATTERNS = [
    /^\/common\//,         // 公共接口（健康检查等）
    /^\/portal\/(article|product|banner|notice|company)\//,  // 公开读接口
    /^\/login/,            // 登录接口
    /^\/register/,         // 注册接口
    /^\/captcha/,          // 验证码接口
];

/**
 * 判断URL是否需要签名
 * 
 * @param {string} url - 请求URL
 * @param {string} method - HTTP方法
 * @returns {boolean} 是否需要签名
 */
function isSignRequired(url, method) {
    // GET/HEAD/OPTIONS 请求默认不需要签名（除非明确配置）
    if (['GET', 'HEAD', 'OPTIONS'].includes(method.toUpperCase())) {
        return false;
    }

    // 检查是否在排除列表中
    for (const pattern of SIGN_EXCLUDE_PATTERNS) {
        if (pattern.test(url)) {
            return false;
        }
    }

    // 检查是否在需要签名列表中
    for (const pattern of SIGN_REQUIRED_PATTERNS) {
        if (pattern.test(url)) {
            return true;
        }
    }

    // 默认不签名（安全优先）
    return false;
}

/**
 * 创建带签名功能的Axios实例
 * 
 * @param {Object} config - Axios配置项
 * @returns {AxiosInstance} 配置好的Axios实例
 * 
 * @example
 * // 在 main.js 中初始化
 * import { createPortalService } from '@/utils/portal-sign-axios';
 * 
 * const portalApi = createPortalService({
 *   baseURL: process.env.VUE_APP_PORTAL_SERVICE_URL || 'http://localhost:8081',
 *   timeout: 10000,
 * });
 * 
 * export default portalApi;
 */
export function createPortalService(config = {}) {
    const instance = axios.create({
        baseURL: config.baseURL || '',
        timeout: config.timeout || 10000,
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
        },
        ...config,
    });

    /**
     * 请求拦截器：自动添加签名头
     */
    instance.interceptors.request.use(
        function(config) {
            const url = config.url || '';
            const method = config.method || 'GET';
            
            if (isSignRequired(url, method)) {
                try {
                    // 提取URL路径（去掉baseURL和查询参数）
                    let pathname = url.split('?')[0].split('#')[0];
                    if (!pathname.startsWith('/')) {
                        pathname = '/' + pathname;
                    }
                    
                    // 获取请求体（用于签名计算）
                    let body = null;
                    if (config.data && typeof config.data === 'string') {
                        body = config.data;
                    } else if (config.data && typeof config.data === 'object') {
                        body = JSON.stringify(config.data);
                    }
                    
                    // 生成签名
                    const signResult = PortalSign.sign(method, pathname, body);
                    
                    // 添加到请求头
                    config.headers['X-Timestamp'] = signResult.timestamp.toString();
                    config.headers['X-Signature'] = signResult.signature;
                } catch (error) {
                    console.error('[PortalSign] 签名失败:', error.message);
                    
                    // 开发环境提示错误，生产环境静默处理
                    if (process.env.NODE_ENV === 'development') {
                        ElMessage.error('请求签名失败：' + error.message);
                    }
                    
                    // 可以选择抛出错误阻止请求发送
                    // return Promise.reject(error);
                }
            }
            
            return config;
        },
        function(error) {
            return Promise.reject(error);
        }
    );

    /**
     * 响应拦截器：统一处理签名相关错误
     */
    instance.interceptors.response.use(
        function(response) {
            // 正常响应，直接返回
            return response;
        },
        function(error) {
            const response = error.response;
            
            if (response) {
                switch (response.status) {
                    case 401:
                        // 签名验证失败或认证失败
                        if (response.headers['x-signature-required']) {
                            console.error('[PortalSign] 签名验证失败:', response.data);
                            ElMessage.error('请求签名无效，请检查客户端版本');
                        } else {
                            ElMessage.error('身份验证已过期，请重新登录');
                        }
                        break;
                        
                    case 429:
                        // 请求频率超限
                        ElMessage.warning(response.data?.msg || '访问过于频繁，请稍后再试');
                        break;
                        
                    case 403:
                        // IP被封禁或其他禁止访问的情况
                        if (response.data?.msg?.includes('封禁')) {
                            ElMessage.error('您的IP已被临时封禁');
                        } else {
                            ElMessage.error('访问被拒绝');
                        }
                        break;
                        
                    default:
                        // 其他错误由业务层处理
                        break;
                }
            }
            
            return Promise.reject(error);
        }
    );

    return instance;
}

/**
 * 手动签名工具函数（适用于非Axios场景）
 * 
 * @param {Object} options - 选项
 * @param {string} options.method - HTTP方法
 * @param {string} options.url - URL路径
 * @param {object|string} [options.body] - 请求体
 * @returns {Object} 包含headers的对象
 * 
 * @example
 * // 用于fetch API
 * fetch(url, {
 *   method: 'POST',
 *   headers: {
 *     'Content-Type': 'application/json',
 *     ...getSignHeaders({ method: 'POST', url: '/open/message', body: data })
 *   },
 *   body: JSON.stringify(data)
 * })
 */
export function getSignHeaders(options) {
    try {
        const result = PortalSign.sign(
            options.method || 'GET', 
            options.url || '/', 
            typeof options.body === 'object' ? JSON.stringify(options.body) : options.body
        );
        
        return result.headers;
    } catch (error) {
        console.error('[PortalSign] 获取签名头失败:', error);
        return {};
    }
}

export default {
    createPortalService,
    getSignHeaders,
};

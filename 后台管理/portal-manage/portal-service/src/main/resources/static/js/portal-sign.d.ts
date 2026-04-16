/**
 * Portal Service 接口签名工具 - TypeScript 类型声明
 * 
 * @version 1.0.0
 * @author 王有政
 */

declare namespace PortalSign {
    /**
     * 签名配置选项
     */
    interface SignConfig {
        /** 签名密钥（与服务端保持一致） */
        secret: string;
        
        /** 时间戳有效窗口（秒），默认300秒（5分钟） */
        timestampWindow?: number;
        
        /** 是否开启调试模式，默认false */
        debug?: boolean;
    }

    /**
     * 签名结果
     */
    interface SignResult {
        /** Base64编码的HMAC-SHA256签名 */
        signature: string;
        
        /** 当前时间戳（毫秒） */
        timestamp: number;
        
        /** 可直接使用的请求头对象 */
        headers: {
            'X-Timestamp': string;
            'X-Signature': string;
        };
    }

    /**
     * 初始化签名配置
     * 
     * @param config - 配置项
     * @example
     * PortalSign.init({
     *   secret: 'your-secret-key',
     *   timestampWindow: 300,
     *   debug: true
     * });
     */
    function init(config: SignConfig): void;

    /**
     * 获取当前配置
     * 
     * @returns 当前配置副本
     */
    function getConfig(): SignConfig;

    /**
     * 生成请求签名
     * 
     * @param method - HTTP方法（GET/POST/PUT/DELETE）
     * @param url - 请求URL路径（不含域名和查询参数）
     * @param body - 请求体字符串（GET请求可为空或undefined）
     * @returns 签名结果，包含签名和时间戳
     * 
     * @example
     * // POST请求签名
     * const result = PortalSign.sign('POST', '/open/message/submit', JSON.stringify(data));
     * console.log(result.signature);
     * console.log(result.timestamp);
     * 
     * // GET请求签名（无body）
     * const getResult = PortalSign.sign('GET', '/portal/article/list');
     */
    function sign(method: string, url: string, body?: string | object | undefined): SignResult;

    /**
     * 为fetch请求添加签名头
     * 
     * @param url - 请求URL
     * @param options - fetch选项（method, headers, body等）
     * @returns 添加了签名头的options对象
     * 
     * @example
     * fetch(PortalSign.signFetch('/api/data', {
     *   method: 'POST',
     *   headers: { 'Content-Type': 'application/json' },
     *   body: JSON.stringify(data)
     * })).then(res => res.json());
     */
    function signFetch(url: string, options?: RequestInit): RequestInit & { url: string };

    /**
     * 为XMLHttpRequest添加签名头
     * 
     * @param xhr - XHR实例
     * @param method - HTTP方法
     * @param url - 请求URL
     * @param body - 请求体（可为null）
     * 
     * @example
     * var xhr = new XMLHttpRequest();
     * xhr.open('POST', '/open/message/submit');
     * xhr.setRequestHeader('Content-Type', 'application/json');
     * PortalSign.signXHR(xhr, 'POST', '/open/message/submit', JSON.stringify(data));
     * xhr.send(data);
     */
    function signXHR(xhr: XMLHttpRequest, method: string, url: string, body?: string | null): void;

    /**
     * 验证时间戳是否在有效窗口内
     * （用于前端本地校验，实际验证由后端完成）
     * 
     * @param timestamp - 待验证的时间戳
     * @returns 是否在有效窗口内
     */
    function isTimestampValid(timestamp: number): boolean;

    /**
     * 版本号
     */
    const version: string;
}

/**
 * 全局PortalSign对象类型导出
 */
declare var PortalSign: typeof PortalSign;

export = PortalSign;
export as namespace PortalSign;

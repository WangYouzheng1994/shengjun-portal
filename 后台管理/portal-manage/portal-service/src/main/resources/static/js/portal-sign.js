/**
 * Portal Service 接口签名工具
 * 
 * 用于门户前端站调用 portal-service 后端接口时的请求签名
 * 采用 HMAC-SHA256 算法，确保请求的完整性、真实性和不可抵赖性
 * 
 * @version 1.0.0
 * @author 王有政
 * @description 门户服务前端签名SDK - 无任何第三方依赖，可在浏览器和Node.js环境使用
 */

var PortalSign = (function() {
    'use strict';

    /**
     * 签名配置
     * @typedef {Object} SignConfig
     * @property {string} secret - 签名密钥（与服务端保持一致）
     * @property {number} timestampWindow - 时间戳有效窗口（秒），默认300秒
     * @property {boolean} debug - 是否开启调试模式，默认false
     */

    /**
     * 签名结果
     * @typedef {Object} SignResult
     * @property {string} signature - Base64编码的HMAC-SHA256签名
     * @property {number} timestamp - 当前时间戳（毫秒）
     * @property {Object} headers - 可直接使用的请求头对象
     */

    /**
     * 默认配置
     */
    var DEFAULT_CONFIG = {
        secret: '',
        timestampWindow: 300,
        debug: false
    };

    /**
     * 当前配置实例
     */
    var _config = Object.assign({}, DEFAULT_CONFIG);

    // ==================== 工具函数 ====================

    /**
     * Base64编码（兼容性实现）
     * 
     * @param {string} str - 待编码字符串
     * @returns {string} Base64编码结果
     */
    function base64Encode(str) {
        if (typeof btoa === 'function') {
            return btoa(unescape(encodeURIComponent(str)));
        }
        
        // Node.js 环境 fallback
        if (typeof Buffer === 'function') {
            return Buffer.from(str, 'utf8').toString('base64');
        }
        
        throw new Error('当前环境不支持Base64编码');
    }

    /**
     * 字符串转字节数组
     * 
     * @param {string} str - 输入字符串
     * @returns {Array<number>} 字节数组
     */
    function stringToBytes(str) {
        var bytes = [];
        for (var i = 0; i < str.length; i++) {
            var code = str.charCodeAt(i);
            if (code < 0x80) {
                bytes.push(code);
            } else if (code < 0x800) {
                bytes.push(0xc0 | (code >> 6));
                bytes.push(0x80 | (code & 0x3f));
            } else if (code < 0xd800 || code >= 0xe000) {
                bytes.push(0xe0 | (code >> 12));
                bytes.push(0x80 | ((code >> 6) & 0x3f));
                bytes.push(0x80 | (code & 0x3f));
            } else {
                code = 0x10000 + (((code & 0x3ff) << 10) | (str.charCodeAt(++i) & 0x3ff));
                bytes.push(0xf0 | (code >> 18));
                bytes.push(0x80 | ((code >> 12) & 0x3f));
                bytes.push(0x80 | ((code >> 6) & 0x3f));
                bytes.push(0x80 | (code & 0x3f));
            }
        }
        return bytes;
    }

    /**
     * HMAC-SHA256 核心算法实现
     * 完整的SHA-256 + HMAC实现，无任何外部依赖
     * 
     * @param {string} message - 待签名的消息
     * @param {string} key - 签名密钥
     * @returns {Array<number>} 32字节的消息摘要
     */
    function hmacSha256(message, key) {
        var SHA256_K = [0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
                    0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
                    0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
                    0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
                    0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
                    0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
                    0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
                    0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
                    0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
                    0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
                    0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
                    0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
                    0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
                    0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
                    0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
                    0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2];

        var SHA256_H = [
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19
        ];

        var SHA256_R = [
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8,
            3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12,
            1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 5, 14, 15, 6, 2,
            4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13
        ];

        function rightRotate(value, amount) {
            return (value >>> amount) | (value << (32 - amount));
        }

        function sha256(M) {
            var m = stringToBytes(M);
            var msgLen = m.length;
            var bitLen = msgLen * 8;
            
            m.push(0x80);
            while (m.length % 64 !== 56) {
                m.push(0);
            }
            
            m.push(0);
            m.push(0);
            m.push(0);
            m.push(0);
            m.push((bitLen >>> 24) & 0xff);
            m.push((bitLen >>> 16) & 0xff);
            m.push((bitLen >>> 8) & 0xff);
            m.push(bitLen & 0xff);

            var K = SHA256_K;
            var H = SHA256_H.slice();
            var W = new Array(64);

            for (var i = 0; i < m.length; i += 64) {
                for (var t = 0; t < 16; t++) {
                    W[t] = (m[i + t * 4] << 24) |
                           (m[i + t * 4 + 1] << 16) |
                           (m[i + t * 4 + 2] << 8) |
                           (m[i + t * 4 + 3]);
                }

                for (t = 16; t < 64; t++) {
                    var s0 = rightRotate(W[t - 15], 7) ^ rightRotate(W[t - 15], 18) ^ (W[t - 15] >>> 3);
                    var s1 = rightRotate(W[t - 2], 17) ^ rightRotate(W[t - 2), 19) ^ (W[t - 2] >>> 10);
                    W[t] = (W[t - 16] + s0 + W[t - 7] + s1) | 0;
                }

                var a = H[0];
                var b = H[1];
                var c = H[2];
                var d = H[3];
                var e = H[4];
                var f = H[5];
                var g = H[6];
                var h = H[7];

                for (t = 0; t < 64; t++) {
                    var S1 = rightRotate(e, 6) ^ rightRotate(e, 11) ^ rightRotate(e, 25);
                    var ch = (e & f) ^ (~e & g);
                    var temp1 = (h + S1 + ch + K[t] + W[t]) | 0;
                    var S0 = rightRotate(a, 2) ^ rightRotate(a, 13) ^ rightRotate(a, 22);
                    var maj = (a & b) ^ (a & c) ^ (b & c);
                    var temp2 = (S0 + maj) | 0;

                    h = g;
                    g = f;
                    f = e;
                    e = (d + temp1) | 0;
                    d = c;
                    c = b;
                    b = a;
                    a = (temp1 + temp2) | 0;
                }

                H[0] = (H[0] + a) | 0;
                H[1] = (H[1] + b) | 0;
                H[2] = (H[2] + c) | 0;
                H[3] = (H[3] + d) | 0;
                H[4] = (H[4] + e) | 0;
                H[5] = (H[5] + f) | 0;
                H[6] = (H[6] + g) | 0;
                H[7] = (H[7] + h) | 0;
            }

            return H;
        }

        // HMAC-SHA256 实现
        var blockSize = 64;
        var keyBytes = stringToBytes(key);
        var msgBytes = stringToBytes(message);

        if (keyBytes.length > blockSize) {
            keyBytes = sha256(key);
        }

        var ipad = new Array(blockSize);
        var opad = new Array(blockSize);

        for (var i = 0; i < blockSize; i++) {
            ipad[i] = keyBytes[i] ^ 0x36;
            opad[i] = keyBytes[i] ^ 0x5c;
        }

        var innerHash = sha256(ipad.concat(msgBytes));
        var outerHash = sha256(opad.concat(innerHash));

        return outerHash;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * 
     * @param {Array<number>} bytes - 字节数组
     * @returns {string} 十六进制字符串
     */
    function bytesToHex(bytes) {
        var hex = [];
        for (var i = 0; i < bytes.length; i++) {
            hex.push(('0' + (bytes[i] & 0xff).toString(16)).slice(-2));
        }
        return hex.join('');
    }

    // ==================== 公共API ====================

    return {
        /**
         * 初始化签名配置
         * 
         * @param {SignConfig} config - 配置项
         * @example
         * PortalSign.init({
         *   secret: 'your-secret-key',
         *   timestampWindow: 300,
         *   debug: true
         * });
         */
        init: function(config) {
            _config = Object.assign({}, DEFAULT_CONFIG, config);
            
            if (_config.debug) {
                console.log('[PortalSign] 配置初始化完成:', {
                    secret: _config.secret ? '***已设置***' : '未设置',
                    timestampWindow: _config.timestampWindow + '秒',
                    debug: _config.debug
                });
            }
        },

        /**
         * 获取当前配置
         * 
         * @returns {SignConfig} 当前配置
         */
        getConfig: function() {
            return Object.assign({}, _config);
        },

        /**
         * 生成请求签名
         * 
         * @param {string} method - HTTP方法（GET/POST/PUT/DELETE）
         * @param {string} url - 请求URL路径（不含域名）
         * @param {Object|string|undefined} body - 请求体（GET请求可为空）
         * @returns {SignResult} 签名结果
         * 
         * @example
         * var result = PortalSign.sign('POST', '/open/message/submit', JSON.stringify(data));
         * console.log(result.signature);  // Base64编码的签名
         * console.log(result.timestamp);  // 时间戳
         * console.log(result.headers);    // 可直接用于fetch/axios的headers
         */
        sign: function(method, url, body) {
            if (!_config.secret) {
                throw new Error('[PortalSign] 错误：未设置签名密钥，请先调用 init() 方法');
            }

            var timestamp = Date.now();
            
            // 构建待签名字符串（与后端格式一致）
            var signData = method.toUpperCase() + '\n' +
                          url + '\n' +
                          timestamp + '\n' +
                          (body || '');

            // 计算HMAC-SHA256签名
            var hash = hmacSha256(signData, _config.secret);
            var signature = base64Encode(bytesToHex(hash));

            var result = {
                signature: signature,
                timestamp: timestamp,
                headers: {
                    'X-Timestamp': timestamp.toString(),
                    'X-Signature': signature
                }
            };

            if (_config.debug) {
                console.log('[PortalSign] 签名生成成功:', {
                    method: method.toUpperCase(),
                    url: url,
                    signDataLength: signData.length,
                    signDataPreview: signData.substring(0, 50) + '...',
                    signature: signature,
                    timestamp: timestamp
                });
            }

            return result;
        },

        /**
         * 为fetch请求添加签名头
         * 
         * @param {string} url - 请求URL
         * @param {Object} options - fetch选项
         * @returns {Object} 添加了签名头的options
         * 
         * @example
         * fetch(PortalSign.signFetch('/api/data', {
         *   method: 'POST',
         *   headers: { 'Content-Type': 'application/json' },
         *   body: JSON.stringify(data)
         * })).then(res => res.json());
         */
        signFetch: function(url, options) {
            options = options || {};
            var method = options.method || 'GET';
            var parsedUrl = this._parseUrl(url);
            var body = options.body;

            var signResult = this.sign(method, parsedUrl.pathname, body);
            
            options.headers = Object.assign({}, options.headers || {}, signResult.headers);
            options.url = url;

            if (_config.debug) {
                console.log('[PortalSign] Fetch请求已签名:', {
                    url: url,
                    method: method,
                    addedHeaders: signResult.headers
                });
            }

            return options;
        },

        /**
         * 为XMLHttpRequest添加签名头
         * 
         * @param {XMLHttpRequest} xhr - XHR实例
         * @param {string} method - HTTP方法
         * @param {string} url - 请求URL
         * @param {string|null} body - 请求体
         */
        signXHR: function(xhr, method, url, body) {
            var parsedUrl = this._parseUrl(url);
            var signResult = this.sign(method, parsedUrl.pathname, body);

            xhr.setRequestHeader('X-Timestamp', signResult.timestamp.toString());
            xhr.setRequestHeader('X-Signature', signResult.signature);

            if (_config.debug) {
                console.log('[PortalSign] XHR请求已签名:', {
                    url: url,
                    method: method,
                    addedHeaders: signResult.headers
                });
            }
        },

        /**
         * 验证时间戳是否在有效窗口内
         * （用于调试或本地校验）
         * 
         * @param {number} timestamp - 待验证的时间戳
         * @returns {boolean} 是否有效
         */
        isTimestampValid: function(timestamp) {
            var now = Date.now();
            var diff = Math.abs(now - timestamp);
            var windowMs = _config.timestampWindow * 1000;

            return diff <= windowMs;
        },

        /**
         * 解析URL获取pathname
         * 
         * @private
         * @param {string} url - 完整URL
         * @returns {Object} URL解析结果
         */
        _parseUrl: function(url) {
            try {
                if (typeof URL !== 'undefined') {
                    var parsed = new URL(url);
                    return {
                        pathname: parsed.pathname,
                        origin: parsed.origin
                    };
                }
                
                // 简单fallback：提取pathname
                var pathname = url.split('?')[0].split('#')[0];
                if (!pathname.startsWith('/')) {
                    pathname = '/' + pathname;
                }
                
                return { pathname: pathname, origin: '' };
            } catch (e) {
                return { pathname: url, origin: '' };
            }
        },

        /**
         * 版本信息
         */
        version: '1.0.0'
    };

})();

// ==================== 导出适配 ====================

// CommonJS环境（Node.js）
if (typeof module !== 'undefined' && module.exports) {
    module.exports = PortalSign;
}

// ES Module环境
if (typeof window !== 'undefined') {
    window.PortalSign = PortalSign;
}

# 🔐 Portal Service 前端签名工具包

> **版本**: 1.0.0  
> **作者**: 王有政  
> **许可证**: MIT  
> **兼容性**: 所有现代浏览器 + Node.js 12+  
> **依赖**: **零依赖**（纯JavaScript实现）

---

## 📖 目录

- [功能特性](#功能特性)
- [快速开始](#快速开始)
- [安装方式](#安装方式)
- [API文档](#api文档)
- [集成示例](#集成示例)
- [调试工具](#调试工具)
- [常见问题](#常见问题)
- [安全建议](#安全建议)

---

## ✨ 功能特性

### 核心能力
- ✅ **HMAC-SHA256 强加密算法** - 工业级安全标准
- ✅ **零外部依赖** - 纯JavaScript实现，无任何第三方库
- ✅ **多环境支持** - 浏览器、Node.js、Vue3/React/Angular
- ✅ **TypeScript 完整支持** - 包含类型声明文件
- ✅ **自动时间戳管理** - 防重放攻击
- ✅ **调试模式** - 开发环境详细日志输出

### 集成方案
- 🎯 **原生 fetch / XHR 支持** - 无需框架即可使用
- 🎯 **Axios 拦截器** - Vue3 项目一键集成
- 🎯 **在线调试工具** - 可视化签名生成与验证

---

## 🚀 快速开始

### 1️⃣ 最简示例（3行代码）

```javascript
// 引入工具库
<script src="portal-sign.js"></script>

// 初始化配置
PortalSign.init({ secret: 'your-secret-key' });

// 生成签名
const result = PortalSign.sign('POST', '/open/message', JSON.stringify(data));

// 发送请求时带上签名头
fetch(url, { headers: result.headers, body: JSON.stringify(data) });
```

### 2️⃣ Vue3 + Axios 推荐（自动签名）

```javascript
// main.js
import { createPortalService } from '@/utils/portal-sign-axios';

const portalApi = createPortalService({
    baseURL: process.env.VUE_APP_PORTAL_SERVICE_URL,
});

export default portalApi;

// 在组件中使用（无需手动签名！）
portalApi.post('/open/message/submit', data).then(res => {
    console.log('请求成功！', res.data);
});
```

---

## 📦 安装方式

### 方式一：直接引用（推荐用于快速集成）

将以下文件复制到你的前端项目中：

```
your-project/
├── src/
│   └── utils/
│       ├── portal-sign.js          # 核心签名库（必须）
│       ├── portal-sign.d.ts        # TypeScript声明（可选）
│       └── portal-sign-axios.js     # Axios拦截器（Vue3项目推荐）
└── static/
    └── sign-test.html              # 调试工具页面（开发用）
```

### 方式二：NPM发布（未来支持）

```bash
npm install @raisetech/portal-sign --save
```

---

## 📚 API 文档

### `PortalSign.init(config)`

初始化签名配置，**必须在调用其他方法前执行一次**。

#### 参数说明

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| `config.secret` | `string` | ✅ | - | 签名密钥（与服务端一致） |
| `config.timestampWindow` | `number` | ❌ | `300` | 时间戳有效窗口（秒） |
| `config.debug` | `boolean` | ❌ | `false` | 是否开启调试模式 |

#### 示例

```javascript
PortalSign.init({
    secret: 'portal_service_secret_key_2026_secure',
    timestampWindow: 300,   // 5分钟有效窗口
    debug: true             // 开发环境开启日志
});
```

---

### `PortalSign.sign(method, url, body?)`

生成请求签名。

#### 参数说明

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `method` | `string` | ✅ | HTTP方法：`GET` / `POST` / `PUT` / `DELETE` |
| `url` | `string` | ✅ | 请求路径（不含域名和查询参数），如 `/open/message` |
| `body` | `string \| object \| undefined` | ❌ | 请求体（GET可为空） |

#### 返回值：`SignResult`

```typescript
interface SignResult {
    signature: string;      // Base64编码的HMAC-SHA256签名
    timestamp: number;      // 当前时间戳（毫秒）
    headers: {
        'X-Timestamp': string;
        'X-Signature': string;
    };
}
```

#### 示例

```javascript
// POST请求带Body
const result = PortalSign.sign(
    'POST',
    '/open/message/submit',
    JSON.stringify({ name: '张三', content: '测试' })
);

console.log(result.signature);    // "aBcDeFgHiJkLmNoPqRsTuVwXyZ123456=="
console.log(result.timestamp);   // 1713292800000
console.log(result.headers);
// { 'X-Timestamp': '1713292800000', 'X-Signature': 'aBcDeFg...' }

// GET请求无Body
const getResult = PortalSign.sign('GET', '/portal/article/list');
```

---

### `PortalSign.signFetch(url, options?)`

为 **fetch API** 自动添加签名头。

#### 示例

```javascript
fetch(PortalSign.signFetch('/api/data', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
})).then(res => res.json());
```

---

### `PortalSign.signXHR(xhr, method, url, body?)`

为 **XMLHttpRequest** 添加签名头。

#### 示例

```javascript
var xhr = new XMLHttpRequest();
xhr.open('POST', '/open/message');
xhr.setRequestHeader('Content-Type', 'application/json');

PortalSign.signXHR(xhr, 'POST', '/open/message', JSON.stringify(data));
xhr.send(JSON.stringify(data));
```

---

### `PortalSign.isTimestampValid(timestamp)`

验证时间戳是否在有效窗口内（前端本地校验用）。

#### 示例

```javascript
const isValid = PortalSign.isTimestampValid(1713292800000);
console.log(isValid ? '✅ 有效' : '❌ 已过期');
```

---

### `PortalSign.getConfig()`

获取当前配置（调试用）。

---

## 🔌 集成示例

### Vue3 项目（推荐）

#### 步骤1：复制文件到项目

```bash
cp portal-service/src/main/resources/static/js/portal-sign.js src/utils/
cp portal-service/src/main/resources/static/js/portal-sign-axios.js src/utils/
cp portal-service/src/main/resources/static/js/portal-sign.d.ts src/utils/
```

#### 步骤2：在 main.js 中初始化

```javascript
// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import ElementPlus from 'element-plus';

// 初始化签名工具
import PortalSign from './utils/portal-sign';

PortalSign.init({
    secret: process.env.VUE_APP_SIGN_SECRET || 'your-secret-key',
    timestampWindow: 300,
    debug: process.env.NODE_ENV === 'development'
});

const app = createApp(App);
app.use(ElementPlus);
app.mount('#app');
```

#### 步骤3：创建API实例

```javascript
// src/api/portal.js
import { createPortalService } from '../utils/portal-sign-axios';

const portalApi = createPortalService({
    baseURL: import.meta.env.VITE_PORTAL_SERVICE_URL || 'http://localhost:8081',
});

export default portalApi;
```

#### 步骤4：在组件中使用

```vue
<template>
    <div>
        <el-button @click="submitMessage">提交留言</el-button>
    </div>
</template>

<script setup>
import portalApi from '@/api/portal';

const submitMessage = async () => {
    try {
        const res = await portalApi.post('/open/message/submit', {
            name: '张三',
            contact: '13800138000',
            content: '这是一条测试留言'
        });

        ElMessage.success('提交成功！');
        console.log('响应数据:', res.data);
    } catch (error) {
        if (error.response?.status === 401) {
            ElMessage.error('签名验证失败');
        }
    }
};
</script>
```

---

### React 项目

```jsx
// App.jsx
import { useEffect, useState } from 'react';
import axios from 'axios';
import PortalSign from './utils/portal-sign';

function App() {
    useEffect(() => {
        // 初始化签名
        PortalSign.init({ secret: process.env.REACT_APP_SIGN_SECRET });
        
        // 配置axios拦截器
        axios.interceptors.request.use(config => {
            if (['POST', 'PUT', 'DELETE'].includes(config.method.toUpperCase())) {
                let body = config.data;
                if (typeof body === 'object') {
                    body = JSON.stringify(body);
                }
                
                const signResult = PortalSign.sign(config.method, config.url, body);
                config.headers['X-Timestamp'] = signResult.timestamp.toString();
                config.headers['X-Signature'] = signResult.signature;
            }
            
            return config;
        });
    }, []);

    return <div>React应用已配置签名</div>;
}

export default App;
```

---

### 原生 JavaScript（无框架）

```html
<!DOCTYPE html>
<html>
<head>
    <title>Portal Sign Demo</title>
    <script src="./js/portal-sign.js"></script>
</head>
<body>
    <button onclick="sendRequest()">发送请求</button>

    <script>
        // 1. 初始化
        PortalSign.init({ secret: 'your-secret-key' });

        // 2. 发送请求函数
        function sendRequest() {
            const data = { name: '张三', content: '测试' };
            
            // 3. 生成签名
            const signResult = PortalSign.sign('POST', '/open/message', JSON.stringify(data));
            
            // 4. 使用fetch发送请求
            fetch('http://localhost:8081/open/message', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    ...signResult.headers
                },
                body: JSON.stringify(data)
            })
            .then(res => res.json())
            .then(data => console.log('成功:', data))
            .catch(err => console.error('失败:', err));
        }
    </script>
</body>
</html>
```

---

## 🛠️ 调试工具

我们提供了一个**可视化的在线签名调试工具**，方便开发和测试：

### 启动方式

1. **直接打开浏览器访问**：
   ```bash
   # 将sign-test.html部署到静态服务器后访问
   http://localhost:8081/sign-test.html
   ```

2. **或者作为独立页面使用**：
   ```bash
   # 复制到任意Web服务器
   cp portal-service/src/main/resources/static/sign-test.html your-web-root/
   
   # 直接双击打开也行（部分功能受限）
   ```

### 功能介绍

- ✅ 可视化配置密钥和时间窗口
- ✅ 支持所有HTTP方法和URL路径
- ✅ 实时生成签名并展示结果
- ✅ 一键复制签名结果到剪贴板
- ✅ 显示待签名字符串（便于调试）
- ✅ 时间戳有效性检查
- ✅ 内置代码示例（JS/TS/Vue3）

---

## ❓ 常见问题

### Q1: 签名验证失败怎么办？

**排查步骤**：

1. **确认密钥一致**
   ```javascript
   // 前端
   PortalSign.init({ secret: 'xxx' });
   
   // 后端 application.yml
   security:
     signature:
       secret: xxx  // 必须完全一致！
   ```

2. **检查时间同步**
   - 确保客户端和服务器的时钟误差不超过5分钟
   - 可以通过调试工具查看时间戳有效性

3. **检查请求体顺序**
   - 签名时的body字符串必须与实际发送的完全一致
   - 注意JSON键的顺序（某些库会重新排序）

4. **开启调试模式**
   ```javascript
   PortalSign.init({ debug: true });  // 查看详细日志
   ```

### Q2: GET请求需要签名吗？

**不需要**。根据我们的设计：
- ✅ **需要签名**：`POST` / `PUT` / `DELETE` / `PATCH`
- ❌ **不需要签名**：`GET` / `HEAD` / `OPTIONS` / 公开读接口

如果确实需要对GET请求签名，可以手动调用 `PortalSign.sign()` 方法。

### Q3: 如何处理跨域问题？

确保后端CORS配置允许你的域名：

```yaml
# 后端 application.yml
security:
  cors:
    allowed-origins:
      - http://localhost:5173    # Vite开发服务器
      - https://yourdomain.com  # 生产域名
```

### Q4: 密钥如何安全管理？

**生产环境最佳实践**：

1. **不要硬编码在前端代码中**
2. **使用环境变量或构建时注入**
3. **定期更换密钥**

```javascript
// .env.development (开发环境)
VUE_APP_SIGN_SECRET=dev_secret_key_123

// .env.production (生产环境)
VUE_APP_SIGN_SECRET=${PORTAL_SIGN_SECRET}  // 从CI/CD注入

// main.js
PortalSign.init({ 
    secret: import.meta.env.VITE_APP_SIGN_SECRET 
});
```

### Q5: 性能影响大吗？

**几乎无影响**：
- SHA-256计算耗时 < 1ms（现代CPU）
- Base64编码耗时 < 0.1ms
- 总体增加延迟 < 2ms/次请求

对于高并发场景，可以考虑缓存签名结果（同一参数短时间内复用）。

---

## 🔒 安全建议

### 1. 密钥管理
- ⚠️ **禁止**将密钥提交到Git仓库
- ✅ 使用 `.env` 文件或 CI/CD 变量
- ✅ 定期轮换密钥（建议每90天）

### 2. 前端防护
- ✅ 生产环境关闭 `debug` 模式
- ✅ 使用 HTTPS 协议传输
- ✅ 对敏感接口添加额外的 CSRF Token

### 3. 监控告警
- ✅ 记录签名失败率（异常升高可能表示攻击）
- ✅ 监控异常IP的请求频率
- ✅ 设置自动封禁阈值

### 4. 版本控制
- ✅ 前后端签名逻辑保持同步升级
- ✅ 更新密钥时同时更新前后端
- ✅ 保留旧版本兼容期（灰度过渡）

---

## 📄 文件清单

| 文件名 | 大小 | 用途 | 是否必需 |
|--------|------|------|----------|
| `portal-sign.js` | ~15KB | 核心签名库 | ✅ 必须 |
| `portal-sign.d.ts` | ~3KB | TypeScript类型声明 | ❌ 可选 |
| `portal-sign-axios.js` | ~5KB | Axios拦截器集成 | ❌ Vue3推荐 |
| `sign-test.html` | ~15KB | 在线调试工具 | ❌ 开发用 |

---

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

---

## 📜 许可证

本项目基于 MIT 许可证开源。详见 [LICENSE](LICENSE) 文件。

---

## 🙏 致谢

- HMAC-SHA256 算法实现参考 RFC 2104
- 感谢若依（RuoYi）框架提供的基础架构
- 感谢 Element Plus 提供优秀的UI组件

---

**🎉 开始使用吧！如有问题欢迎提Issue或联系开发者。**

**作者**: 王有政  
**最后更新**: 2026-04-16

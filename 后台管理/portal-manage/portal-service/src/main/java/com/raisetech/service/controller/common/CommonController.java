package com.raisetech.service.controller.common;

import com.raisetech.common.core.domain.AjaxResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 门户前端站-公共接口
 * 提供验证码、健康检查、系统信息等公共服务
 * 
 * @author 王有政
 */
@Tag(name = "公共接口")
@RestController
@RequestMapping("/common")
public class CommonController
{
    /**
     * 健康检查端点
     * 用于负载均衡器、监控系统检测服务状态
     */
    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public AjaxResult health()
    {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        healthInfo.put("service", "portal-service");
        healthInfo.put("timestamp", System.currentTimeMillis());
        return AjaxResult.success(healthInfo);
    }

    /**
     * 获取服务版本信息
     * 用于问题排查和运维监控
     */
    @Operation(summary = "获取版本信息")
    @GetMapping("/info")
    public AjaxResult info()
    {
        Map<String, Object> info = new HashMap<>();
        info.put("serviceName", "Portal-Service");
        info.put("version", "3.9.2");
        info.put("description", "门户前端站API服务");
        info.put("securityLevel", "HIGH");
        return AjaxResult.success(info);
    }

    /**
     * 获取安全配置概要（脱敏处理）
     * 前端可根据此信息调整请求策略
     */
    @Operation(summary = "获取安全配置概要")
    @GetMapping("/security/config")
    public AjaxResult securityConfig()
    {
        Map<String, Object> config = new HashMap<>();
        // 返回限流相关配置（不暴露具体阈值）
        config.put("rateLimitEnabled", true);
        config.put("maxRequestSize", "1MB");
        config.put("supportedMethods", "GET,POST,PUT,DELETE");

        // 提示前端需要携带的请求头
        config.put("requiredHeaders", new String[]{"X-Timestamp"});

        return AjaxResult.success(config);
    }
}

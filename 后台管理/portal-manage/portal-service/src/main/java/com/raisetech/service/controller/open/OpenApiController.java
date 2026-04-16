package com.raisetech.service.controller.open;

import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.service.security.annotation.SignatureRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Map;

/**
 * 门户前端站-开放接口（需要签名验证）
 * 用于处理用户提交的敏感数据，如留言、反馈、表单提交等
 * 所有写操作都必须携带有效的签名
 *
 * 签名要求：
 * 1. Header: X-Signature (HMAC-SHA256签名)
 * 2. Header: X-Timestamp (当前时间戳，毫秒)
 *
 * @author 王有政
 */
@Tag(name = "开放接口（需签名）")
@RestController
@RequestMapping("/open")
public class OpenApiController extends BaseController
{
    /**
     * 提交用户留言/反馈
     * 需要完整的签名验证 + 参数校验 + 限流保护
     */
    @Operation(summary = "提交用户留言")
    @PostMapping("/message/submit")
    @SignatureRequired(
        requireTimestamp = true,
        timestampWindow = 300,  // 5分钟有效窗口
        validateBody = true      // 校验请求体
    )
    public AjaxResult submitMessage(@Valid @RequestBody MessageSubmitDTO dto)
    {
        // TODO: 调用消息服务保存留言
        // messageService.saveMessage(dto);

        return success("留言提交成功，我们会尽快回复");
    }

    /**
     * 提交联系表单
     * 用于"联系我们"页面的表单提交
     */
    @Operation(summary = "提交联系表单")
    @PostMapping("/contact/submit")
    @SignatureRequired
    public AjaxResult submitContact(@Valid @RequestBody ContactFormDTO dto)
    {
        // TODO: 处理联系表单

        return success("提交成功，我们将尽快与您联系");
    }

    /**
     * 订阅邮件/通知
     * 用于用户订阅 newsletters 或通知
     */
    @Operation(summary = "订阅通知")
    @PostMapping("/subscribe")
    @SignatureRequired(
        timestampWindow = 600  // 10分钟有效窗口（订阅操作相对宽松）
    )
    public AjaxResult subscribe(@RequestBody Map<String, String> params)
    {
        String email = params.get("email");

        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
        {
            return error("邮箱格式不正确");
        }

        // TODO: 处理订阅逻辑

        return success("订阅成功");
    }

    /**
     * 用户留言提交DTO
     */
    public static class MessageSubmitDTO
    {
        /** 留言人姓名 */
        @NotBlank(message = "姓名不能为空")
        @Size(max = 50, message = "姓名长度不能超过50个字符")
        private String name;

        /** 联系方式（手机或邮箱） */
        @NotBlank(message = "联系方式不能为空")
        @Size(max = 100, message = "联系方式长度不能超过100个字符")
        private String contact;

        /** 留言内容 */
        @NotBlank(message = "留言内容不能为空")
        @Size(max = 2000, message = "留言内容长度不能超过2000个字符")
        private String content;

        /** 留言类型（feedback/suggestion/complaint/other） */
        private String type;

        // Getter和Setter方法
        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getContact()
        {
            return contact;
        }

        public void setContact(String contact)
        {
            this.contact = contact;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }
    }

    /**
     * 联系表单DTO
     */
    public static class ContactFormDTO
    {
        /** 姓名 */
        @NotBlank(message = "姓名不能为空")
        private String name;

        /** 公司名（可选） */
        private String company;

        /** 邮箱 */
        @NotBlank(message = "邮箱不能为空")
        private String email;

        /** 电话（可选） */
        private String phone;

        /** 咨询主题 */
        @NotBlank(message = "咨询主题不能为空")
        private String subject;

        /** 详细描述 */
        @NotBlank(message = "详细描述不能为空")
        @Size(max = 5000, message = "详细描述长度不能超过5000个字符")
        private String message;

        // Getter和Setter方法
        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getCompany()
        {
            return company;
        }

        public void setCompany(String company)
        {
            this.company = company;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getSubject()
        {
            return subject;
        }

        public void setSubject(String subject)
        {
            this.subject = subject;
        }

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }
    }
}

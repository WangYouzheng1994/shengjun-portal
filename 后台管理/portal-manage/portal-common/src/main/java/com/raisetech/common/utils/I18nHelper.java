package com.raisetech.common.utils;

/**
 * 多语言工具类
 * 封装常用的多语言回退、判断等纯工具方法
 * 注意：此类为公共工具类，不依赖任何业务模块
 *
 * @author 王有政
 */
public class I18nHelper {

    /**
     * 默认语言代码（中文简体）
     */
    public static final String DEFAULT_LANG_CODE = "zh-CN";

    /**
     * 获取翻译值，如果不存在则返回主表的原始值（回退机制）
     * 当目标语言没有翻译时，自动使用默认语言的值
     *
     * @param i18nValue 翻译值（可能为空）
     * @param defaultValue 主表的默认值
     * @return 翻译值或默认值
     */
    public static String getWithFallback(String i18nValue, String defaultValue) {
        if (StringUtils.isNotBlank(i18nValue)) {
            return i18nValue;
        }
        return defaultValue;
    }

    /**
     * 判断是否为默认语言（中文）
     *
     * @param langCode 语言代码
     * @return 是否为默认语言
     */
    public static boolean isDefaultLanguage(String langCode) {
        if (StringUtils.isBlank(langCode)) {
            return true;
        }
        return DEFAULT_LANG_CODE.equals(langCode);
    }

    /**
     * 规范化语言代码
     * 处理null或空白字符串，返回默认语言代码
     *
     * @param langCode 输入的语言代码
     * @return 规范化后的语言代码
     */
    public static String normalizeLangCode(String langCode) {
        if (StringUtils.isBlank(langCode)) {
            return DEFAULT_LANG_CODE;
        }
        return langCode.trim();
    }
}

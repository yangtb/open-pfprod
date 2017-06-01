package com.sm.pfprod.service.common.util;

/**
 * @ClassName: AuthConstant
 * @Description: 认证中的一些常量定义
 */
public class AuthConstant {

    public static final String CURRENT_USER         = "currUser";
    /** CURRENT_ACCESS_TOKEN */
    public static final String CURRENT_ACCESS_TOKEN = "currAccessToken";
    /** 错误编码 */
    public static final String ERROR_CODE           = "errorCode";
    /** 错误消息 */
    public static final String ERROR_MSG            = "errorMsg";
    /** appId的key */
    public static final String APP_ID_KEY           = "app_id";
    /** 方法名称key */
    public static final String METHOD_KEY           = "method";
    /** CHARSET_KEY */
    public static final String CHARSET_KEY          = "charset";
    /** 签名方式key */
    public static final String SIGN_TYPE_KEY        = "sign_type";
    /** 签名串key */
    public static final String SIGN_KEY             = "sign";
    /** 时间戳key */
    public static final String TIMESTAMP_KEY        = "timestamp";
    /** 版本key */
    public static final String VERSION_KEY          = "version";
    /** 授权令牌 */
    public static final String AUTH_TOKEN_KEY       = "auth_token";
    /** 一分钟时间，单位：秒 */
    public static final int    ONE_MINUTE_SECONDS   = 60;
    /** 一年时间，单位：秒 */
    public static final int    ONE_YEAR_SECONDS     = 31536000;
}

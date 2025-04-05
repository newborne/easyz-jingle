package cn.easyz.common.model.enums;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * The enum Result code enum.
 */
@Getter
@ApiModel
public enum ResultCodeEnum {
    /**
     * Success result code enum.
     */
// 成功段
    SUCCESS(200, "成功"),
    /**
     * Fail result code enum.
     */
    FAIL(201, "失败"),
    /**
     * Send checkcode error 1 result code enum.
     */
// 登录段1~50
    SEND_CHECKCODE_ERROR_1(001, "发送短信验证码失败"),
    /**
     * Send checkcode error 2 result code enum.
     */
    SEND_CHECKCODE_ERROR_2(002, "上一次发送的验证码还未失效"),
    /**
     * Need login result code enum.
     */
    NEED_LOGIN(1, "需要登录后操作"),
    /**
     * Login password error result code enum.
     */
    LOGIN_PASSWORD_ERROR(2, "密码错误"),
    /**
     * Token invalid result code enum.
     */
// TOKEN50~100
    TOKEN_INVALID(50, "无效的TOKEN"),
    /**
     * Token expire result code enum.
     */
    TOKEN_EXPIRE(51, "TOKEN已过期"),
    /**
     * Token require result code enum.
     */
    TOKEN_REQUIRE(52, "TOKEN是必须的"),
    /**
     * Sign invalid result code enum.
     */
// SIGN验签 100~120
    SIGN_INVALID(100, "无效的SIGN"),
    /**
     * Sig timeout result code enum.
     */
    SIG_TIMEOUT(101, "SIGN已过期"),
    /**
     * Param require result code enum.
     */
// 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数"),
    /**
     * Param invalid result code enum.
     */
    PARAM_INVALID(501, "无效参数"),
    /**
     * Param image format error result code enum.
     */
    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    /**
     * Server error result code enum.
     */
    SERVER_ERROR(503, "服务器内部错误"),
    /**
     * Data exist result code enum.
     */
// 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),
    /**
     * Ap user data not exist result code enum.
     */
    AP_USER_DATA_NOT_EXIST(1001, "ApUser数据不存在"),
    /**
     * Data not exist result code enum.
     */
    DATA_NOT_EXIST(1002, "数据不存在"),
    /**
     * No operator auth result code enum.
     */
// 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000, "无权限操作");
    private Integer code;
    private String message;
    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

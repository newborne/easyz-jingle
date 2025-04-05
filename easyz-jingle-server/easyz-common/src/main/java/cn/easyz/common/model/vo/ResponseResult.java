package cn.easyz.common.model.vo;

import cn.easyz.common.model.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * The type Response result.
 *
 * @param <T> the type parameter
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class ResponseResult<T> {
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;
    /**
     * Instantiates a new Response result.
     */
    public ResponseResult() {
    }
    /**
     * Build response result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response result
     */
    protected static <T> ResponseResult<T> build(T data) {
        ResponseResult<T> result = new ResponseResult<T>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }
    /**
     * Build response result.
     *
     * @param <T>            the type parameter
     * @param body           the body
     * @param resultCodeEnum the result code enum
     * @return the response result
     */
    public static <T> ResponseResult<T> build(T body, ResultCodeEnum resultCodeEnum) {
        ResponseResult<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }
    /**
     * Build response result.
     *
     * @param <T>     the type parameter
     * @param code    the code
     * @param message the message
     * @return the response result
     */
    public static <T> ResponseResult<T> build(Integer code, String message) {
        ResponseResult<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    /**
     * Ok response result.
     *
     * @param <T> the type parameter
     * @return the response result
     */
    public static <T> ResponseResult<T> ok() {
        return ResponseResult.ok(null);
    }
    /**
     * Ok response result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response result
     */
    public static <T> ResponseResult<T> ok(T data) {
        ResponseResult<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }
    /**
     * Fail response result.
     *
     * @param <T> the type parameter
     * @return the response result
     */
    public static <T> ResponseResult<T> fail() {
        return ResponseResult.fail(null);
    }
    /**
     * Fail response result.
     *
     * @param <T>  the type parameter
     * @param data the data
     * @return the response result
     */
    public static <T> ResponseResult<T> fail(T data) {
        ResponseResult<T> responseResult = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }
    /**
     * Message response result.
     *
     * @param msg the msg
     * @return the response result
     */
    public ResponseResult<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }
    /**
     * Code response result.
     *
     * @param code the code
     * @return the response result
     */
    public ResponseResult<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
    /**
     * Is ok boolean.
     *
     * @return the boolean
     */
    public boolean isOk() {
        if (this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
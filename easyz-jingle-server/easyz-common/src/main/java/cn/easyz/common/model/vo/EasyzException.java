package cn.easyz.common.model.vo;

import cn.easyz.common.model.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * The type Easyz exception.
 */
@Data
@ApiModel(value = "自定义全局异常类")
public class EasyzException extends RuntimeException {
    @ApiModelProperty(value = "异常状态码")
    private Integer code;
    /**
     * Instantiates a new Easyz exception.
     *
     * @param message the message
     * @param code    the code
     */
    public EasyzException(String message, Integer code) {
        super(message);
        this.code = code;
    }
    /**
     * Instantiates a new Easyz exception.
     *
     * @param resultCodeEnum the result code enum
     */
    public EasyzException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    @Override
    public String toString() {
        return "EasyzException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
package cn.easyz.common.handler;

import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.common.model.vo.EasyzException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Error response result.
     *
     * @param e the e
     * @return the response result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e) {
        e.printStackTrace();
        return ResponseResult.fail();
    }
    /**
     * Error response result.
     *
     * @param e the e
     * @return the response result
     */
    @ExceptionHandler(EasyzException.class)
    @ResponseBody
    public ResponseResult error(EasyzException e) {
        e.printStackTrace();
        return ResponseResult.fail();
    }
}

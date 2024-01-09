package com.bbc.exception;

import com.bbc.dto.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @Title: GlobalExceptionHandler
 * @Author WangHaoWei
 * @Package com.bbc.exception
 * @Date 2024/1/9 14:39
 * @description:
 */

/**
 * 全局异常处理
 *
 * @author qp
 * @date 2019/4/12 10:10
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseResult jsonErrorHandler(Exception e) {
        return ResponseResult.fail(e.getMessage());
    }
    @ExceptionHandler(value = BaseException.class)
    public ResponseResult businessMessages(BaseException e){
        return ResponseResult.fail(e.getMessage());
    }
}


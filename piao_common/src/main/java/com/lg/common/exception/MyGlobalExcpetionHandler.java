package com.lg.common.exception;

import com.lg.common.response.ResponseResult;
import com.lg.common.response.ResultCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author L
 * @version 1.0
 * @ClassName: MyGlobalExcpetionHandler
 * @date: 2019/12/31 11:02
 * @since JDK 1.8
 */
@ControllerAdvice
public class MyGlobalExcpetionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e) {
        e.printStackTrace();
        return ResponseResult.error();
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public ResponseResult arithmeticException(Exception e) {
        e.printStackTrace();
        return ResponseResult.setResult(ResultCode.Arithmetic_Exception);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public ResponseResult badSqlGrammarException(Exception e) {
        e.printStackTrace();
        return ResponseResult.setResult(ResultCode.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseResult JsonParseException(Exception e){
        e.printStackTrace();
        return ResponseResult.setResult(ResultCode.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(PiaoException.class)
    @ResponseBody
    public ResponseResult eduException(PiaoException e) {
        e.printStackTrace();
        return ResponseResult.error().code(e.getCode()).message(e.getMessage());
    }
}
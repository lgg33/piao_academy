package com.lg.common.response;

import lombok.Getter;

/**
 * @author L
 * @version 1.0
 * @ClassName: ResultCode
 * @date: 2019/12/30 20:05
 * @since JDK 1.8
 */
@Getter
public enum ResultCode {
    SUCCESS(true, 20000,"成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),
    Arithmetic_Exception(false, 20002, "除数不能为0"),
    BAD_SQL_GRAMMAR(false, 20003, "sql语法错误"),
    JSON_PARSE_ERROR(false, 20004, "json解析异常"),
    PARAM_ERROR(false, 20005,"参数不正确"),
    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    EXCEL_DATA_ERROR(false, 21005, "Excel数据导入错误");


    private Boolean success;

    private Integer code;

    private String message;

    ResultCode(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}

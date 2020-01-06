package com.lg.common.exception;

import com.lg.common.response.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author L
 * @version 1.0
 * @ClassName: MyException
 * @date: 2019/12/31 11:20
 * @since JDK 1.8
 */
@Data
@ApiModel(value = "自定义异常")
public class PiaoException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public PiaoException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    /**
     * 接收枚举类型
     * @param resultCode
     */
    public PiaoException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}

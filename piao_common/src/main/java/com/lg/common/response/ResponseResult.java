package com.lg.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author L
 * @version 1.0
 * @ClassName: ResponseResult
 * @date: 2019/12/30 20:08
 * @since JDK 1.8
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class ResponseResult {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    private ResponseResult(){}

    public static ResponseResult success(){
        ResponseResult r = new ResponseResult();
        r.setSuccess(ResultCode.SUCCESS.getSuccess());
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        return r;
    }

    public static ResponseResult error(){
        ResponseResult r = new ResponseResult();
        r.setSuccess(ResultCode.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCode.UNKNOWN_REASON.getCode());
        r.setMessage(ResultCode.UNKNOWN_REASON.getMessage());
        return r;
    }

    public static ResponseResult setResult(ResultCode resultCode){
        ResponseResult r = new ResponseResult();
        r.setSuccess(resultCode.getSuccess());
        r.setCode(resultCode.getCode());
        r.setMessage(resultCode.getMessage());
        return r;
    }

    public ResponseResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ResponseResult message(String message){
        this.setMessage(message);
        return this;
    }

    public ResponseResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResponseResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ResponseResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}

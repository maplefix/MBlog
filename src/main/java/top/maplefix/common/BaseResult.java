package top.maplefix.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.constant.Constant;

/**
 * @author : Maple
 * @description : 通用数据返回对象
 * @date : 2020/1/16 9:54
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResult {
    /**
     * 返回应答码
     */
    private int  code;
    /**
     * 返回应答信息
     */
    private String msg;
    /**
     * 返回数据域
     */
    private Object data;


    /**
     * 初始化一个BaseResult对象,无data对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个BaseResult对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public BaseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static BaseResult success() {
        return BaseResult.success(Constant.SUCCESS_MSG);
    }
    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static BaseResult success(String msg) {
        return BaseResult.success(msg, null);
    }
    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static BaseResult success(Object msg) {
        return BaseResult.success(Constant.SUCCESS_MSG, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static BaseResult success(String msg, Object data) {
        return new BaseResult(ResultCode.OK, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 警告消息
     */
    public static BaseResult error() {
        return BaseResult.error(Constant.FAIL_MSG);
    }
    /**
     * 返回错误消息
     *
     * @return 警告消息
     */
    public static BaseResult error(String msg) {
        return BaseResult.error(msg,null);
    }
    /**
     * 返回错误消息
     *
     * @return 警告消息
     */
    public static BaseResult error(String msg,Object data) {
        return BaseResult.error(ResultCode.INTERNAL_SERVER_ERROR,msg,data);
    }
    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static BaseResult error(int code,String msg, Object data) {
        return new BaseResult(code, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static BaseResult error(int code, String msg) {
        return new BaseResult(code, msg, null);
    }

}

package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 操作日志实体
 * @date : Created in 2019/7/24 0:05
 * @editor:
 * @version: v2.1
 */
@Data
@Table(name = "t_operation_log")
@NameStyle
public class OperationLog implements Serializable {

    /**
     * 操作日志表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    private String operId;
    /**
     * 模块名
     */
    private String module;
    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private String businessType;
    /**
     * 方法名
     */
    private String method;
    /**
     * 操作ip
     */
    private String operIp;
    /**
     * 操作地区
     */
    private String operLocation;
    /**
     * 操作浏览器
     */
    private String operBrowser;
    /**
     * 操作系统
     */
    private String operOs;
    /**
     * 操作人名称
     */
    private String operName;
    /**
     * 操作地址
     */
    private String operUrl;

    /**
     * 请求参数
     */
    private String operParam;
    /**
     * 请求状态(0:成功,1:失败)
     */
    private String status;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 操作日期
     */
    private String operDate;
}
package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 用户表实体类
 * @date : Created in 2020/1/15 15:09
 */
@Data
@Table(name = "t_user")
public class User implements Serializable {

    /**
     * 用户表主键
     */
    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String userId;
    /**
     * 登录名
     */
    @Excel(name = "登陆名称")
    private String loginName;
    /**
     * 用户名
     */
    @Excel(name = "用户名称")
    private String userName;
    /**
     * 邮箱
     */
    @Excel(name = "邮件地址")
    private String email;
    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;
    /**
     * 性别
     */
    @Excel(name = "性别",readConverterExp = "0=女,1=男")
    private String gender;
    /**
     * 头像相对路径
     */
    @Excel(name = "头像地址")
    private String avatar;
    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;
    /**
     * 帐号状态(0停用,1正常)
     */
    @Excel(name = "用户状态", readConverterExp = "0=停用,1=正常")
    private Integer status;
    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP")
    private String loginIp;
    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间")
    private String loginDate;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private String createDate;
    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    private String updateDate;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


}

package top.maplefix.vo.server;

import lombok.Data;

/**
 * @author : Maple
 * @description : 系统文件相关信息
 * @date : Created in 2019/9/15 16:17
 * @version : v2.1
 */
@Data
public class SysFile {
    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private double usage;
}

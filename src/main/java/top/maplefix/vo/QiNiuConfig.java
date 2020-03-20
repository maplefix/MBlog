package top.maplefix.vo;

import lombok.Data;
import top.maplefix.utils.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Maple
 * @description 七牛云图床配置
 * @date 2020/3/16 10:18
 */
@Data
public class QiNiuConfig implements Serializable {

    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @NotBlank(message = "Access Key 不能为空")
    private String accessKey;
    /**
     * 一个账号最多拥有两对密钥(Access/Secret Key)
     */
    @NotBlank(message = "Secret Key 不能为空")
    private String secretKey;
    /**
     * 存储空间名称作为唯一的 Bucket 识别符
     */
    @NotBlank(message = "Bucket 不能为空")
    private String bucket;
    /**
     * Zone表示与机房的对应关系
     * 华东	Zone.zone0()
     * 华北	Zone.zone1()
     * 华南	Zone.zone2()
     * 北美	Zone.zoneNa0()
     * 东南亚	Zone.zoneAs0()
     */
    @NotBlank(message = "Zone 不能为空")
    private String zone;
    /**
     * 外链域名，可自定义，需在七牛云绑定
     */
    @NotBlank(message = "外链域名不能为空")
    private String host;
    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

    public boolean check() {
        return StringUtils.isNotEmpty(accessKey)
                && StringUtils.isNotEmpty(secretKey)
                && StringUtils.isNotEmpty(bucket)
                && StringUtils.isNotEmpty(zone)
                && StringUtils.isNotEmpty(host)
                && StringUtils.isNotEmpty(type);
    }
}

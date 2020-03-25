package top.maplefix.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.annotation.Excel;
import top.maplefix.utils.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maple
 * @description 实体类基类
 * @date 2020/2/2 14:33
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity implements Serializable {

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间")
    private String createDate;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间")
    private String updateDate;
    /**
     * 请求参数
     */
    @JsonIgnore
    private String params;

    public Map<String,Object> getParams(){
        if(StringUtils.isEmpty(params)){
            return new HashMap<>(16);
        }else {
            return JSON.parseObject(params,Map.class);
        }
    }
}

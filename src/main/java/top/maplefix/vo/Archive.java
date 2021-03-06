package top.maplefix.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.maplefix.model.Blog;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Maple
 * @description : 归档实体
 * @date : 2019/9/14 21:05
 * @version : v1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Archive implements Serializable {

    /**
     * 博客创建时间
     */
    private String createDate;
    /**
     * 总数
     */
    private Integer count;
    /**
     * 博客列表
     */
    private List<Blog> blogList;
}

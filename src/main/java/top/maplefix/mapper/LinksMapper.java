package top.maplefix.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
import top.maplefix.model.Links;

/**
 * @author : Maple
 * @description : 友链mapper
 * @date : Created in 2019/7/25 0:32
 * @editor:
 * @version: v2.1
 */
@CacheNamespace
public interface LinksMapper extends Mapper<Links> {

}

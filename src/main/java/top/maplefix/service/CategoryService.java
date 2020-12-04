package top.maplefix.service;

import top.maplefix.model.Category;

import java.util.List;

/**
 * @author : Maple
 * @description : 博客分类接口
 * @date : 2020/1/15 15:46
 */
public interface CategoryService {
    /**
     * 查询分类
     *
     * @param id 分类ID
     * @return 分类
     */
    Category selectCategoryById(Long id);

    /**
     * 查询分类列表
     *
     * @param bgCategory 分类
     * @return 分类集合
     */
    List<Category> selectCategoryList(Category bgCategory);

    /**
     * 新增分类
     *
     * @param bgCategory 分类
     * @return 结果
     */
    int insertCategory(Category bgCategory);

    /**
     * 修改分类
     *
     * @param bgCategory 分类
     * @return 结果
     */
    int updateCategory(Category bgCategory);

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCategoryByIds(String ids);

    /**
     * 删除分类信息
     *
     * @param id 分类ID
     * @return 结果
     */
    int deleteCategoryById(Long id);

    /**
     * 获取support的分类
     *
     * @return list
     */
    List<Category> selectSupportCategory();
}

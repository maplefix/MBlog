package top.maplefix.service;


import top.maplefix.model.Book;

import java.util.List;

/**
 * @author : Maple
 * @description: 图书service
 * @date : 2020/1/15 14:36
 */
public interface BookService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Book selectBookById(String  id);

    /**
     * 新增数据
     *
     * @param book 实例对象
     * @return 受影响的行
     */
    int insertBook(Book book);

    /**
     * 修改数据
     *
     * @param book 实例对象
     * @return 受影响的行数
     */
    int updateBook(Book book);

    /**
     * 获取Book 集合
     *
     * @param book book查询条件
     * @return list
     */
    List<Book> selectBookList(Book book);

    /**
     * 根据id删除所有的book
     *
     * @param ids id
     * @return 受影响的行数
     */
    int deleteBookByIds(String ids);

    /**
     * 喜欢图书id
     *
     * @param id id
     * @return 受影响的行数
     */
    int likeBook(String id);

    /**
     * 获取Book详细
     *
     * @param id id
     * @return Book
     */
    Book getBookDetail(String id);
}

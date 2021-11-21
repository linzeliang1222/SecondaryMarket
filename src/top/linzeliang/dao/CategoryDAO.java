package top.linzeliang.dao;

import top.linzeliang.domain.Category;

import java.util.List;

/**
 * @Description: Category对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface CategoryDAO {
    /**
     * 获取分类总数量
     *
     * @return: int
     */
    int getTotalCategory();

    void addCategory(Category category);

    void deleteCategory(int id);

    void updateCategory(Category category);

    /**
     * 根据id获取分类
     *
     * @param: id
     * @return: Category
     */
    Category getCategoryById(int id);

    /**
     * 获取分类集合
     *
     * @param: start
     * @param: count
     * @return: List<Category>
     */
    List<Category> listCategory(int start, int count);
}

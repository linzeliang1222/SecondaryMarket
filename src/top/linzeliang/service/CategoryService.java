package top.linzeliang.service;

import top.linzeliang.domain.Category;

import java.util.List;

/**
 * @Description: TODO
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface CategoryService {
    int getTotalCategory();

    void addCategory(Category category);

    void deleteCategory(int id);

    void updateCategory(Category category);

    Category getCategoryById(int id);

    List<Category> findAllCategory();

    List<Category> findSomeCategory(int start, int count);
}

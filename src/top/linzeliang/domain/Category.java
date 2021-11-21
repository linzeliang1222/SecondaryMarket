package top.linzeliang.domain;

import java.util.List;

/**
 * @Description: Category实体类
 * @Author: LinZeLiang
 * @Date: 2021-02-08
 */
public class Category {
    private int id;
    private String name;
    /**
     * 该分类下总的产品
     */
    private List<Product> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

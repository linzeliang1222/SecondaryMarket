package top.linzeliang.service;

import top.linzeliang.domain.Category;
import top.linzeliang.domain.Product;

import java.util.List;

/**
 * @Description: TODO
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface ProductService {
    int getTotalProduct();

    int getTotalProductByCategory(int cid);

    void addProduct(Product product);

    void deleteProduct(int id);

    void updateProduct(Product product);

    Product getProductById(int id);

    Product getProductByIdAndValid(int id);

    List<Product> findAllProduct();

    List<Product> findSomeProduct(int start, int count);

    List<Product> findAllProductByValid();

    List<Product> findSomeProductByValid(int start, int count);

    List<Product> findAllProductByCategory(int cid);

    List<Product> findSomeProductByCategory(int cid, int start, int count);

    List<Product> findAllProductByCategoryAndValid(int cid);

    List<Product> findSomeProductByCategoryAndValid(int cid, int start, int count);

    List<Product> findAllProductByUser(int uid);

    List<Product> findSomeProductByUser(int uid, int start, int count);

    List<Product> findAllProductByUserAndValid(int uid);

    List<Product> findSomeProductByUserAndValid(int uid, int start, int count);

    List<Product> findAllProductByCreateDate();

    List<Product> findSomeProductByCreateDate(int start, int count);

    /**
     * 根据关键字查找产品
     *
     * @param: keyword 查询的关键字
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> searchAllProduct(String keyword);

    /**
     * 根据关键字查找部分范围产品
     *
     * @param: keyword 查询的关键字
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> searchSomeProduct(String keyword, int start, int count);

    /**
     * 查找指定价格范围的所有产品
     *
     * @param: beginPrice
     * @param: endPrice
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> findAllProductByPrice(int beginPrice, int endPrice);

    /**
     * 查找指定价格范围的部分产品
     *
     * @param: beginPrice
     * @param: endPrice
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> findSomeProductByPrice(int beginPrice, int endPrice, int start, int count);

    /**
     * 根据分类查找指定价格范围的产品
     *
     * @param: cid
     * @param: beginPrice
     * @param: endPrice
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> findAllProductByCategoryAndPrice(int cid, int beginPrice, int endPrice);

    /**
     * 根据分类查找指定价格范围的部分产品
     *
     * @param: cid
     * @param: beginPrice
     * @param: endPrice
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> findSomeProductByCategoryAndPrice(int cid, int beginPrice, int endPrice, int start, int count);

    /**
     * 根据关键字查找指定价格范围的产品
     *
     * @param: keyword 查询的关键字
     * @param: beginPrice
     * @param: endPrice
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> searchAllProductByPrice(String keyword, int beginPrice, int endPrice);

    /**
     * 根据关键字查找指定价格范围的部分产品
     *
     * @param: keyword 查询的关键字
     * @param: beginPrice
     * @param: endPrice
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> searchSomeProductByPrice(String keyword, int beginPrice, int endPrice, int start, int count);

    /**
     * 初始化传入的分类集合，将产品添加到属于它的分类下
     *
     * @param: categories
     */
    void fillByCategories(List<Category> categories);

    /**
     * 将产品添加到分类category下
     *
     * @param: category
     */
    void fillByCategory(Category category);

    /**
     * 设置产品的第一张图片
     *
     * @param: product
     */
    void setFirstProductImage(Product product);

    /**
     * 设置产品的销售进度
     *
     * @param: product
     */
    public void setProductProgress(Product product);

    /**
     * 获取产品对应的订单id
     *
     * @param: pid
     * @param: start
     * @param: count
     * @return: String
     */
    public int getOrderIdByProduct(int pid);
}

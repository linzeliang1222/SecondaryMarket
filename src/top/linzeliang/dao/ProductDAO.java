package top.linzeliang.dao;

import top.linzeliang.domain.Product;

import java.util.List;

/**
 * @Description: Product对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface ProductDAO {
    /**
     * 查询某分类下的产品总数
     *
     * @param: cid
     * @return: int
     */
    int getTotalProduct();

    /**
     * 查询某分类下的产品总数
     *
     * @param: cid
     * @return: int
     */
    int getTotalProductByCategory(int cid);

    void addProduct(Product product);

    void deleteProduct(int id);

    void updateProduct(Product product);

    Product getProductById(int id);

    Product getProductByIdAndValid(int id);

    /**
     * 获取产品集合
     *
     * @param: start
     * @param: count
     * @return: List<Product>
     */
    List<Product> listProduct(int start, int count);

    /**
     * 通过cid获取某分类下的产品集合
     *
     * @param: cid
     * @param: start
     * @param: count
     * @return: List<Product>
     */
    List<Product> listProductByCategory(int cid, int start, int count);

    /**
     * 通过uid获取某用户的产品集合
     *
     * @param: uid
     * @param: start
     * @param: count
     * @return: List<Product>
     */
    List<Product> listProductByUser(int uid, int start, int count);

    /**
     * 按照创建日期获取产品集合
     *
     * @param: start
     * @param: count
     * @return: List<Product>
     */
    List<Product> listProductByCreateDate(int start, int count);

    /**
     * 根据关键字查找产品
     *
     * @param: keyword 查询的关键字
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> searchProduct(String keyword, int start, int count);

    /**
     * 查找指定价格范围的产品
     *
     * @param: beginPrice
     * @param: endPrice
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> listProductByPrice(int beginPrice, int endPrice, int start, int count);

    /**
     * 根据分类查找指定价格范围的产品
     *
     * @param: cid
     * @param: beginPrice
     * @param: endPrice
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> listProductByCategoryAndPrice(int cid, int beginPrice, int endPrice, int start, int count);

    /**
     * 通过关键字查找指定价格范围的产品
     *
     * @param: keyword
     * @param: beginPrice
     * @param: endPrice
     * @param: start
     * @param: count
     * @return: List<Product> 得到的结果就是查询后的结果
     */
    List<Product> searchProductByPrice(String keyword, int beginPrice, int endPrice, int start, int count);

    /**
     * 获取用户有效的产品
     *
     * @param: uid
     * @param: start
     * @param: count
     * @return: List<Product>
     */
    public List<Product> listProductByUserAndValid(int uid, int start, int count);

    /**
     * 获取有效的产品
     *
     * @param: start
     * @param: count
     * @return: List<Product>
     */
    public List<Product> listProductByValid(int start, int count);

    /**
     * 获取分类下有效的产品
     *
     * @param: cid
     * @param: start
     * @param: count
     * @return: List<Product>
     */
    public List<Product> listProductByCategoryAndValid(int cid, int start, int count);

    /**
     * 获取产品的销售进度
     *
     * @param: pid
     * @param: start
     * @param: count
     * @return: String
     */
    public String getProductProgress(int pid);

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

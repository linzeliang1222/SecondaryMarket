package top.linzeliang.dao;

import top.linzeliang.domain.Product;
import top.linzeliang.domain.ProductImage;

import java.util.List;

/**
 * @Description: ProductImage对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface ProductImageDAO {
    /**
     * 表示单个图片
     */
    public static final String type_single = "type_single";
    /**
     * 表示详情图片
     */
    public static final String type_detail = "type_detail";

    /**
     * 添加图片
     *
     * @param: productImage
     */
    void addProductImage(ProductImage productImage);

    /**
     * 根据id删除图片
     *
     * @param: id
     */
    void deleteProductImage(int id);

    /**
     * 根据id获取一个图片对象
     *
     * @param: id
     * @return: ProductImage
     */
    ProductImage getProductImageById(int id);

    /**
     * 查询指定产品p的type类型的图片
     *
     * @param: product
     * @param: type
     * @param: start
     * @param: count
     * @return: List<ProductImage>
     */
    List<ProductImage> listProductImage(Product product, String type, int start, int count);
}

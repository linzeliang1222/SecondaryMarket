package top.linzeliang.service;

import top.linzeliang.domain.Product;
import top.linzeliang.domain.ProductImage;

import java.util.List;

/**
 * @Description: TODO
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface ProductImageService {
    void addProductImage(ProductImage productImage);

    void deleteProductImage(int id);

    ProductImage getProductImageById(int id);

    List<ProductImage> findAllProductImage(Product product, String type);

    List<ProductImage> findSomeProductImage(Product product, String type, int start, int count);

    /**
     * 设置填充产品的图片
     */
    void setProductImageWithProduct(Product product);
}

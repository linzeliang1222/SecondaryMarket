package top.linzeliang.service.impl;

import top.linzeliang.dao.ProductImageDAO;
import top.linzeliang.dao.impl.ProductImageDAOImpl;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.ProductImage;
import top.linzeliang.service.ProductImageService;

import java.util.List;

public class ProductImageServiceImpl implements ProductImageService {

    final private ProductImageDAO productImageDAO = new ProductImageDAOImpl();

    @Override
    public void addProductImage(ProductImage productImage) {
        productImageDAO.addProductImage(productImage);
    }

    @Override
    public void deleteProductImage(int id) {
        productImageDAO.deleteProductImage(id);
    }

    @Override
    public ProductImage getProductImageById(int id) {
        return productImageDAO.getProductImageById(id);
    }

    @Override
    public List<ProductImage> findAllProductImage(Product product, String type) {
        return productImageDAO.listProductImage(product, type, 0, Short.MAX_VALUE);
    }

    @Override
    public List<ProductImage> findSomeProductImage(Product product, String type, int start, int count) {
        return productImageDAO.listProductImage(product, type, start, count);
    }

    @Override
    public void setProductImageWithProduct(Product product) {
        List<ProductImage> productSingleImages = productImageDAO.listProductImage(product,
                ProductImageDAO.type_single, 0, Short.MAX_VALUE);
        List<ProductImage> productDetailImages = productImageDAO.listProductImage(product,
                ProductImageDAO.type_detail, 0, Short.MAX_VALUE);
        if (!productSingleImages.isEmpty()) {
            product.setProductSingleImages(productSingleImages);
        }
        if (!productDetailImages.isEmpty()) {
            product.setProductDetailImages(productDetailImages);
        }
    }
}

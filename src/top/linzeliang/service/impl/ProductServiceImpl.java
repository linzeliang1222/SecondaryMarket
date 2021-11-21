package top.linzeliang.service.impl;

import top.linzeliang.dao.ProductDAO;
import top.linzeliang.dao.ProductImageDAO;
import top.linzeliang.dao.impl.ProductDAOImpl;
import top.linzeliang.dao.impl.ProductImageDAOImpl;
import top.linzeliang.domain.Category;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.ProductImage;
import top.linzeliang.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    final private ProductDAO productDAO = new ProductDAOImpl();
    final private ProductImageDAO productImageDAO = new ProductImageDAOImpl();

    @Override
    public int getTotalProduct() {
        return productDAO.getTotalProduct();
    }

    @Override
    public int getTotalProductByCategory(int cid) {
        return productDAO.getTotalProductByCategory(cid);
    }

    @Override
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    @Override
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }

    @Override
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    @Override
    public Product getProductById(int id) {
        Product product = productDAO.getProductById(id);
        // 设置产品的第一个图片
        setFirstProductImage(product);

        return product;
    }

    @Override
    public Product getProductByIdAndValid(int id) {
        Product product = productDAO.getProductByIdAndValid(id);
        // 设置产品的第一个图片
        setFirstProductImage(product);

        return product;
    }

    @Override
    public List<Product> findAllProduct() {
        return findSomeProduct(0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProduct(int start, int count) {
        List<Product> products = productDAO.listProduct(start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByValid() {
        return findSomeProductByValid(0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByValid(int start, int count) {
        List<Product> products = productDAO.listProductByValid(start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByCategory(int cid) {
        return findSomeProductByCategory(cid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByCategory(int cid, int start, int count) {
        List<Product> products = productDAO.listProductByCategory(cid, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByCategoryAndValid(int cid) {
        return findSomeProductByCategoryAndValid(cid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByCategoryAndValid(int cid, int start, int count) {
        List<Product> products = productDAO.listProductByCategoryAndValid(cid, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByUser(int uid) {
        return findSomeProductByUser(uid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByUser(int uid, int start, int count) {
        List<Product> products = productDAO.listProductByUser(uid, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByUserAndValid(int uid) {
        return findSomeProductByUserAndValid(uid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByUserAndValid(int uid, int start, int count) {
        List<Product> products = productDAO.listProductByUserAndValid(uid, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByCreateDate() {
        return productDAO.listProductByCreateDate(0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByCreateDate(int start, int count) {
        List<Product> products = productDAO.listProductByCreateDate(start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> searchAllProduct(String keyword) {
        return searchSomeProduct(keyword, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> searchSomeProduct(String keyword, int start, int count) {
        List<Product> products = productDAO.searchProduct(keyword, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByPrice(int beginPrice, int endPrice) {
        return findSomeProductByPrice(beginPrice, endPrice, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByPrice(int beginPrice, int endPrice, int start, int count) {
        List<Product> products = productDAO.listProductByPrice(beginPrice, endPrice, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> findAllProductByCategoryAndPrice(int cid, int beginPrice, int endPrice) {
        return findSomeProductByCategoryAndPrice(cid, beginPrice, endPrice, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> findSomeProductByCategoryAndPrice(int cid, int beginPrice, int endPrice, int start, int count) {
        List<Product> products = productDAO.listProductByCategoryAndPrice(cid, beginPrice, endPrice, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public List<Product> searchAllProductByPrice(String keyword, int beginPrice, int endPrice) {
        return searchSomeProductByPrice(keyword, beginPrice, endPrice, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Product> searchSomeProductByPrice(String keyword, int beginPrice, int endPrice, int start, int count) {
        List<Product> products = productDAO.searchProductByPrice(keyword, beginPrice, endPrice, start, count);
        for (Product product : products) {
            setFirstProductImage(product);
        }

        return products;
    }

    @Override
    public void fillByCategories(List<Category> categories) {
        for (Category category : categories) {
            fillByCategory(category);
        }
    }

    @Override
    public void fillByCategory(Category category) {
        List<Product> products = productDAO.listProductByCategory(category.getId(), 0, Short.MAX_VALUE);
        category.setProducts(products);
    }

    @Override
    public void setFirstProductImage(Product product) {
        final List<ProductImage> productImages = productImageDAO.listProductImage(product, ProductImageDAO.type_single, 0, Short.MAX_VALUE);
        // 有图片的情况下，将第一张作为默认的图片
        if (!productImages.isEmpty()) {
            product.setFirstProductImage(productImages.get(0));
            // 设置产品销售进度
            setProductProgress(product);
        }
    }

    @Override
    public void setProductProgress(Product product) {
        String productProgress = productDAO.getProductProgress(product.getId());
        product.setProgress(productProgress);
    }

    @Override
    public int getOrderIdByProduct(int pid) {
        return productDAO.getOrderIdByProduct(pid);
    }
}

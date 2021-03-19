package top.linzeliang.dao.impl;

import top.linzeliang.dao.ProductImageDAO;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.ProductImage;
import top.linzeliang.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAOImpl implements ProductImageDAO {
    @Override
    public void addProductImage(ProductImage productImage) {
        String sql = "INSERT INTO productimage VALUES (NULL, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setInt(1, productImage.getProduct().getId());
            pstmt.setString(2, productImage.getType());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                productImage.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProductImage(int id) {
        String sql = "DELETE FROM productimage WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductImage getProductImageById(int id) {
        ProductImage productImage = null;

        String sql = "SELECT * FROM productimage WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                productImage = new ProductImage();

                int pid = rs.getInt("pid");
                Product product = new ProductDAOImpl().getProductById(pid);
                String type = rs.getString("type");

                productImage.setId(id);
                productImage.setProduct(product);
                productImage.setType(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productImage;
    }

    @Override
    public List<ProductImage> listProductImage(Product product, String type, int start, int count) {
        List<ProductImage> productImages = new ArrayList<>();

        String sql = "SELECT * FROM productimage WHERE pid = ? AND type = ? ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, product.getId());
            pstmt.setString(2, type);
            pstmt.setInt(3, start);
            pstmt.setInt(4, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductImage productImage = new ProductImage();

                int id = rs.getInt(1);

                productImage.setId(id);
                productImage.setProduct(product);
                productImage.setType(type);

                productImages.add(productImage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productImages;
    }
}

package top.linzeliang.dao.impl;

import top.linzeliang.dao.ProductDAO;
import top.linzeliang.domain.Category;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.User;
import top.linzeliang.util.DBUtil;
import top.linzeliang.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public int getTotalProduct() {
        int totalProduct = 0;

        try (
                Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
        ) {

            String sql = "SELECT COUNT(*) FROM product";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                totalProduct = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalProduct;
    }

    @Override
    public int getTotalProductByCategory(int cid) {
        int totalProductByCategory = 0;

        String sql = "SELECT COUNT(*) FROM product WHERE cid = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, cid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalProductByCategory = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalProductByCategory;
    }

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO product VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setString(1, product.getName());
            if (null != product.getUser()) {
                pstmt.setInt(2, product.getUser().getId());
            } else {
                pstmt.setInt(2, -1);
            }
            pstmt.setFloat(3, product.getOriginalPrice());
            pstmt.setFloat(4, product.getPromotePrice());
            pstmt.setInt(5, product.getCategory().getId());
            pstmt.setString(6, product.getDescription());
            pstmt.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));
            pstmt.setInt(8, product.getStatus());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                product.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE id = ?";

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
    public void updateProduct(Product product) {
        String sql = "UPDATE product SET name = ?, uid = ?, originalPrice = ?, promotePrice = ?, cid = ?, description = ?, createDate = ?, status = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getUser().getId());
            pstmt.setFloat(3, product.getOriginalPrice());
            pstmt.setFloat(4, product.getPromotePrice());
            pstmt.setInt(5, product.getCategory().getId());
            pstmt.setString(6, product.getDescription());
            pstmt.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));
            pstmt.setInt(8, product.getStatus());
            pstmt.setInt(9, product.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;

        String sql = "SELECT * FROM product WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = new Product();

                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public Product getProductByIdAndValid(int id) {
        Product product = null;

        String sql = "SELECT * FROM product WHERE id = ? AND status = 1";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = new Product();

                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public List<Product> listProduct(int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByCategory(int cid, int start, int count) {
        List<Product> products = new ArrayList<>();

        // 由于该cid分类下的产品的分类都是同一个对象，只需创建一个即可
        Category category = new CategoryDAOImpl().getCategoryById(cid);

        String sql = "SELECT * FROM product WHERE cid = ? ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, cid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByUser(int uid, int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE uid = ? ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByCreateDate(int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE status = 1 ORDER BY createDate DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> searchProduct(String keyword, int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE name LIKE ? AND status = 1 LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, "%" + keyword.trim() + "%");
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByPrice(int beginPrice, int endPrice, int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE promotePrice >= ? AND promotePrice <= ? AND status = 1 ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, beginPrice);
            pstmt.setInt(2, endPrice);
            pstmt.setInt(3, start);
            pstmt.setInt(4, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByCategoryAndPrice(int cid, int beginPrice, int endPrice, int start, int count) {
        List<Product> products = new ArrayList<>();

        // 由于该cid分类下的产品的分类都是同一个对象，只需创建一个即可
        Category category = new CategoryDAOImpl().getCategoryById(cid);

        String sql = "SELECT * FROM product WHERE promotePrice >= ? AND promotePrice <= ? AND cid = ? AND status = 1 ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, beginPrice);
            pstmt.setInt(2, endPrice);
            pstmt.setInt(3, cid);
            pstmt.setInt(4, start);
            pstmt.setInt(5, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                int status = rs.getInt("status");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> searchProductByPrice(String keyword, int beginPrice, int endPrice, int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE name LIKE ? AND promotePrice >= ? AND promotePrice <= ? AND status = 1 LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, "%" + keyword.trim() + "%");
            pstmt.setInt(2, beginPrice);
            pstmt.setInt(3, endPrice);
            pstmt.setInt(4, start);
            pstmt.setInt(5, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByUserAndValid(int uid, int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE uid = ? AND status = 1 ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByValid(int start, int count) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM product WHERE status = 1 ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int cid = rs.getInt("cid");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Category category = new CategoryDAOImpl().getCategoryById(cid);
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> listProductByCategoryAndValid(int cid, int start, int count) {
        List<Product> products = new ArrayList<>();

        // 由于该cid分类下的产品的分类都是同一个对象，只需创建一个即可
        Category category = new CategoryDAOImpl().getCategoryById(cid);

        String sql = "SELECT * FROM product WHERE cid = ? AND status = 1 ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, cid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                int uid = rs.getInt("uid");
                float originalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int status = rs.getInt("status");
                String description = rs.getString("description");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                User user = new UserDAOImpl().getUserById(uid);

                product.setId(id);
                product.setName(name);
                product.setUser(user);
                product.setOriginalPrice(originalPrice);
                product.setPromotePrice(promotePrice);
                product.setCategory(category);
                product.setDescription(description);
                product.setCreateDate(createDate);
                product.setStatus(status);

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public String getProductProgress(int pid) {
        String progress = "";

        String sql = "SELECT t3.status FROM product t1 JOIN orderitem t2 ON t1.id = t2.pid AND t2.oid != -1 AND t1.id = ? JOIN order_ t3 ON t2.oid = t3.id";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, pid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                progress = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progress;
    }

    @Override
    public int getOrderIdByProduct(int pid) {

        int orderId = -1;

        String sql = "SELECT t2.oid FROM product t1 JOIN orderitem t2 ON t1.id = t2.pid AND t1.id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, pid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderId;
    }
}

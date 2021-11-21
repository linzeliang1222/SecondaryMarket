package top.linzeliang.dao.impl;

import top.linzeliang.dao.CategoryDAO;
import top.linzeliang.domain.Category;
import top.linzeliang.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public int getTotalCategory() {
        int totalCategory = 0;

        // try括号内的资源会在try语句结束后自动释放（前提是这些资源必须实现AutoCloseable接口）
        try (
                Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
        ) {

            String sql = "SELECT COUNT(*) FROM category";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                totalCategory = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalCategory;
    }

    @Override
    public void addCategory(Category category) {
        String sql = "INSERT INTO category VALUES(NULL, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setString(1, category.getName());

            pstmt.executeUpdate();

            // 获取主键
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                // 同时把数据库生成的id回写记录到category中
                int id = rs.getInt(1);
                category.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(int id) {
        String sql = "DELETE FROM category WHERE id = ?";

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
    public void updateCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, category.getName());
            pstmt.setInt(2, category.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = null;

        String sql = "SELECT * FROM category WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                category = new Category();

                String name = rs.getString("name");

                category.setId(id);
                category.setName(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> listCategory(int start, int count) {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM category ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                int id = rs.getInt(1);
                String name = rs.getString("name");

                category.setId(id);
                category.setName(name);

                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}

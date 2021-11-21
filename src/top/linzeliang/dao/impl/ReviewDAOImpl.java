package top.linzeliang.dao.impl;

import top.linzeliang.dao.ReviewDAO;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.Review;
import top.linzeliang.domain.User;
import top.linzeliang.util.DBUtil;
import top.linzeliang.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {
    @Override
    public int getTotalReviewByUser(int uid) {
        int totalReviewByUser = 0;

        String sql = "SELECT COUNT(*) FROM review WHERE uid = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalReviewByUser = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalReviewByUser;
    }

    @Override
    public void addReview(Review review) {
        String sql = "INSERT INTO review VALUES (NULL, ?, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setInt(1, review.getUser().getId());
            pstmt.setInt(2, review.getProduct().getId());
            pstmt.setString(3, review.getContent());
            pstmt.setTimestamp(4, DateUtil.d2t(review.getCreateDate()));

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                review.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReview(int id) {
        String sql = "DELETE FROM review WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Review getReviewById(int id) {
        Review review = null;

        String sql = "SELECT * FROM review WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                review = new Review();

                int uid = rs.getInt("uid");
                User user = new UserDAOImpl().getUserById(uid);
                int pid = rs.getInt("pid");
                Product product = new ProductDAOImpl().getProductById(pid);
                String content = rs.getString("content");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                review.setId(id);
                review.setUser(user);
                review.setProduct(product);
                review.setContent(content);
                review.setCreateDate(createDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return review;
    }

    @Override
    public List<Review> listReview(int uid, int start, int count) {
        List<Review> reviews = new ArrayList<>();

        String sql = "SELECT t1.id, t1.uid, t1.pid, content, t1.createDate " +
                "FROM review t1 JOIN product t2 ON t1.pid = t2.id " +
                "WHERE t2.uid = ? ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Review review = new Review();

                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int buyerId = rs.getInt("uid");
                User user = new UserDAOImpl().getUserById(buyerId);
                Product product = new ProductDAOImpl().getProductById(pid);
                String content = rs.getString("content");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));

                review.setId(id);
                review.setUser(user);
                review.setProduct(product);
                review.setContent(content);
                review.setCreateDate(createDate);

                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}

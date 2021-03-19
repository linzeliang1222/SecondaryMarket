package top.linzeliang.dao.impl;

import top.linzeliang.dao.OrderDAO;
import top.linzeliang.domain.Order;
import top.linzeliang.domain.User;
import top.linzeliang.util.DBUtil;
import top.linzeliang.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public int getTotalOrder() {
        int totalOrder = 0;

        try (
                Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
        ) {

            String sql = "SELECT COUNT(*) FROM order_";

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                totalOrder = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalOrder;
    }

    @Override
    public int getTotalOrderByUser(int uid) {
        int totalOrder = 0;

        String sql = "SELECT COUNT(*) FROM order_ WHERE uid = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalOrder = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalOrder;
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO order_ VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setInt(1, order.getUser().getId());
            pstmt.setString(2, order.getOrderCode());
            pstmt.setString(3, order.getAddress());
            pstmt.setString(4, order.getPost());
            pstmt.setString(5, order.getReceiver());
            pstmt.setString(6, order.getMobile());
            pstmt.setString(7, order.getUserMessage());
            pstmt.setTimestamp(8, DateUtil.d2t(order.getCreateDate()));
            pstmt.setTimestamp(9, DateUtil.d2t(order.getPayDate()));
            pstmt.setTimestamp(10, DateUtil.d2t(order.getDeliveryDate()));
            pstmt.setTimestamp(11, DateUtil.d2t(order.getConfirmDate()));
            pstmt.setString(12, order.getStatus());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                order.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int id) {
        String sql = "DELETE FROM order_ WHERE id = ?";

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
    public void updateOrder(Order order) {
        String sql = "UPDATE order_ SET uid = ?, orderCode = ?, address = ?, post = ?, receiver = ?, mobile = ?, userMessage = ?, createDate = ?, payDate = ?" +
                ", deliveryDate = ?, confirmDate = ?, status = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, order.getUser().getId());
            pstmt.setString(2, order.getOrderCode());
            pstmt.setString(3, order.getAddress());
            pstmt.setString(4, order.getPost());
            pstmt.setString(5, order.getReceiver());
            pstmt.setString(6, order.getMobile());
            pstmt.setString(7, order.getUserMessage());
            pstmt.setTimestamp(8, DateUtil.d2t(order.getCreateDate()));
            pstmt.setTimestamp(9, DateUtil.d2t(order.getPayDate()));
            pstmt.setTimestamp(10, DateUtil.d2t(order.getDeliveryDate()));
            pstmt.setTimestamp(11, DateUtil.d2t(order.getConfirmDate()));
            pstmt.setString(12, order.getStatus());
            pstmt.setInt(13, order.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrderById(int id) {
        Order order = null;

        String sql = "SELECT * FROM order_ WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                order = new Order();

                int uid = rs.getInt("uid");
                User user = new UserDAOImpl().getUserById(uid);
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                java.util.Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                java.util.Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                java.util.Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                java.util.Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                String status = rs.getString("status");

                order.setId(id);
                order.setUser(user);
                order.setOrderCode(orderCode);
                order.setAddress(address);
                order.setPost(post);
                order.setReceiver(receiver);
                order.setMobile(mobile);
                order.setUserMessage(userMessage);
                order.setCreateDate(createDate);
                order.setPayDate(payDate);
                order.setDeliveryDate(deliveryDate);
                order.setConfirmDate(confirmDate);
                order.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public List<Order> listOrder(int start, int count) {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM order_ ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();

                int id = rs.getInt(1);
                int uid = rs.getInt("uid");
                User user = new UserDAOImpl().getUserById(uid);
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                String status = rs.getString("status");

                order.setId(id);
                order.setUser(user);
                order.setOrderCode(orderCode);
                order.setAddress(address);
                order.setPost(post);
                order.setReceiver(receiver);
                order.setMobile(mobile);
                order.setUserMessage(userMessage);
                order.setCreateDate(createDate);
                order.setPayDate(payDate);
                order.setDeliveryDate(deliveryDate);
                order.setConfirmDate(confirmDate);
                order.setStatus(status);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<Order> listOrderWithoutStatus(int uid, String excludedStatus, int start, int count) {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM order_ WHERE uid = ? AND status != ? ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);
            pstmt.setString(2, excludedStatus);
            pstmt.setInt(3, start);
            pstmt.setInt(4, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();

                int id = rs.getInt(1);
                User user = new UserDAOImpl().getUserById(uid);
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                String status = rs.getString("status");

                order.setId(id);
                order.setUser(user);
                order.setOrderCode(orderCode);
                order.setAddress(address);
                order.setPost(post);
                order.setReceiver(receiver);
                order.setMobile(mobile);
                order.setUserMessage(userMessage);
                order.setCreateDate(createDate);
                order.setPayDate(payDate);
                order.setDeliveryDate(deliveryDate);
                order.setConfirmDate(confirmDate);
                order.setStatus(status);

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}

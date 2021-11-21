package top.linzeliang.dao.impl;

import top.linzeliang.dao.OrderItemDAO;
import top.linzeliang.domain.Order;
import top.linzeliang.domain.OrderItem;
import top.linzeliang.domain.Product;
import top.linzeliang.domain.User;
import top.linzeliang.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public int getTotalOrderItem() {
        int totalOrderItem = 0;

        try (
                Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
        ) {

            String sql = "SELECT COUNT(*) FROM orderitem";

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                totalOrderItem = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalOrderItem;
    }

    @Override
    public int getTotalOrderItemByUser(int uid) {
        int totalOrderItem = 0;

        String sql = "SELECT COUNT(*) FROM orderitem WHERE uid = ? AND oid = -1";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalOrderItem = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalOrderItem;
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO orderitem VALUES (NULL, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setInt(1, orderItem.getProduct().getId());
            pstmt.setInt(2, orderItem.getUser().getId());
            // 订单存在才设置oid，否则设置为-1
            if (null != orderItem.getOrder()) {
                pstmt.setInt(3, orderItem.getOrder().getId());
            } else {
                pstmt.setInt(3, -1);
            }

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                orderItem.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItem(int id) {
        String sql = "DELETE FROM orderitem WHERE id = ?";

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
    public void updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE orderitem SET pid = ?, uid = ?, oid = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, orderItem.getProduct().getId());
            pstmt.setInt(2, orderItem.getUser().getId());
            // 存在订单才设置oid，否则为-1
            if (null != orderItem.getOrder()) {
                pstmt.setInt(3, orderItem.getOrder().getId());
            } else {
                pstmt.setInt(3, -1);
            }
            pstmt.setInt(4, orderItem.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        OrderItem orderItem = null;

        String sql = "SELECT * FROM orderitem WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                orderItem = new OrderItem();

                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                int oid = rs.getInt("oid");
                Product product = new ProductDAOImpl().getProductById(pid);
                Order order = null;
                if (-1 != oid) {
                    order = new OrderDAOImpl().getOrderById(oid);
                }
                User user = new UserDAOImpl().getUserById(uid);

                orderItem.setId(id);
                orderItem.setProduct(product);
                orderItem.setUser(user);
                orderItem.setOrder(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItem;
    }

    @Override
    public OrderItem getOrderItemByOrder(int oid) {
        OrderItem orderItem = null;

        String sql = "SELECT * FROM orderitem WHERE oid = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, oid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                orderItem = new OrderItem();

                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                Product product = new ProductDAOImpl().getProductById(pid);
                int uid = rs.getInt("uid");
                User user = new UserDAOImpl().getUserById(uid);
                Order order = null;
                if (-1 != oid) {
                    order = new OrderDAOImpl().getOrderById(oid);
                }

                orderItem.setId(id);
                orderItem.setProduct(product);
                orderItem.setUser(user);
                orderItem.setOrder(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItem;
    }

    @Override
    public List<OrderItem> listOrderItemsByUser(int uid, int start, int count) {
        List<OrderItem> orderItems = new ArrayList<>();

        // oid=-1说明产品只是被加入到购物车，但是还没创建订单付款
        String sql = "SELECT * FROM orderitem WHERE uid = ? AND oid = -1 ORDER BY id DESC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);
            pstmt.setInt(2, start);
            pstmt.setInt(3, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();

                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                Product product = new ProductDAOImpl().getProductById(pid);
                User user = new UserDAOImpl().getUserById(uid);
                Order order = new OrderDAOImpl().getOrderById(oid);

                orderItem.setId(id);
                orderItem.setProduct(product);
                orderItem.setUser(user);
                orderItem.setOrder(order);

                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    @Override
    public int getProductCollect(int pid, int excludedUser) {
        int totalProductCount = 0;

        String sql = "SELECT COUNT(*) FROM orderitem WHERE pid = ? AND uid != ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, pid);
            pstmt.setInt(2, excludedUser);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalProductCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalProductCount;
    }
}

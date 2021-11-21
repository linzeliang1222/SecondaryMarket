package top.linzeliang.dao.impl;

import top.linzeliang.dao.UserDAO;
import top.linzeliang.domain.User;
import top.linzeliang.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public int findTotalUser() {
        int totalUser = 0;

        try (
                Connection conn = DBUtil.getConnection();
                Statement stmt = conn.createStatement();
        ) {

            String sql = "SELECT COUNT(*) FROM user";

            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                totalUser = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalUser;
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user VALUES (NULL, ?, ?, 1000.00, '/', '/', 1, '---- ----', '/', 1)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";

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
    public void updateUser(User user) {
        String sql = "UPDATE user SET password = ?, balance = ?, mobile = ?," +
                " mail = ?, gender = ?, address = ?, qq = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, user.getPassword());
            pstmt.setFloat(2, user.getBalance());
            pstmt.setString(3, user.getMobile());
            pstmt.setString(4, user.getMail());
            pstmt.setInt(5, user.getGender());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getQq());
            pstmt.setInt(8, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(int id) {
        User user = null;

        String sql = "SELECT * FROM user WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                String name = rs.getString("name");
                String password = rs.getString("password");
                float balance = rs.getFloat("balance");
                String mobile = rs.getString("mobile");
                String mail = rs.getString("mail");
                int gender = rs.getInt("gender");
                String address = rs.getString("address");
                String qq = rs.getString("qq");
                int status = rs.getInt("status");

                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setBalance(balance);
                user.setMobile(mobile);
                user.setMail(mail);
                user.setGender(gender);
                user.setAddress(address);
                user.setQq(qq);
                user.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> listUser(int start, int count) {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM user ORDER BY id ASC LIMIT ?, ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, start);
            pstmt.setInt(2, count);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();

                int id = rs.getInt(1);
                String name = rs.getString("name");
                String password = rs.getString("password");
                float balance = rs.getFloat("balance");
                String mobile = rs.getString("mobile");
                String mail = rs.getString("mail");
                int gender = rs.getInt("gender");
                String address = rs.getString("address");
                String qq = rs.getString("qq");
                int status = rs.getInt("status");

                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setBalance(balance);
                user.setMobile(mobile);
                user.setMail(mail);
                user.setGender(gender);
                user.setAddress(address);
                user.setQq(qq);
                user.setStatus(status);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User getUserByName(String name) {
        User user = null;

        String sql = "SELECT * FROM user WHERE name = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();

                int id = rs.getInt(1);
                String password = rs.getString("password");
                float balance = rs.getFloat("balance");
                String mobile = rs.getString("mobile");
                String mail = rs.getString("mail");
                int gender = rs.getInt("gender");
                String address = rs.getString("address");
                String qq = rs.getString("qq");
                int status = rs.getInt("status");

                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setBalance(balance);
                user.setMobile(mobile);
                user.setMail(mail);
                user.setGender(gender);
                user.setAddress(address);
                user.setQq(qq);
                user.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserByNameAndActive(String name) {
        User user = null;

        String sql = "SELECT * FROM user WHERE name = ? AND status = 1";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();

                int id = rs.getInt(1);
                String password = rs.getString("password");
                float balance = rs.getFloat("balance");
                String mobile = rs.getString("mobile");
                String mail = rs.getString("mail");
                int gender = rs.getInt("gender");
                String address = rs.getString("address");
                String qq = rs.getString("qq");
                int status = rs.getInt("status");

                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setBalance(balance);
                user.setMobile(mobile);
                user.setMail(mail);
                user.setGender(gender);
                user.setAddress(address);
                user.setQq(qq);
                user.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserByNameAndPasswordAndActive(String name, String password) {
        User user = null;

        String sql = "SELECT * FROM user WHERE name = ? AND password = ? AND status = 1";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, name);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();

                int id = rs.getInt(1);
                float balance = rs.getFloat("balance");
                String mobile = rs.getString("mobile");
                String mail = rs.getString("mail");
                int gender = rs.getInt("gender");
                String address = rs.getString("address");
                String qq = rs.getString("qq");
                int status = rs.getInt("status");

                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setBalance(balance);
                user.setMobile(mobile);
                user.setMail(mail);
                user.setGender(gender);
                user.setAddress(address);
                user.setQq(qq);
                user.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        User user = null;

        String sql = "SELECT * FROM user WHERE name = ? AND password = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, name);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();

                int id = rs.getInt(1);
                float balance = rs.getFloat("balance");
                String mobile = rs.getString("mobile");
                String mail = rs.getString("mail");
                int gender = rs.getInt("gender");
                String address = rs.getString("address");
                String qq = rs.getString("qq");
                int status = rs.getInt("status");

                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setBalance(balance);
                user.setMobile(mobile);
                user.setMail(mail);
                user.setGender(gender);
                user.setAddress(address);
                user.setQq(qq);
                user.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public int getSaleCountByUser(int uid) {
        int totalSale = 0;

        // 用户销售量是已经下单购买了的，不包含在购物车中的，则oid != -1
        String sql = "SELECT * FROM orderItem t1 JOIN (SELECT * FROM product WHERE uid = ?) t2 ON t1.pid = t2.id AND oid != -1 JOIN order_ t3 ON t1.uid = t3.uid WHERE status = 'finish'";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, uid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalSale = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSale;
    }

    @Override
    public int setUserStatus(int uid, int status) {
        String sql = "UPDATE user SET status = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setInt(1, status);
            pstmt.setInt(2, uid);

            // 获取受影响的行，修改成功结果应该是1
            int res = pstmt.executeUpdate();

            return res;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 返回0代表修改失败
        return 0;
    }

    @Override
    public void updateUserPassword(User user) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, user.getPassword());
            pstmt.setInt(2, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserInfo(User user) {
        String sql = "UPDATE user SET mobile = ?, mail = ?, gender = ?, address = ?, qq = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, user.getMobile());
            pstmt.setString(2, user.getMail());
            pstmt.setInt(3, user.getGender());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getQq());
            pstmt.setInt(6, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserBalance(User user) {
        String sql = "UPDATE user SET balance = ? WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setFloat(1, user.getBalance());
            pstmt.setInt(2, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
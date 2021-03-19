package top.linzeliang.dao.impl;

import top.linzeliang.dao.AdministratorUserDAO;
import top.linzeliang.domain.AdministratorUser;
import top.linzeliang.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorUserDAOImpl implements AdministratorUserDAO {
    @Override
    public AdministratorUser getAdministratorUserByName(String name) {
        AdministratorUser administratorUser = null;

        String sql = "SELECT * FROM administratoruser WHERE name = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, name);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                administratorUser = new AdministratorUser();

                int id = rs.getInt(1);
                String password = rs.getString("password");

                administratorUser.setId(id);
                administratorUser.setName(name);
                administratorUser.setPassword(password);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return administratorUser;
    }

    @Override
    public AdministratorUser getAdministratorUserByNameAndPassword(String name, String password) {
        AdministratorUser administratorUser = null;

        String sql = "SELECT * FROM administratoruser WHERE name = ? AND password = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            pstmt.setString(1, name);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                administratorUser = new AdministratorUser();

                int id = rs.getInt(1);

                administratorUser.setId(id);
                administratorUser.setName(name);
                administratorUser.setPassword(password);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return administratorUser;
    }
}

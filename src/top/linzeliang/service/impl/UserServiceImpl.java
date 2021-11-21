package top.linzeliang.service.impl;

import top.linzeliang.dao.UserDAO;
import top.linzeliang.dao.impl.UserDAOImpl;
import top.linzeliang.domain.User;
import top.linzeliang.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    final private UserDAO userDAO = new UserDAOImpl();

    @Override
    public int getTotalUser() {
        return userDAO.findTotalUser();
    }

    @Override
    public void register(User user) {
        userDAO.addUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }

    @Override
    public List<User> findAllUser() {
        return userDAO.listUser(0, Short.MAX_VALUE);
    }

    @Override
    public List<User> findSomeUser(int start, int count) {
        return userDAO.listUser(start, count);
    }

    @Override
    public boolean isExistUser(String name) {
        return null != userDAO.getUserByName(name);
    }

    @Override
    public boolean isExistActiveUser(String name) {
        return null != userDAO.getUserByName(name);
    }

    @Override
    public User login(String name, String password) {
        return userDAO.getUserByNameAndPassword(name, password);
    }

    @Override
    public void setUserSales(User user) {
        user.setSales(userDAO.getSaleCountByUser(user.getId()));
    }

    @Override
    public int setUserStatus(int uid, int status) {
        return userDAO.setUserStatus(uid, status);
    }

    @Override
    public void updateUserPassword(User user) {
        userDAO.updateUserPassword(user);
    }

    @Override
    public void updateUserInfo(User user) {
        userDAO.updateUserInfo(user);
    }

    @Override
    public void updateUserBalance(User user) {
        userDAO.updateUserBalance(user);
    }
}

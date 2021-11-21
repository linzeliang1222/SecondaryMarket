package top.linzeliang.service;


import top.linzeliang.domain.User;

import java.util.List;

/**
 * @Description: TODO
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface UserService {
    int getTotalUser();

    void register(User user);

    void deleteUser(int id);

    void updateUser(User user);

    User getUserById(int id);

    User getUserByName(String name);

    List<User> findAllUser();

    List<User> findSomeUser(int start, int count);

    /**
     * 判断用户名是否存在
     *
     * @param: name
     * @return: boolean
     */
    boolean isExistUser(String name);

    /**
     * 判断用户名是否存在且激活
     *
     * @param: name
     * @return: boolean
     */
    boolean isExistActiveUser(String name);

    User login(String name, String password);

    /**
     * 设置用户的销量
     *
     * @param: user
     */
    void setUserSales(User user);

    /**
     * 设置用户的状态
     *
     * @param: uid
     * @param: status
     * @return: int
     */
    int setUserStatus(int uid, int status);

    /**
     * 修改账户密码
     *
     * @param: user
     */
    void updateUserPassword(User user);

    /**
     * 更新账户信息
     *
     * @param: user
     */
    void updateUserInfo(User user);

    /**
     * 更新账户余额
     *
     * @param: user
     */
    void updateUserBalance(User user);
}

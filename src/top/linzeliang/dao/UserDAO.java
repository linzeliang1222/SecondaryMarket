package top.linzeliang.dao;

import top.linzeliang.domain.User;

import java.util.List;

/**
 * @Description: User对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-08
 */
public interface UserDAO {

    /**
     * 获取总的用户数
     *
     * @return: int
     */
    int findTotalUser();

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    /**
     * 根据id获取用户信息
     *
     * @param: id
     * @return: User
     */
    User getUserById(int id);

    /**
     * 获取用户列表
     *
     * @param: start
     * @param: count
     * @return: List<User>
     */
    List<User> listUser(int start, int count);

    /**
     * 通过用户名获取用户信息
     *
     * @param: name
     * @return: User
     */
    User getUserByName(String name);

    /**
     * 通过用户名获取用户信息且状态为激活状态
     *
     * @param: name
     * @return: User
     */
    User getUserByNameAndActive(String name);

    /**
     * 通过用户名和密码获取用户信息且状态为激活状态
     *
     * @param: name
     * @param: password
     * @return: User
     */
    User getUserByNameAndPasswordAndActive(String name, String password);

    /**
     * 通过账号密码获取用户信息
     *
     * @param: name
     * @param: password
     * @return: User
     */
    User getUserByNameAndPassword(String name, String password);

    /**
     * 获取某用户的销售量
     *
     * @param: uid
     * @return: int
     */
    int getSaleCountByUser(int uid);

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

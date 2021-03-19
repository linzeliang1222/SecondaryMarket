package top.linzeliang.dao;

import top.linzeliang.domain.AdministratorUser;

/**
 * @Description: AdministratorUser对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface AdministratorUserDAO {
    /**
     * 通过用户名获取超级管理员对象
     */
    AdministratorUser getAdministratorUserByName(String name);

    /**
     * 通过用户名和密码获取超级管理员对象
     */
    AdministratorUser getAdministratorUserByNameAndPassword(String name, String password);
}

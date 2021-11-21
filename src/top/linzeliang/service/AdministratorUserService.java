package top.linzeliang.service;

import top.linzeliang.domain.AdministratorUser;

/**
 * @Description: TODO
 * @Author: LinZeLiang
 * @Date: 2021-02-10
 */
public interface AdministratorUserService {
    AdministratorUser getUserByName(String name);

    AdministratorUser getUserByNameAndPassword(String name, String password);
}

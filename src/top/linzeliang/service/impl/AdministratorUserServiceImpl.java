package top.linzeliang.service.impl;

import top.linzeliang.dao.AdministratorUserDAO;
import top.linzeliang.dao.impl.AdministratorUserDAOImpl;
import top.linzeliang.domain.AdministratorUser;
import top.linzeliang.service.AdministratorUserService;

public class AdministratorUserServiceImpl implements AdministratorUserService {

    final private AdministratorUserDAO administratorUserDAO = new AdministratorUserDAOImpl();

    @Override
    public AdministratorUser getUserByName(String name) {
        return administratorUserDAO.getAdministratorUserByName(name);
    }

    @Override
    public AdministratorUser getUserByNameAndPassword(String name, String password) {
        return administratorUserDAO.getAdministratorUserByNameAndPassword(name, password);
    }
}

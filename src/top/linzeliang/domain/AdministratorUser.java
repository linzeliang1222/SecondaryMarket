package top.linzeliang.domain;

/**
 * @Description: AdministratorUser超级管理员实体类
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public class AdministratorUser {
    private int id;
    private String name;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

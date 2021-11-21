package top.linzeliang.domain;

/**
 * @Description: User实体类
 * @Author: LinZeLiang
 * @Date: 2021-02-08
 */
public class User {
    private int id;
    private int status;
    private String name;
    private String password;
    private float balance;
    private String mobile;
    private String mail;
    private int gender;
    private String address;
    private String qq;
    private int sales;
    private int totalCountCollect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getTotalCountCollect() {
        return totalCountCollect;
    }

    public void setTotalCountCollect(int totalCountCollect) {
        this.totalCountCollect = totalCountCollect;
    }

    /**
     * 获取用户名的匿名昵称，在评价的时候显示用户名使用
     *
     * @return: String
     */
    public String getAnonymousName() {
        if (null == name) {
            return null;
        }
        if (name.length() <= 1) {
            return "*";
        }
        if (name.length() == 2) {
            return name.substring(0, 1) + "*";
        }
        char[] cs = name.toCharArray();
        for (int i = 1; i < cs.length - 1; i++) {
            cs[i] = '*';
        }
        return new String(cs);
    }

    /**
     * 获取省
     *
     * @return: String
     */
    public String getProvince() {
        return address.split(" ")[0];
    }

    /**
     * 获取市
     *
     * @return: String
     */
    public String getCity() {
        return address.split(" ")[1];
    }
}

package top.linzeliang.domain;


/**
 * @Description: OrderItem实体类
 * @Author: LinZeLiang
 * @Date: 2021-02-08
 */
public class OrderItem {
    private int id;
    private Product product;
    private User user;
    private Order order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

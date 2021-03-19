package top.linzeliang.dao;

import top.linzeliang.domain.Order;

import java.util.List;

/**
 * @Description: Order对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface OrderDAO {
    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    /**
     * 查询总的订单数
     *
     * @return: int
     */
    int getTotalOrder();

    /**
     * 查询某用户的订单数
     *
     * @return: int
     */
    int getTotalOrderByUser(int uid);

    void addOrder(Order order);

    void deleteOrder(int id);

    void updateOrder(Order order);

    /**
     * 根据id获取订单信息
     *
     * @param: id
     * @return: Order
     */
    Order getOrderById(int id);

    /**
     * 获取订单集合
     *
     * @param: start
     * @param: count
     * @return: List<Order>
     */
    List<Order> listOrder(int start, int count);

    /**
     * 获取某用户排除指定状态订单集合
     *
     * @param: uid
     * @param: excludedStatus
     * @param: start
     * @param: count
     * @return: List<Order>
     */
    List<Order> listOrderWithoutStatus(int uid, String excludedStatus, int start, int count);
}

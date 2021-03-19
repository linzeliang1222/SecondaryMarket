package top.linzeliang.service;

import top.linzeliang.domain.Order;
import top.linzeliang.domain.OrderItem;

import java.util.List;

/**
 * @Description: TODO
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface OrderItemService {
    int getTotalOrderItem();

    int getTotalOrderItemByUser(int uid);

    int getProductCollect(int pid, int excludedUser);

    void addOrderItem(OrderItem orderItem);

    void deleteOrderItem(int id);

    void updateOrderItem(OrderItem orderItem);

    OrderItem getOrderItemById(int id);

    OrderItem getOrderItemByOrder(int oid);

    List<OrderItem> findAllOrderItemByUser(int uid);

    List<OrderItem> findSomeOrderItemByUser(int uid, int start, int count);

    void fillByOrders(List<Order> orders);

    /**
     * 填充订单的信息: 订单项、价格
     *
     * @param: order
     */
    void fillByOrder(Order order);
}
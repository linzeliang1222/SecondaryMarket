package top.linzeliang.dao;

import top.linzeliang.domain.OrderItem;

import java.util.List;

/**
 * @Description: OrderItem对象的ORM映射
 * @Author: LinZeLiang
 * @Date: 2021-02-09
 */
public interface OrderItemDAO {
    /**
     * 获取所有订单项的总数
     *
     * @return: int
     */
    int getTotalOrderItem();

    /**
     * 获取某用户的收藏数
     *
     * @return: int
     */
    int getTotalOrderItemByUser(int uid);

    void addOrderItem(OrderItem orderItem);

    void deleteOrderItem(int id);

    void updateOrderItem(OrderItem orderItem);

    /**
     * 通过id获取订单项
     *
     * @param: id
     * @return: OrderItem
     */
    OrderItem getOrderItemById(int id);

    /**
     * 通过oid获取订单项
     *
     * @param: oid
     * @return: OrderItem
     */
    OrderItem getOrderItemByOrder(int oid);

    /**
     * 获取用户购物车的产品
     *
     * @param: uid
     * @param: start
     * @param: count
     * @return: List<Order>
     */
    List<OrderItem> listOrderItemsByUser(int uid, int start, int count);

    /**
     * 获取产品的收藏数量
     *
     * @param: pid
     * @param: start
     * @param: count
     * @return: List<Order>
     */
    int getProductCollect(int pid, int excludedUser);
}

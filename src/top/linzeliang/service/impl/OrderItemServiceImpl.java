package top.linzeliang.service.impl;

import top.linzeliang.dao.OrderItemDAO;
import top.linzeliang.dao.ProductDAO;
import top.linzeliang.dao.impl.OrderItemDAOImpl;
import top.linzeliang.dao.impl.ProductDAOImpl;
import top.linzeliang.domain.Order;
import top.linzeliang.domain.OrderItem;
import top.linzeliang.service.OrderItemService;
import top.linzeliang.service.ProductService;

import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {

    final private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Override
    public int getTotalOrderItem() {
        return orderItemDAO.getTotalOrderItem();
    }

    @Override
    public int getTotalOrderItemByUser(int uid) {
        return orderItemDAO.getTotalOrderItemByUser(uid);
    }

    @Override
    public int getProductCollect(int pid, int excludedUser) {
        return orderItemDAO.getProductCollect(pid, excludedUser);
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        orderItemDAO.addOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(int id) {
        orderItemDAO.deleteOrderItem(id);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        orderItemDAO.updateOrderItem(orderItem);
    }

    @Override
    public OrderItem getOrderItemById(int id) {
        return orderItemDAO.getOrderItemById(id);
    }

    @Override
    public OrderItem getOrderItemByOrder(int oid) {
        return orderItemDAO.getOrderItemByOrder(oid);
    }

    @Override
    public List<OrderItem> findAllOrderItemByUser(int uid) {
        return orderItemDAO.listOrderItemsByUser(uid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<OrderItem> findSomeOrderItemByUser(int uid, int start, int count) {
        return orderItemDAO.listOrderItemsByUser(uid, start, count);
    }

    @Override
    public void fillByOrders(List<Order> orders) {
        for (Order order : orders) {
            fillByOrder(order);
        }
    }

    @Override
    public void fillByOrder(Order order) {
        OrderItem orderItemByOrder = orderItemDAO.getOrderItemByOrder(order.getId());

        // 为每个产品设置图片
        ProductService productService = new ProductServiceImpl();
        productService.setFirstProductImage(orderItemByOrder.getProduct());

        order.setOrderItem(orderItemByOrder);
        order.setTotalPrice(orderItemByOrder.getProduct().getPromotePrice());
    }
}
